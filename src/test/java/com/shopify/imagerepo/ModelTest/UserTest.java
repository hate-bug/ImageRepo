package com.shopify.imagerepo.ModelTest;

import com.shopify.imagerepo.ImagerepoApplication;
import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest (classes = ImagerepoApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserTest {

    User user1, user2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup () {
        this.user1 = new User ("user1", "pwd1");
        this.user2 = new User ("user2", "pwd2");

        this.user1 = userRepository.save(this.user1);
        this.user2 = userRepository.save(this.user2);

    }

    /**
     * Make sure user persists
     */
    @Test
    void TestUserPersist () {
        assertTrue(this.userRepository.findUserByUserName(this.user1.getUsername()).isPresent());
        assertTrue(this.userRepository.findUserByUserName(this.user2.getUsername()).isPresent());
        assertEquals(this.user1.getUsername(), this.userRepository.findUserByUserName(this.user1.getUsername()).get().getUsername());
        assertEquals(this.user2.getUsername(), this.userRepository.findUserByUserName(this.user2.getUsername()).get().getUsername());
    }

}
