package com.jerusalem.jerusalem_api.business.controller;


import com.jerusalem.jerusalem_api.business.service.AuthenticationService;
import com.jerusalem.jerusalem_api.business.service.JwtService;
import com.jerusalem.jerusalem_api.data.dto.UserRequest;
import com.jerusalem.jerusalem_api.data.dto.UserResponse;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody UserRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

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
    }
}
