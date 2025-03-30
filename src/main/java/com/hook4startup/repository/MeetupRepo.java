package com.hook4startup.repository;

import com.hook4startup.models.MeetupNotification;
import com.hook4startup.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MeetupRepo extends MongoRepository<MeetupNotification, String> {
    MeetupNotification findNotifyById(String id);

    @Query("{'postCreate_userId': ?0}")
    List<MeetupNotification> findAllByPostCreateUserId(String id);


}
