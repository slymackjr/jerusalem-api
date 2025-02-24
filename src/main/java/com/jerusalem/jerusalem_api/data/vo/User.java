package com.jerusalem.jerusalem_api.data.vo;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    private String name;
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role; // Roles: ADMIN, STUDENT, TEACHER, ACCOUNTANT, BUS_CONDUCTOR
}
