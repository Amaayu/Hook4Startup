package com.hook4startup.controller;

import com.hook4startup.models.MeetupNotification;
import com.hook4startup.models.User;
import com.hook4startup.models.UserProfile;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.MeetupRepo;
import com.hook4startup.repository.UserProfileRepo;
import com.hook4startup.services.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/notify")
public class MeetupController {

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private MeetupRepo meetupRepo;

    @PostMapping("meetup/notification")
    public ResponseEntity<?> sendMeetupNotify(@RequestBody MeetupNotification notification) {
        try {
            // Get authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String username = authentication.getName(); // Get username of authenticated user
            Optional<User> userOptional = customerRepo.findByUsername(username);

            // Check if user exists
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();

            // Check if profile exists
            if (!user.isMakeProfileStatus()) {
                return new ResponseEntity<>("User profile not created, please complete your profile", HttpStatus.BAD_REQUEST);
            }

            // Get user profile
            UserProfile userProfile = userProfileRepo.findUserProfileByUsername(username);

            // Set authenticated user's details in the notification
            notification.setHook_userId(user.getId()); // Set User ID
            notification.setUsername(user.getUsername()); // Set username
            notification.setDpUrl(userProfile.getProfilePictureUrl());// Set profile picture URL
            notification.setName(userProfile.getFullName());

            // Send notification to RabbitMQ
            Boolean rwq = rabbitMqSender.send_meetup_Notify(notification , user);
           if (rwq)
            return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
           return new ResponseEntity<>("Notification sent do not successfully", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/notifications")
    public ResponseEntity<?> Aal_Notify () {
        try {
            // Get authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String username = authentication.getName(); // Get username of authenticated user
            Optional<User> userOptional = customerRepo.findByUsername(username);

            // Check if user exists
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            User user = userOptional.get();
           List<MeetupNotification>  notifyById = meetupRepo.findAllByPostCreateUserId(user.getId());

            return new ResponseEntity<>(notifyById, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}