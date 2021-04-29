package com.shopify.imagerepo.Service;

import com.shopify.imagerepo.Exception.ImageSaveException;
import com.shopify.imagerepo.Model.Image;
import com.shopify.imagerepo.Payload.ImageAccessibilityPayload;
import com.shopify.imagerepo.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ParseFileService {

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<?> saveImage (MultipartFile file, boolean isPublic) {

        try {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setContent(file.getBytes());
            image.setType(file.getContentType());
            image.setPublic(isPublic);
            return new ResponseEntity<>(imageRepository.save(image), HttpStatus.CREATED);
        } catch (Exception e) {
            throw  new ImageSaveException(e.getMessage());
        }
    }

    public ResponseEntity<?> saveImages (MultipartFile[] files) {

        List<Image> images = new LinkedList<>();

        for (MultipartFile file: files) {
            try {
                Image image = new Image();
                image.setName(file.getOriginalFilename());
                image.setContent(file.getBytes());
                image.setType(file.getContentType());
                image.setPublic(false); // for false now
                images.add(imageRepository.save(image));
            } catch (Exception e) {
                throw new ImageSaveException(e.getMessage());
            }
        }
        if (images.size()>0) {
            return new ResponseEntity<>(images, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("None image saved successfully.", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> setAccessibility (ImageAccessibilityPayload[] imageAccessibilityPayloads) {
        List <Image> images = new LinkedList<>();
        for (ImageAccessibilityPayload imageAccessibilityPayload: imageAccessibilityPayloads) {
            Optional<Image> image= this.imageRepository.findById(imageAccessibilityPayload.getImageId());
            if (!image.isPresent()) {
                return new ResponseEntity<>("Image ID does not exist.", HttpStatus.NOT_FOUND);
            }
            image.get().setPublic(imageAccessibilityPayload.isPublic());
            images.add(this.imageRepository.save(image.get()));
        }
        if (images.size()>0) {
            return new ResponseEntity<>(images, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No Image updated", HttpStatus.NOT_FOUND);
        }
    }
}
