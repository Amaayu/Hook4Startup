package com.hook4startup.services;

import com.hook4startup.models.UserProfile;
import com.hook4startup.repository.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServices {
    @Autowired
    UserProfileRepo userProfileRepo;

    // Save userPost by created user
    @Transactional
    public void ProfileSave(UserProfile userProfile) {
        userProfileRepo.save(userProfile);

    }
    @Transactional
    public  boolean userProfileUpdate( UserProfile userProfileUpdatePost){
        userProfileRepo.save(userProfileUpdatePost);
        return true;
    }
}
