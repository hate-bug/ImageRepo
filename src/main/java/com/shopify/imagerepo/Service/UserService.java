package com.shopify.imagerepo.Service;

import com.shopify.imagerepo.Exception.UsersaveException;
import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Model.UserPassword;
import com.shopify.imagerepo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> userRegisterService (User user) {
        if (userRepository.findUserByUserName(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new UsersaveException ("User name and password cannot be empty");
        }
    }

}
