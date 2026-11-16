package com.example.universitysystem.course;

import jakarta.persistence.*;

@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private Integer credits;
    public Course() {
    }
    public Course(String name, String code, Integer credits) {
        this.name=name;
        this.code=code;
        this.credits=credits;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }


}
