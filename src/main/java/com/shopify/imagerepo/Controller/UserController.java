package com.shopify.imagerepo.Controller;

import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping ("/register")
    public ResponseEntity<?> userRegister (@RequestBody User user) {

        return this.userService.userRegisterService(user);
    }

}
