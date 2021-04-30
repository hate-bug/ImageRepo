package com.shopify.imagerepo.Service;

import com.shopify.imagerepo.Exception.UserNotFoundException;
import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> result  = userRepository.findUserByUserName(userName);

        if (!result.isPresent()) {
            throw new UserNotFoundException("Username not found");
        }
        return result.get();
    }

    @Transactional
    public User loadUserById (long id) {
        Optional<User> result  = userRepository.findById(id);

        if (!result.isPresent()) {
            throw new UserNotFoundException("User Id not found");
        }
        return result.get();
    }
}
