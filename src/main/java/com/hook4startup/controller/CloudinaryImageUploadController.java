package com.hook4startup.controller;

import com.hook4startup.models.Post;
import com.hook4startup.models.UserProfile;
import com.hook4startup.repository.PostRepo;
import com.hook4startup.repository.UserProfileRepo;
import com.hook4startup.services.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryImageUploadController {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserProfileRepo userProfileRepo;
    @PostMapping("/post/create")
    public ResponseEntity<Map> postCreate(@RequestParam("image")MultipartFile file){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Map.of(
                    "message","User is not authenticated"
            ), HttpStatus.UNAUTHORIZED);
        }
        String nameofUser = authentication.getName();
       // System.out.println(nameofUser);

        Map data = this.cloudinaryImageService.uplodeImage(file);
        Post postbyusername = postRepo.findByusername(nameofUser);
      if (postbyusername != null){
          postbyusername.setImageUrl(data.get("url").toString());
          postRepo.save(postbyusername);
      }else {
          return new ResponseEntity<>(Map.of(
                    "message","Post not found"
            ), HttpStatus.NOT_FOUND);

      }

      return new ResponseEntity<>( Map.of(
            "message","Image uploaded successfully",
            "Image data",data
    ), HttpStatus.OK);
    }

    @PostMapping("/post/update")
    public ResponseEntity<Map> postUpdate(@RequestParam("image")MultipartFile file){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Map.of(
                    "message","User is not authenticated"
            ), HttpStatus.UNAUTHORIZED);
        }
        String nameofUser = authentication.getName();
        // System.out.println(nameofUser);

        Map data = this.cloudinaryImageService.uplodeImage(file);
        Post postbyusername = postRepo.findByusername(nameofUser);
        if (postbyusername != null){
            postbyusername.setImageUrl(data.get("url").toString());
            postRepo.save(postbyusername);
        }else {
            return new ResponseEntity<>(Map.of(
                    "message","Post not found"
            ), HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>( Map.of(
                "message","Image uploaded successfully",
                "Image data",data
        ), HttpStatus.OK);
    }

    @PostMapping("/profile/create")
    public ResponseEntity<Map> profileCreate(@RequestParam("image")MultipartFile file){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Map.of(
                    "message","User is not authenticated"
            ), HttpStatus.UNAUTHORIZED);
        }
        String nameofUser = authentication.getName();
        UserProfile userProfileByUsername = userProfileRepo.findUserProfileByUsername(nameofUser);
        Map data = this.cloudinaryImageService.uplodeImage(file);
        if (userProfileByUsername != null){
            userProfileByUsername.setProfilePictureUrl(data.get("secure_url").toString());
            userProfileRepo.save(userProfileByUsername);
            return new ResponseEntity<>(Map.of(
                    "message","Image uploaded successfully"
            ), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(Map.of(
                    "message","userProfile not found"
            ), HttpStatus.NOT_FOUND);

        }

    }


    @PostMapping("/profile/update")
    public ResponseEntity<Map> profileUpdate(@RequestParam("image")MultipartFile file){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Map.of(
                    "message","User is not authenticated"
            ), HttpStatus.UNAUTHORIZED);
        }
        String nameofUser = authentication.getName();
        UserProfile userProfileByUsername = userProfileRepo.findUserProfileByUsername(nameofUser);
        Map data = this.cloudinaryImageService.uplodeImage(file);
        if (userProfileByUsername != null){
            userProfileByUsername.setProfilePictureUrl(data.get("secure_url").toString());
            userProfileRepo.save(userProfileByUsername);
            return new ResponseEntity<>(Map.of(
                    "message","Image uploaded successfully"
            ), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(Map.of(
                    "message","userProfile not found"
            ), HttpStatus.NOT_FOUND);

        }

    }

}
