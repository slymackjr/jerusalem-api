package com.jerusalem.jerusalem_api.business.service;


import com.jerusalem.jerusalem_api.data.dao.UserRepository;
import com.jerusalem.jerusalem_api.data.dto.UserRequest;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserRequest input) {

        logger.info("Processing signup for: {}", input);

        try {
            User user = new User();
            user.setName(input.getName());
            user.setEmail(input.getEmail());
            user.setRole(input.getRole());
            user.setPassword(passwordEncoder.encode(input.getPassword()));

            User savedUser = userRepository.save(user);
            if (savedUser == null) {
                throw new RuntimeException("Failed to save user during signup");
            }
            return savedUser;
        } catch (Exception e) {
            logger.error("Signup failed for: {}. Error: {}", input, e.getMessage(), e);
            throw new RuntimeException("Signup process failed", e);
        }
    }

    public User authenticate(UserRequest input) {
        logger.info("Authenticating user: {}", input);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
            Optional<User> userOptional = userRepository.findByEmail(input.getEmail());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found after successful authentication");
            }
            User user = userOptional.get();
            return user;
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Authentication failed for: {}. Error: {}", input, e.getMessage(), e);
            throw new RuntimeException("Authentication process failed", e);
        }
    }
}
