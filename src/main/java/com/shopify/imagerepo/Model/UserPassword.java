package com.shopify.imagerepo.Model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
public class UserPassword {

    private String encryptedPassword;

    public UserPassword () {
        this ("unknown");
    }

    public UserPassword (String plainTextPassword){
        this.encryptedPassword = new BCryptPasswordEncoder().encode(plainTextPassword);
    }

    public void setPassword (String plainTextPassword){
        this.encryptedPassword = new BCryptPasswordEncoder().encode(plainTextPassword);
    }

    public String getPassword (){
        return this.encryptedPassword;
    }
}
