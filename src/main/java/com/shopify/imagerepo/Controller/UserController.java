package com.shopify.imagerepo.Controller;

import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Payload.JWTLoginSuccessResponse;
import com.shopify.imagerepo.Payload.UserLogInRequest;
import com.shopify.imagerepo.Security.JWTProvider;
import com.shopify.imagerepo.Security.SecurityURLs;
import com.shopify.imagerepo.Service.ErrorMapServices;
import com.shopify.imagerepo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping ("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ErrorMapServices errorMapServices;

    @PostMapping ("/login")
    public ResponseEntity<?> userAuthentication (@Valid @RequestBody UserLogInRequest userLogInRequest, BindingResult bindingResult){
        if (errorMapServices.ErrorMapService(bindingResult) != null) {
            return errorMapServices.ErrorMapService(bindingResult);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogInRequest.getUsername(), userLogInRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwttoken = SecurityURLs.Token_Prefix + jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwttoken));
    }

    @PostMapping ("/register")
    public ResponseEntity<?> userRegister (@RequestBody User user) {

        return this.userService.userRegisterService(user);
    }

}
