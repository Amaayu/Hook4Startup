package com.hook4startup.Component;

import com.hook4startup.models.MeetupNotification;
import com.hook4startup.models.MeetupNotificationWrapper;
import com.hook4startup.models.User;
import com.hook4startup.models.UserProfile;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.MeetupRepo;
import com.hook4startup.repository.PostRepo;
import com.hook4startup.repository.UserProfileRepo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RabbitListener(queues = "My_queue")
public class MessageReceiver {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private MeetupRepo meetupRepo;

    @RabbitHandler
    @Transactional
    public void receiveMessage( MeetupNotificationWrapper wrapper) {

        MeetupNotification notification = wrapper.getNotification();
        User user1 = wrapper.getUser();

        try {
            // Find user by username in notification
            Optional<User> userOptional = customerRepo.findByUsername(notification.getUsername());
            if (userOptional.isEmpty()) {
                System.out.println("User not found");
                return;
            }
            User user = userOptional.get();

            // Check if user has created a profile
            if (!user.isMakeProfileStatus()) {
                System.out.println("User profile not created");
                return;
            }

            // Get User Profile
            UserProfile userProfile1 = userProfileRepo.findUserProfileByUsername(notification.getUsername());

            // Find the user who created the post
            Optional<User> byId = customerRepo.findByPosts_Id(notification.getPostId());
            if (byId.isEmpty()) {
                System.out.println("Post not found");
                return;
            }

            // Create and save the notification
            MeetupNotification meetupNotification = new MeetupNotification();
            meetupNotification.setName(notification.getName());
            meetupNotification.setUsername(notification.getUsername());
            meetupNotification.setStatus("Interested You");
            meetupNotification.setPostId(notification.getPostId());
            meetupNotification.setName(notification.getName());
            //this user create post
            meetupNotification.setPostCreate_userId(byId.get().getId());
            meetupNotification.setHook_userId(notification.getHook_userId());
            meetupNotification.setDpUrl(userProfile1.getProfilePictureUrl());
           // meetupNotification.getNotifications().add(user1);
            meetupRepo.save(meetupNotification);

            System.out.println(meetupNotification);
        } catch (Exception e) {
            System.out.println("Error processing notification: " + e.getMessage());
        }
    }
}
