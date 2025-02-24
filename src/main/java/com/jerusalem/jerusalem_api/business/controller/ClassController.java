package com.jerusalem.jerusalem_api.business.controller;

import com.jerusalem.jerusalem_api.data.dao.ClassDao;
import com.jerusalem.jerusalem_api.data.vo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassDao classDao;

    // Get all classes
    @GetMapping
    public List<Class> getAllClasses() {
        return classDao.getAllClasses();
    }

    // Add a new class
    @PostMapping
    public Class addClass(@RequestBody Class classObj) {
        return classDao.addClass(classObj);
    }

    // Get a class by ID
    @GetMapping("/{id}")
    public Class getClassById(@PathVariable Long id) {
        return classDao.getClassById(id);
    }

    // Update a class
    @PutMapping("/{id}")
    public Class updateClass(@PathVariable Long id, @RequestBody Class classDetails) {
        return classDao.updateClass(id, classDetails);
    }

    // Delete a class
    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Long id) {
        classDao.deleteClass(id);
    }
}