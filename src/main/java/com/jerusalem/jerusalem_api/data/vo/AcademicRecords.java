package com.jerusalem.jerusalem_api.data.vo;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "academic_records")
public class AcademicRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classId;

    @OneToMany(mappedBy = "academicRecords", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubjectResult> subjectResults;

    private Double average;
    private String grades;
    private String remark;
}
