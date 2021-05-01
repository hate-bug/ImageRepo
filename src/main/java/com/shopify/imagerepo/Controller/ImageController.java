package com.shopify.imagerepo.Controller;

import com.shopify.imagerepo.Payload.ImageAccessibilityPayload;
import com.shopify.imagerepo.Service.ParseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ParseFileService parseFileService;


    @PostMapping("/save")
    private ResponseEntity<?> storeImage (@RequestParam("file") MultipartFile file, @RequestParam("isPublic") @NotBlank @NotNull boolean isPublic, Principal principal) {

        return parseFileService.saveImage(file, isPublic, principal.getName());

    }

    @PostMapping ("/saveall")
    private ResponseEntity<?> storeImages (@RequestParam("file") MultipartFile[] files, Principal principal) {

        return parseFileService.saveImages(files, principal.getName());
    }

    @PutMapping("/updateimages")
    private ResponseEntity<?> updateImagesPermission (@RequestBody ImageAccessibilityPayload[] imageAccessibilityPayloads, Principal principal) {

        return parseFileService.setAccessibility(imageAccessibilityPayloads, principal.getName());

    }

    @DeleteMapping("/deleteimages")
    private ResponseEntity<?> deleteImages (@RequestBody Long[] imageIds, Principal principal) {

        return parseFileService.deleteImages (imageIds, principal.getName());
    }


}
