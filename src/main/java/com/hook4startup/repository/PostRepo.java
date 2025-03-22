package com.hook4startup.repository;

import com.hook4startup.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepo extends MongoRepository<Post, String> {

    // Find posts by user ID
    List<Post> findByUserId(String userId);
    Post findByusername(String username);


}
