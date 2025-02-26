package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.ClassDao;
import com.jerusalem.jerusalem_api.data.vo.Class;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassDao classDao;

    // Get all classes
    @GetMapping
    public List<Class> getAllClasses() {
        logger.info("Fetching all classes");
        return classDao.getAllClasses();
    }

    // Add a new class
    @PostMapping
    public Class addClass(@RequestBody Class classObj) {
        logger.info("Adding new class: {}", classObj);
        try {
            Class savedClass = classDao.addClass(classObj);
            if (savedClass == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add class");
            }

            return savedClass;
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding class", e);
        }
    }

    // Get a class by ID
    @GetMapping("/{id}")
    public Class getClassById(@PathVariable Long id) {
        logger.info("Fetching class with ID: {}", id);
        Class classObj = classDao.getClassById(id);
        if (classObj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found for ID: " + id);
        }
        return classObj;
    }

    // Update a class
    @PutMapping("/{id}")
    public Class updateClass(@PathVariable Long id, @RequestBody Class classDetails) {
        logger.info("Updating class with ID: {}, Class: {}", id , classDetails);
        Class updatedClass = classDao.updateClass(id, classDetails);
        if (updatedClass == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found or update failed for ID: " + id);
        }
        return updatedClass;
    }

    // Delete a class
    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Long id) {
        logger.info("Deleting class with ID: {}", id);
        try {
            classDao.deleteClass(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete class", e);
        }
    }
}