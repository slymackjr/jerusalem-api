package com.jerusalem.jerusalem_api.data.dto;

import java.util.Date;

public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String role;
    private String token;
    private long expiresIn;
    private Date createdAt;
    private Date updatedAt;

    public UserResponse() {}

    public UserResponse(String userId, String name, String email, String role, String token, long expiresIn, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ✅ Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
