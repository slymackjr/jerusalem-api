package com.jerusalem.jerusalem_api.business.service;


import com.jerusalem.jerusalem_api.data.dao.UserRepository;
import com.jerusalem.jerusalem_api.data.dto.UserRequest;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String login(UserRequest loginRequest) {
        // Find user by userId
        User user = userRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

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
            default -> throw new RuntimeException("Invalid role");
        };
    }

    public void logout() {
        // Implement logout logic (e.g., invalidate session/token)
    }
}