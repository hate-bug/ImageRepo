package com.shopify.imagerepo.Controller;

import com.shopify.imagerepo.Repository.ImageRepository;
import com.shopify.imagerepo.Service.MapValidationService;
import com.shopify.imagerepo.Service.ParseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ParseFileService parseFileService;


    @PostMapping("/save")
    private ResponseEntity<?> storeImage (@RequestParam("file") MultipartFile file,  @RequestParam("isPublic") @NotBlank @NotNull boolean isPublic) {
        try{
            return parseFileService.getImage(file, isPublic);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.BAD_REQUEST);
        }
    }
}
