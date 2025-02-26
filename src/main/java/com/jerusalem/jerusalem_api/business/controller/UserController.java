package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.UserDao;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userDao.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for ID: " + id);
        }
        return user;
    }

    // Add a new user
    @PostMapping
    public User addUser(@RequestBody User user) {
        logger.info("Adding new user: {}", user);
        try {
            User savedUser = userDao.addUser(user);
            if (savedUser == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add user");
            }
            return savedUser;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding user", e);
        }
    }

    // Update a user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("Updating user with ID: {}, Details: {}", id , userDetails);
        User updatedUser = userDao.updateUser(id, userDetails);
        if (updatedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or update failed for ID: " + id);
        }
        return updatedUser;
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            userDao.deleteUser(id);
        } catch (Exception e) {
            logger.error("Failed to delete user with ID: {}. Error: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete user", e);
        }
    }
}