package com.hook4startup.controller;

import com.hook4startup.Dto.PostDto;
import com.hook4startup.models.Post;
import com.hook4startup.models.User;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    PostRepo postRepo;

    @PostMapping("/create")
    public ResponseEntity<?> likePost(@RequestBody PostDto postDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            String nameofUser = authentication.getName();

            // Optional.ofNullable hata diya
            Optional<User> userOptional = customerRepo.findByUsername(nameofUser);
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();

            if (!user.isMakeProfileStatus()) {
                return new ResponseEntity<>("UserProfile not found, please create it", HttpStatus.BAD_REQUEST);
            }

            Optional<Post> byId = postRepo.findById(postDto.getPostId());
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
            }

            Post post = byId.get();
            post.getLikes().add(user);
            postRepo.save(post);

            return new ResponseEntity<>("Like post Done", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> unLikePost(@RequestBody PostDto postDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            String nameofUser = authentication.getName();

            // Optional.ofNullable hata diya
            Optional<User> userOptional = customerRepo.findByUsername(nameofUser);
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();

            if (!user.isMakeProfileStatus()) {
                return new ResponseEntity<>("UserProfile not found, please create it", HttpStatus.BAD_REQUEST);
            }

            Optional<Post> byId = postRepo.findById(postDto.getPostId());
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
            }

            Post post = byId.get();
            post.getLikes().remove(user);
            postRepo.save(post);

            return new ResponseEntity<>("Unlike post Done", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
