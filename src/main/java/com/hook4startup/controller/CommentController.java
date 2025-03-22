package com.hook4startup.controller;

import com.hook4startup.Dto.CommentDto;
import com.hook4startup.models.Comment;
import com.hook4startup.models.Post;
import com.hook4startup.models.User;
import com.hook4startup.repository.CommentRepo;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.PostRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    Comment newComment;
    @Autowired
    CommentRepo commentRepo;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> commentPost(@RequestBody CommentDto comment) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            String nameofUser = authentication.getName();

            // ✅ Remove redundant Optional wrapping
            Optional<User> userOptional = customerRepo.findByUsername(nameofUser);
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();

            if (!user.isMakeProfileStatus()) {
                return new ResponseEntity<>("UserProfile not found, please create one", HttpStatus.BAD_REQUEST);
            }

            Optional<Post> byId = postRepo.findById(comment.getPostId());
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
            }

            Comment newComment = new Comment(); // ✅ Create new comment instance
            newComment.setCommentId(new ObjectId().toString());
            newComment.setPostId(byId.get());
            newComment.setContent(comment.getContent());
            newComment.setUserId(user);
            commentRepo.save(newComment);

            Post post = byId.get();
            post.getComments().add(newComment);
            postRepo.save(post);

            return new ResponseEntity<>("Comment created successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
