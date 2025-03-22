package com.hook4startup.repository;

import com.hook4startup.models.SessionToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SessionTokenRepository extends MongoRepository<SessionToken, String> {
    Optional<SessionToken> findByToken(String token);
    Optional<SessionToken> findByUsername(String username);

    // ✅ Custom Query to Update Token by Username
    @Transactional
    @Query("{ 'username' : ?0 }")
    void updateTokenByUsername(String username, String newToken);
}
