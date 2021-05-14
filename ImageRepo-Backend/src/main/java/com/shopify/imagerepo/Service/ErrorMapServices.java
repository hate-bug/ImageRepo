package com.shopify.imagerepo.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@Service
public class ErrorMapServices {
    public ResponseEntity<?> ErrorMapService (BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error: result.getFieldErrors()){
            errorMap.put (error.getField(), error.getDefaultMessage());
        }

        if (result.hasErrors()) {
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        } else {
            return null;
        }
    }
}
