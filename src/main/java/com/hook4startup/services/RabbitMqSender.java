package com.hook4startup.services;

import com.hook4startup.models.MeetupNotification;
import com.hook4startup.models.MeetupNotificationWrapper;
import com.hook4startup.models.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMqSender {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    Queue queue;

    public  Boolean  send_meetup_Notify(MeetupNotification notification , User user){
        // Wrap the objects in a wrapper
        MeetupNotificationWrapper wrapper = new MeetupNotificationWrapper(notification, user);
        rabbitTemplate.convertAndSend(queue.getName(),wrapper);
                return  true;
    }

}
