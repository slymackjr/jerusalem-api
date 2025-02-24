package com.jerusalem.jerusalem_api.data.vo;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", unique = true, nullable = false)
    private String className;

    @OneToMany(mappedBy = "class", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;
}
