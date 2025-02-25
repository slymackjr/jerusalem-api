package com.jerusalem.jerusalem_api.data.vo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", unique = true, nullable = false)
    private String className;

    @OneToMany(mappedBy = "classId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
