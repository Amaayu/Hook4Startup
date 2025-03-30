package com.hook4startup.repository;

import com.hook4startup.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPosts_Id(String id);

}
