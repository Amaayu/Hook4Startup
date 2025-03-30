package com.hook4startup.models;

import java.io.Serializable;

public class MeetupNotificationWrapper implements Serializable {
    private MeetupNotification notification;
    private User user;

    // Constructor
    public MeetupNotificationWrapper(MeetupNotification notification, User user) {
        this.notification = notification;
        this.user = user;
    }

    // Getters and Setters
    public MeetupNotification getNotification() {
        return notification;
    }

    public void setNotification(MeetupNotification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

