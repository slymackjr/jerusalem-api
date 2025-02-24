package com.jerusalem.jerusalem_api.business.controller;


import com.jerusalem.jerusalem_api.data.dao.AcademicRecordsDao;
import com.jerusalem.jerusalem_api.data.vo.AcademicRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-records")
public class AcademicRecordsController {

    @Autowired
    private AcademicRecordsDao academicRecordsDao;

    // Get all academic records
    @GetMapping
    public List<AcademicRecords> getAllAcademicRecords() {
        return academicRecordsDao.getAllAcademicRecords();
    }

    // Get academic records by student ID
    @GetMapping("/student/{studentId}")
    public AcademicRecords getAcademicRecordsByStudentId(@PathVariable Long studentId) {
        return academicRecordsDao.getAcademicRecordsByStudentId(studentId);
    }

    // Add a new academic record (for students)
    @PostMapping
    public AcademicRecords addAcademicRecords(@RequestBody AcademicRecords academicRecords) {
        return academicRecordsDao.addAcademicRecords(academicRecords);
    }

    // Update academic records (for teachers)
    @PutMapping("/{id}")
    public AcademicRecords updateAcademicRecords(@PathVariable Long id, @RequestBody AcademicRecords academicRecordsDetails) {
        return academicRecordsDao.updateAcademicRecords(id, academicRecordsDetails);
    }

    // Delete academic records
    @DeleteMapping("/{id}")
    public void deleteAcademicRecords(@PathVariable Long id) {
        academicRecordsDao.deleteAcademicRecords(id);
    }
}