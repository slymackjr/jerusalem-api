package com.jerusalem.jerusalem_api.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role; // Roles: ADMIN, STUDENT, TEACHER, ACCOUNTANT, BUS_CONDUCTOR
    private Date updatedAt;
    private Date createdAt;
}