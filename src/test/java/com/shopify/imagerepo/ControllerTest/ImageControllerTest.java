package com.shopify.imagerepo.ControllerTest;

import com.shopify.imagerepo.ImagerepoApplication;
import com.shopify.imagerepo.Model.Image;
import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Repository.ImageRepository;
import com.shopify.imagerepo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest (classes = ImagerepoApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ImageControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MockMvc mockMvc;

    private User user1, user2;
    private Image image1, image2;


    @BeforeEach
    void setup () {
        this.user1 = new User("user1", "pwd");
        this.user2 = new User("user2", "pwd");
        this.userRepository.save(user1);
        this.userRepository.save(user2);

        this.image1 = new Image();
        this.image1.setPublic(false);
        this.image1.setUser(user1);
        this.image1.setContent(new byte[200]);
        this.image1.setType("jpg");
        this.image1.setName("name1");

        this.image2 = new Image();
        this.image2.setUser(user2);
        this.image2.setPublic(false);
        this.image2.setContent(new byte[500]);
        this.image2.setType("jpg");
        this.image2.setName("name1");

        this.imageRepository.save(image1);
        this.imageRepository.save(image2);
    }

    /**
     * Test with set permissions:
     * User is only able to his own image
     */
    @Test
    @WithMockUser(username = "user1")
    @Transactional
    public void testImagePermission () throws Exception{
        String changePermission1 = "[{\n" +
                "        \"imageId\": 1, \n" +
                "        \"isPublic\": true\n" +
                "    }]";
        // log in as User1, change his own image to isPublic = true
        this.mockMvc.perform(put("/api/image/updateimages").contentType("application/json").content(changePermission1))
                .andExpect(status().isOk());

        assertTrue(this.imageRepository.findById(Integer.toUnsignedLong(1)).get().isPublic());

        //log in as User2, receive error response
        String changePermission2 = "[{\n" +
                "        \"imageId\": 2, \n" +
                "        \"isPublic\": true\n" +
                "    }]";
        this.mockMvc.perform(put("/api/image/updateimages").contentType("application/json").content(changePermission2))
                .andExpect(status().isBadRequest());
        assertFalse(this.imageRepository.findById(Integer.toUnsignedLong(2)).get().isPublic());
    }

}
