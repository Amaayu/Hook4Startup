package com.hook4startup.repository;

import com.hook4startup.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment,String> {
    List<Comment> findByUserId(String userId);
}
