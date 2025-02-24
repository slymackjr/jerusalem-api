package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.UserDao;
import com.jerusalem.jerusalem_api.data.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userDao.getUserById(id);
    }

    // Add a new user
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userDao.addUser(user);
    }

    // Update a user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userDao.updateUser(id, userDetails);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
    }
}