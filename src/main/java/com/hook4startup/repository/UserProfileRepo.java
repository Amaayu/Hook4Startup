package com.hook4startup.repository;

import com.hook4startup.models.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepo extends MongoRepository<UserProfile, String> {
    UserProfile  findUserProfileByUserId(String userId);
    UserProfile findUserProfileByUsername(String username);
}
