package com.jerusalem.jerusalem_api.data.vo;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subject_results")
public class SubjectResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "academic_records_id", nullable = false)
    private AcademicRecords academicRecords;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject; // The subject

    private Double score; // Score for the subject
    private String grade; // Grade for the subject (e.g., A, B, C)
}
