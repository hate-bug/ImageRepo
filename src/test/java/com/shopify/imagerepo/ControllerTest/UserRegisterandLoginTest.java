package com.shopify.imagerepo.ControllerTest;

import com.shopify.imagerepo.ImagerepoApplication;
import com.shopify.imagerepo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest (classes = ImagerepoApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRegisterandLoginTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;


    /**
     * User register with account name and password
     * User login with correct credentials
     * User login with wrong credentials
     * User exists in the Repository after registration
     */
    @Test
    @Transactional
    public void TestUserRegisterandLogin () throws Exception {
        String registerJson = "{\"userName\": \"user\", \n" +
                "    \"password\": \"password\"}";
        this.mockMvc.perform(post("/api/user/register").content(registerJson).contentType("application/json"))
                .andExpect(status().isOk());
        assertTrue(this.userRepository.findUserByUserName("user").isPresent());
        assertEquals("user", this.userRepository.findUserByUserName("user").get().getUsername());

        String loginJson = "{ \"username\": \"user\", \n" +
                "    \"password\": \"password\"}";
        this.mockMvc.perform(post("/api/user/login").content(loginJson).contentType("application/json"))
                .andExpect(status().isOk());

        String wrongLoginJson = "{ \"username\": \"user\", \n" +
                "    \"password\": \"wrongpassword\"}";
        this.mockMvc.perform(post("/api/user/login").content(wrongLoginJson).contentType("application.json"))
                .andExpect(status().isUnsupportedMediaType());

        assertTrue(this.userRepository.findUserByUserName("user").isPresent());
    }


}
