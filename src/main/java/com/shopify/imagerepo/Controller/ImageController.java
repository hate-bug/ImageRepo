package com.shopify.imagerepo.Controller;

import com.shopify.imagerepo.Payload.ImageAccessibilityPayload;
import com.shopify.imagerepo.Service.ParseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        return parseFileService.saveImage(file, isPublic);

    }

    @PostMapping ("/saveall")
    private ResponseEntity<?> storeImages (@RequestParam("file") MultipartFile[] files) {

        return parseFileService.saveImages(files);
    }

    @PutMapping("/updateimages")
    private ResponseEntity<?> updateImages (@RequestBody ImageAccessibilityPayload[] imageAccessibilityPayloads) {

        return parseFileService.setAccessibility(imageAccessibilityPayloads);

    }


}
