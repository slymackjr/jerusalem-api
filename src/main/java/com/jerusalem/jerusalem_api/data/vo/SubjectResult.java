package com.jerusalem.jerusalem_api.data.vo;

import jakarta.persistence.*;

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
    private Subject subject;

    private Double score;
    private String grade;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AcademicRecords getAcademicRecords() {
        return academicRecords;
    }

    public void setAcademicRecords(AcademicRecords academicRecords) {
        this.academicRecords = academicRecords;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}