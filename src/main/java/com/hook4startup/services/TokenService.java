package com.hook4startup.services;

import com.hook4startup.models.SessionToken;
import com.hook4startup.models.User;
import com.hook4startup.repository.CustomerRepo;
import com.hook4startup.repository.SessionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    @Autowired
    CustomerRepo customerRepo;

    public SessionToken generateToken(String username , String id) {
        String token = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusSeconds(86400); // 1 din ka expiry

        // finde user
        Optional<User> user =  customerRepo.findByUsername(username);
        User userObj = user.get();
        // Pehle se token exist hai to use update karo, warna naya save karo
        Optional<SessionToken> existingToken = sessionTokenRepository.findById(id);
        System.out.println(username);
        System.out.println(expiry);
        if (existingToken.isPresent()) {
            SessionToken tokenObj = existingToken.get();
            System.out.println(tokenObj+" this is token");
            System.out.println(userObj+" this is user");
            tokenObj = new SessionToken(token, username, expiry);
            userObj.setSessionTokenId(tokenObj);
            customerRepo.save(userObj);
            sessionTokenRepository.save(tokenObj);
            return tokenObj;
        }
        SessionToken tokenObj = new SessionToken(token, username, expiry);
               userObj.setSessionTokenId(tokenObj);
                customerRepo.save(userObj);
            sessionTokenRepository.save(tokenObj);



        return tokenObj;
    }

    public Optional<String> validateToken(String token) {
        Optional<SessionToken> sessionToken = sessionTokenRepository.findByToken(token);
        if (sessionToken.isPresent() && sessionToken.get().getExpiry().isAfter(Instant.now())) {
            System.out.println(sessionToken.get().getUsername());
            return Optional.of(sessionToken.get().getUsername());
        }


        return Optional.empty();
    }
}
