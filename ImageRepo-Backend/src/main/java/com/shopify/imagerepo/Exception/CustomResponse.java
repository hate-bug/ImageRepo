package com.shopify.imagerepo.Exception;

public class CustomResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomResponse (String message) {
        this.message = message;
    }
}
