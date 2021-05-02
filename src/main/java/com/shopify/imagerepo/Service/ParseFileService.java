package com.shopify.imagerepo.Service;

import com.shopify.imagerepo.Exception.ImageAccessException;
import com.shopify.imagerepo.Exception.ImageSaveException;
import com.shopify.imagerepo.Model.Image;
import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Payload.ImageAccessibilityPayload;
import com.shopify.imagerepo.Payload.ImageItem;
import com.shopify.imagerepo.Repository.ImageRepository;
import com.shopify.imagerepo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
public class ParseFileService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    private final String imageURL = "/api/image/getimagebyid?id=";

    public ResponseEntity<?> saveImage (MultipartFile file, boolean isPublic, String userName) {

        try {
            User user = userRepository.findUserByUserName(userName).get();
            Image image = new Image();
            image.setUser(user);
            image.setName(file.getOriginalFilename());
            image.setContent(file.getBytes());
            image.setType(file.getContentType());
            image.setPublic(isPublic);
            return new ResponseEntity<>(imageRepository.save(image), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ImageSaveException(e.getMessage());
        }
    }

    public ResponseEntity<?> saveImages (MultipartFile[] files, String userName) {

        List<Image> images = new LinkedList<>();

        for (MultipartFile file: files) {
            try {
                User user = userRepository.findUserByUserName(userName).get();
                Image image = new Image();
                image.setUser(user);
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

    public ResponseEntity<?> setAccessibility (ImageAccessibilityPayload[] imageAccessibilityPayloads, String userName) {
        List <Image> images = new LinkedList<>();
        for (ImageAccessibilityPayload imageAccessibilityPayload: imageAccessibilityPayloads) {
            Optional<Image> image= this.imageRepository.findById(imageAccessibilityPayload.getImageId());
            if (!image.isPresent()) {
                return new ResponseEntity<>("Image ID does not exist.", HttpStatus.NOT_FOUND);
            }
            if (!image.get().getUser().getUsername().equals(userName)) { // check if login user can access the Image (access control)
                return new ResponseEntity<>("User doesn't have access to Image ID: "+ image.get().getId()+".", HttpStatus.BAD_REQUEST);
            }
            image.get().setPublic(imageAccessibilityPayload.isPublic());
            images.add(this.imageRepository.save(image.get()));
        }
        if (images.size()>0) {
            return new ResponseEntity<>(images, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Image updated", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteImages (Long [] imageIds, String userName) {
        for (Long id: imageIds) {
            Optional<Image> imageOptional = this.imageRepository.findById(id);
            if (imageOptional.isPresent() && imageOptional.get().getUser().getUsername().equals(userName)){ // if image exists and access is allowed
                Image image = this.imageRepository.findById(id).get();
                this.imageRepository.delete(image);
            } else {
                return new ResponseEntity<>("Image id: "+id +" is not able to be deleted", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("All images deleted.", HttpStatus.OK);
    }

    public ResponseEntity<?> getImagebyId (Long imageId, String userName) {
        Optional<Image> optionalImage = this.imageRepository.findById(imageId);
        if (optionalImage.isPresent() && (optionalImage.get().getUser().getUsername().equals(userName) || optionalImage.get().isPublic())) {
            Image image = optionalImage.get();
            return new ResponseEntity<>(image.getContent(), HttpStatus.OK);
        } else {
            throw new ImageAccessException("Image not accessible");
        }
    }

    public ResponseEntity<?> getAllImages (String userName, String requesturl) {
        requesturl += this.imageURL;
        List <ImageItem> imageList = new LinkedList<>();
        Optional<User> optionalUser = this.userRepository.findUserByUserName(userName);
        if (optionalUser.isPresent()) {
            Iterable<Image> images = this.imageRepository.findAllByUserOrisPublic(optionalUser.get().getId());

            for (Image image: images) {
                ImageItem imageItem = new ImageItem(image.getName(),requesturl+image.getId());
                imageList.add(imageItem);
            }
            return new ResponseEntity<>(imageList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User can not be authorized", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllImagesByName (String fileName, String userName, String url) {
        url += this.imageURL;
        List<ImageItem> imageList = new LinkedList<>();
        Optional<User> optionalUser = this.userRepository.findUserByUserName(userName);
        if (optionalUser.isPresent()) {
            Iterable<Image> images = this.imageRepository.findImagesByName(optionalUser.get().getId(), fileName);

            for (Image image: images) {
                ImageItem imageItem = new ImageItem(image.getName(),url+image.getId());
                imageList.add(imageItem);
            }
            return new ResponseEntity<>(imageList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User can not be authorized", HttpStatus.BAD_REQUEST);
        }
    }


}
