package com.jerusalem.jerusalem_api.business.service;

import com.jerusalem.jerusalem_api.data.dao.UserRepository;
import com.jerusalem.jerusalem_api.data.dto.UserRequest;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    public String login(UserRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest);
        try {
            // Find user by userId
            User user = userRepository.findByUserId(loginRequest.getUserId())
                    .orElseThrow(() -> {
                        return new RuntimeException("User not found for ID: " + loginRequest.getUserId());
                    });

            // Verify password (use a password encoder in real applications)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            // Verify role
            if (!user.getRole().equals(loginRequest.getRole())) {
                throw new RuntimeException("Invalid role");
            }

            // Redirect based on role

            return switch (user.getRole()) {
                case "ADMIN" -> "/admin/dashboard";
                case "STUDENT" -> "/student/dashboard";
                case "TEACHER" -> "/teacher/dashboard";
                case "ACCOUNTANT" -> "/accountant/dashboard";
                case "BUS_CONDUCTOR" -> "/bus-conductor/dashboard";
                default -> {
                    throw new RuntimeException("Invalid role: " + user.getRole());
                }
            };

        } catch (Exception e) {
            logger.error("Login failed for user: {}. Error: {}", loginRequest, e.getMessage(), e);
            throw e; // Rethrow to let GlobalExceptionHandler handle it
        }
    }

    public void logout() {
        logger.info("Logout requested");
        try {
            // Implement logout logic (e.g., invalidate session/token)
            // For now, just log it since there's no session management here
            logger.debug("Logout processed successfully");
        } catch (Exception e) {
            logger.error("Logout failed. Error: {}", e.getMessage(), e);
            throw new RuntimeException("Logout process failed", e);
        }
    }
}