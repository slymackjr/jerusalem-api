package com.jerusalem.jerusalem_api.data.vo;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name", unique = true, nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classId;
}