package com.jerusalem.jerusalem_api.business.controller;


import com.jerusalem.jerusalem_api.data.dao.AcademicRecordsDao;
import com.jerusalem.jerusalem_api.data.vo.AcademicRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/academic-records")
public class AcademicRecordsController {

    Logger logger = LoggerFactory.getLogger(AcademicRecordsController.class);

    @Autowired
    private AcademicRecordsDao academicRecordsDao;

    // Get all academic records
    @GetMapping
    public List<AcademicRecords> getAllAcademicRecords() {
        logger.info("Fetching all academic records");
        return academicRecordsDao.getAllAcademicRecords();
    }

    // Get academic records by student ID
    @GetMapping("/student/{studentId}")
    public AcademicRecords getAcademicRecordsByStudentId(@PathVariable Long studentId) {
        logger.info("Fetching academic records for student ID: {}", studentId);
        AcademicRecords records = academicRecordsDao.getAcademicRecordsByStudentId(studentId);
        if (records == null) {
            logger.error("No academic records found for student ID: {}", studentId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic records not found for student ID: " + studentId);
        }
        return records;
    }

    // Add a new academic record (for students)
    @PostMapping
    public AcademicRecords addAcademicRecords(@RequestBody AcademicRecords academicRecords) {
        logger.info("Adding new academic record for student ID: {}", academicRecords);
        try {
            AcademicRecords savedRecord = academicRecordsDao.addAcademicRecords(academicRecords);
            logger.info("Successfully added academic record with ID: {}", savedRecord.getId());
            return savedRecord;
        } catch (Exception e) {
            logger.error("Failed to add academic record Error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add academic record", e);
        }
    }

    // Update academic records (for teachers)
    @PutMapping("/{id}")
    public AcademicRecords updateAcademicRecords(@PathVariable Long id, @RequestBody AcademicRecords academicRecordsDetails) {
        logger.info("Updating academic record with ID: {} and with Details: {}", id, academicRecordsDetails);
        AcademicRecords updatedRecord = academicRecordsDao.updateAcademicRecords(id, academicRecordsDetails);
        if (updatedRecord == null) {
            logger.error("Failed to update academic record with ID: {} - record not found or update failed", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic record not found or update failed for ID: " + id);
        }
        logger.info("Successfully updated academic record with ID: {}", id);
        return updatedRecord;
    }

    // Delete academic records
    @DeleteMapping("/{id}")
    public void deleteAcademicRecords(@PathVariable Long id) {
        logger.info("Deleting academic record with ID: {}", id);
        try {
            academicRecordsDao.deleteAcademicRecords(id);
            logger.info("Successfully deleted academic record with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete academic record with ID: {}. Error: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete academic record", e);
        }
    }
}