package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.SubjectsDao;
import com.jerusalem.jerusalem_api.data.vo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectsDao subjectsDao;

    // Get all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        logger.info("Fetching all subjects");
        return subjectsDao.getAllSubjects();
    }

    // Add a new subject
    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        logger.info("Adding new subject: {}", subject);
        try {
            Subject savedSubject = subjectsDao.addSubject(subject);
            if (savedSubject == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add subject");
            }
            return savedSubject;
        } catch (Exception e) {
            logger.error("Error adding subject: {}. Error: {}", subject, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding subject", e);
        }
    }

    // Get a subject by ID
    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Long id) {
        logger.info("Fetching subject with ID: {}", id);
        Subject subject = subjectsDao.getSubjectById(id);
        if (subject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found for ID: " + id);
        }
        return subject;
    }

    // Update a subject
    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        logger.info("Updating subject with ID: {} , subject: {}", id , subjectDetails);
        Subject updatedSubject = subjectsDao.updateSubject(id, subjectDetails);
        if (updatedSubject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found or update failed for ID: " + id);
        }
        return updatedSubject;
    }

    // Delete a subject
    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        logger.info("Deleting subject with ID: {}", id);
        try {
            subjectsDao.deleteSubject(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete subject", e);
        }
    }
}