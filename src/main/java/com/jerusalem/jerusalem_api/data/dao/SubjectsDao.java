package com.jerusalem.jerusalem_api.data.dao;


import com.jerusalem.jerusalem_api.data.vo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsDao {

    @Autowired
    private SubjectsRepository subjectsRepository;

    // Get all subjects
    public List<Subject> getAllSubjects() {
        return subjectsRepository.findAll();
    }

    // Add a new subject
    public Subject addSubject(Subject subject) {
        return subjectsRepository.save(subject);
    }

    // Get a subject by ID
    public Subject getSubjectById(Long id) {
        return subjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    // Update a subject
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = subjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        subject.setSubjectName(subjectDetails.getSubjectName());
        return subjectsRepository.save(subject);
    }

    // Delete a subject
    public void deleteSubject(Long id) {
        subjectsRepository.deleteById(id);
    }
}
