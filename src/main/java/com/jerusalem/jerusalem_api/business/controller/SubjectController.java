package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.SubjectsDao;
import com.jerusalem.jerusalem_api.data.vo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectsDao subjectsDao;

    // Get all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectsDao.getAllSubjects();
    }

    // Add a new subject
    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectsDao.addSubject(subject);
    }

    // Get a subject by ID
    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Long id) {
        return subjectsDao.getSubjectById(id);
    }

    // Update a subject
    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        return subjectsDao.updateSubject(id, subjectDetails);
    }

    // Delete a subject
    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectsDao.deleteSubject(id);
    }
}