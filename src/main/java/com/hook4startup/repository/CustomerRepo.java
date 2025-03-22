package com.hook4startup.repository;
import com.hook4startup.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CustomerRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

}
