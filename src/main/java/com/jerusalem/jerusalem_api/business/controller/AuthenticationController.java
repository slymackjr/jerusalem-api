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
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserDto) {
        logger.info("Signup request received : {}", registerUserDto);
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            if (registeredUser == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user");
            }
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            logger.error("Signup failed for : {}. Error: {}", registerUserDto, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Signup process failed", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody UserRequest loginUserDto) {

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
                    jwtService.getExpirationTime(), // Ensure this method exists
                    authenticatedUser.getCreatedAt(),
                    authenticatedUser.getUpdatedAt()
            );

            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            logger.error("Login failed for : {}. Error: {}", loginUserDto, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login process failed", e);
        }
    }
}
