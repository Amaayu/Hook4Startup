package com.hook4startup.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupNotification {


    @Id
    private String id;
    private String postId;
    private String postCreate_userId;// User ID
    private String hook_userId;     // user send hook message
    private String username;        // Username of the user
    private String name;            // Full name of the user
    private String dpUrl;           // User's profile picture URl
    private String status;          // Status: "Interested You" or "Deal Done"
    @DBRef
    private List<User> notifications = new ArrayList<>();// List of all notifications for counting
}
