package com.jerusalem.jerusalem_api.business.controller;


import com.jerusalem.jerusalem_api.business.service.AuthenticationService;
import com.jerusalem.jerusalem_api.business.service.JwtService;
import com.jerusalem.jerusalem_api.data.dto.UserRequest;
import com.jerusalem.jerusalem_api.data.dto.UserResponse;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRequest registerUserDto) {
        logger.info("Signup request received: {}", registerUserDto);
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            if (registeredUser == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user");
            }

            // Create a response map
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("userId", registeredUser.getUserId());
            response.put("email", registeredUser.getEmail());
            response.put("role", registeredUser.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Signup failed for: {}. Error: {}", registerUserDto, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody UserRequest loginUserDto) {
        logger.info("Login request received for email: {}", loginUserDto);

        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            if (authenticatedUser == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
            }
            String jwtToken = jwtService.generateToken(authenticatedUser);
            if (jwtToken == null || jwtToken.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate JWT token");
            }

            UserResponse userResponse = new UserResponse(
                    authenticatedUser.getUserId(),
                    authenticatedUser.getName(),
                    authenticatedUser.getEmail(),
                    authenticatedUser.getRole(),
                    jwtToken,
                    jwtService.getExpirationTime(),
                    authenticatedUser.getCreatedAt(),
                    authenticatedUser.getUpdatedAt()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", userResponse.getToken());
            response.put("role", userResponse.getRole());
            response.put("user", userResponse); // Or extract specific fields

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Login failed for : {}. Error: {}", loginUserDto, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
