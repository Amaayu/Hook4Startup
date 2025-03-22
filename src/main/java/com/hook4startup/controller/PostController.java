package com.hook4startup.controller;

import com.hook4startup.Dto.PostDto;
import com.hook4startup.models.Post;
import com.hook4startup.models.User;
import com.hook4startup.models.UserProfile;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.PostRepo;
import com.hook4startup.repository.UserProfileRepo;
import com.hook4startup.services.PostServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private PostServices postServices;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PostRepo postRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("User is authenticated");
            String nameofUser = authentication.getName();

            Optional<User> userOptional = customerRepo.findByUsername(nameofUser);
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();

            if (!user.isMakeProfileStatus()) {
                System.out.println("UserProfile not found, please create it");
                return new ResponseEntity<>("UserProfile not found, please create it", HttpStatus.BAD_REQUEST);
            }
            UserProfile userProfileByUserId = userProfileRepo.findUserProfileByUserId(user.getId());
            // ✅ Corrected: Use user.getId() instead of user object
            Post newPost = new Post();
            newPost.setPostId(new ObjectId().toString());
            newPost.setUserId(user);  // ✅ Store only the String ID
            newPost.setContent(postDto.getContent());
            newPost.setUsername(user.getUsername());
            newPost.setProfileImageUrl(null);
            postServices.PostSave(newPost);
            newPost.setProfileImageUrl(userProfileByUserId.getProfilePictureUrl());
            postServices.postUpdate(newPost);

            user.getPosts().add(newPost);
            customerRepo.save(user);


            userProfileByUserId.setNumberOfPosts(postRepo.findByUserId(user.getId()));
            userProfileRepo.save(userProfileByUserId);

            return new ResponseEntity<>("Created Done", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update post by user
    @PutMapping("/update")
    public ResponseEntity<?> PostUpdate(@RequestBody PostDto postDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String nameofUser = authentication.getName();
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
            post.setContent(postDto.getContent());
            postServices.postUpdate(post);

            return new ResponseEntity<>("Update successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        try {
            List<Post> postList = postRepo.findAll();
            System.out.println("🔥 Post List: " + postList);

            if (postList.isEmpty()) {
                System.out.println("⚠ No posts found!");
                return ResponseEntity.noContent().build();
            }

            List<PostDto> postDTOList = postList.stream()
                    .map(post -> {
                        if (post.getUserId() == null) {
                            System.out.println("⚠ Skipping post as user is not found for postId: " + post.getPostId());
                            return null;
                        }

                        User user = post.getUserId();  // ✅ Get User Object

                        PostDto dto = new PostDto(
                                user.getId(),                             // ✅ userId
                                post.getPostId(),                         // ✅ postId
                                user.getUsername(),                       // ✅ username
                                post.getContent(),                        // ✅ content
                                post.getProfileImageUrl() != null ? post.getProfileImageUrl() : "default-profile-url",
                                post.getImageUrl() != null ? post.getImageUrl() : "default-post-url",
                                post.getComments() != null ? post.getComments().size() : 0,  // ✅ Number of Comments
                                post.getLikes() != null ? post.getLikes().size() : 0,        // ✅ Number of Likes
                                user.getPosts() != null ? user.getPosts().size() : 0,       // ✅ User's Total Posts
                                user.isMakeProfileStatus()                // ✅ Profile Status
                        );

                        System.out.println("✅ Post DTO Created: " + dto);
                        return dto;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            System.out.println("🚀 Final DTO List: " + postDTOList);
            return ResponseEntity.ok(postDTOList);
        } catch (Exception e) {
            System.out.println("❌ Error in fetching posts:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUserPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        String nameofUser = authentication.getName();

        // ✅ Remove redundant Optional wrapping
        Optional<User> userOptional = customerRepo.findByUsername(nameofUser);
              User user1 =userOptional.get();
              if (user1 == null) {
                  return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
              }
        try {
            List<Post> postList = postRepo.findByUserId(user1.getId());
            System.out.println("🔥 Post List: " + postList);

            if (postList.isEmpty()) {
                System.out.println("⚠ No posts found!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<PostDto> postDTOList = postList.stream().map(post -> {
                if (post.getUserId() == null) {
                    System.out.println("⚠ User not found for post: " + post.getPostId());
                    return null;
                }

                User user = post.getUserId();  // ✅ Directly get User Object from Post

                PostDto dto = new PostDto(
                        user.getId(),               // ✅ userId
                        post.getPostId(),           // ✅ postId
                        user.getUsername(),          // ✅ Post ID
                        post.getContent(),          // ✅ Post Content
                        post.getProfileImageUrl(),
                        post.getPostImageUrl(),
                        post.getComments().size(),  // ✅ Number of Comments
                        post.getLikes().size(),     // ✅ Number of Likes
                        user.getPosts().size(),     // ✅ User's Total Posts
                        user.isMakeProfileStatus()  // ✅ Profile Status
                );

                System.out.println("✅ Post DTO Created: " + dto);
                return dto;
            }).filter(Objects::nonNull).collect(Collectors.toList());

            System.out.println("🚀 Final DTO List: " + postDTOList);
            return new ResponseEntity<>(postDTOList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("❌ Error in fetching posts:");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
