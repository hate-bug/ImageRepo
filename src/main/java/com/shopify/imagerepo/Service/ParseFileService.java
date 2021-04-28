package com.shopify.imagerepo.Service;

import com.shopify.imagerepo.Exception.ImageSaveException;
import com.shopify.imagerepo.Model.Image;
import com.shopify.imagerepo.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParseFileService {

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<?> getImage (MultipartFile file, boolean isPublic) {

        try {
            Image image = new Image();
            image.setName(file.getName());
            image.setContent(file.getBytes());
            image.setType(file.getContentType());
            image.setPublic(isPublic);
            return new ResponseEntity<>(imageRepository.save(image), HttpStatus.CREATED);
        } catch (Exception e) {
            throw  new ImageSaveException(e.getMessage());
        }

    }
}
