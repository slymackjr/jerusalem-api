package com.jerusalem.jerusalem_api.data.dto;

import com.jerusalem.jerusalem_api.data.vo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String role;
    private String token;
    private long expiresIn;
    private Date createdAt;
    private Date updatedAt;

}
