package com.jerusalem.jerusalem_api.business.service;

import com.jerusalem.jerusalem_api.data.dao.UserRepository;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user details for email: {}", email);
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword()) // This should be the encoded password
                    .roles(user.getRole()) // Assuming Role is a String or Enum
                    .build();
        } catch (Exception e) {
            logger.error("Failed to load user details for email: {}. Error: {}", email, e.getMessage(), e);
            throw e; // Rethrow to ensure Spring Security handles it appropriately
        }
    }
}

