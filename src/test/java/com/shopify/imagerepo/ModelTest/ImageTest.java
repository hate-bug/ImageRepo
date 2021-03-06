package com.shopify.imagerepo.ModelTest;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest (classes = ImagerepoApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ImageTest {

    Image image1, image2;
    User user;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup () {
        this.user = new User("user", "password");
        this.userRepository.save(user);
        image1 = new Image();
        image2 = new Image();
        image1.setName("pic1");
        image1.setPublic(true);
        image1.setType("image/png");
        image1.setContent(new byte[]{2,3,4});
        image1.setUser(this.user);

        image2.setName("pic2");
        image2.setPublic(true);
        image2.setType("image/png");
        image2.setContent(new byte[50000]);
        image2.setUser(this.user);

        image1 = imageRepository.save(image1);
        image2 = imageRepository.save(image2);

    }

    /**
     * Make sure the image creation date is correctly updated
     */
    @Test
    void TestImageDate () {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals (dateFormat.format(image1.getCreateDate()), dateFormat.format(new Date().getTime()));
        assertEquals (dateFormat.format(image2.getCreateDate()), dateFormat.format(new Date().getTime()));
    }

    /**
     * Test on the Image content can be stored correctly, even with large file
     */
    @Test
    void TestImageContent () {

        assertArrayEquals(new byte[]{2,3,4}, image1.getContent());
        assertArrayEquals(new byte[50000], image2.getContent());
    }

}
