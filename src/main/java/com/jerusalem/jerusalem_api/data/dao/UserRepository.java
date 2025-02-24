package com.jerusalem.jerusalem_api.data.dao;

import com.jerusalem.jerusalem_api.data.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by userId
    Optional<User> findByUserId(String userId);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if a user exists by userId
    boolean existsByUserId(String userId);

    // Check if a user exists by email
    boolean existsByEmail(String email);

}
