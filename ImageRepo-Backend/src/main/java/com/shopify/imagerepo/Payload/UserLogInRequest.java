package com.shopify.imagerepo.Payload;

import javax.validation.constraints.NotBlank;

public class UserLogInRequest {

    @NotBlank (message = "User name cannot be empty.")
    private String username;

    @NotBlank (message = "User password cannot be empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
