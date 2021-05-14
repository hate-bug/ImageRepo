package com.shopify.imagerepo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageSaveException extends RuntimeException {
    public ImageSaveException (String message) {
        super(message);
    }
}
