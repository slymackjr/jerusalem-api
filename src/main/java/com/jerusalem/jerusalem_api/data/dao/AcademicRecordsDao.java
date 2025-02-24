package com.jerusalem.jerusalem_api.data.dao;

import com.jerusalem.jerusalem_api.data.vo.AcademicRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicRecordsDao {

    @Autowired
    private AcademicRecordsRepository academicRecordsRepository;

    // Get all academic records
    public List<AcademicRecords> getAllAcademicRecords() {
        return academicRecordsRepository.findAll();
    }

    // Get academic records by student ID
    public AcademicRecords getAcademicRecordsByStudentId(Long studentId) {
        return academicRecordsRepository.findByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("Academic records not found for student ID: " + studentId));
    }

    // Add a new academic record
    public AcademicRecords addAcademicRecords(AcademicRecords academicRecords) {
        return academicRecordsRepository.save(academicRecords);
    }

    // Update academic records (only by teachers)
    public AcademicRecords updateAcademicRecords(Long id, AcademicRecords academicRecordsDetails) {
        AcademicRecords academicRecords = academicRecordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic records not found with ID: " + id));
        academicRecords.setSubjectResults(academicRecordsDetails.getSubjectResults());
        academicRecords.setAverage(academicRecordsDetails.getAverage());
        academicRecords.setGrades(academicRecordsDetails.getGrades());
        academicRecords.setRemark(academicRecordsDetails.getRemark());
        return academicRecordsRepository.save(academicRecords);
    }

    // Delete academic records
    public void deleteAcademicRecords(Long id) {
        academicRecordsRepository.deleteById(id);
    }
}
