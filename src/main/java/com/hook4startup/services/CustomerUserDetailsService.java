package com.hook4startup.services;

import com.hook4startup.config.CustomUserDetails;
import com.hook4startup.models.User;
import com.hook4startup.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private CustomerRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Agar user exist nahi karta, to exception throw karein
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // `Optional<User>` se direct `User` object leke CustomUserDetails me pass karein
        return new CustomUserDetails(Optional.of(userOptional.get()));
    }
}
