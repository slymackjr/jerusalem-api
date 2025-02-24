package com.jerusalem.jerusalem_api.data.dao;

import com.jerusalem.jerusalem_api.data.vo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassDao {

    @Autowired
    private ClassRepository classRepository;

    // Get all classes
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    // Add a new class
    public Class addClass(Class classObj) {
        return classRepository.save(classObj);
    }

    // Get a class by ID
    public Class getClassById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }

    // Update a class
    public Class updateClass(Long id, Class classDetails) {
        Class classObj = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        classObj.setClassName(classDetails.getClassName());
        return classRepository.save(classObj);
    }

    // Delete a class
    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}