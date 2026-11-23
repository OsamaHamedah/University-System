package com.example.universitysystem.student;

import com.example.universitysystem.department.Department;
import com.example.universitysystem.offeredclass.OfferedClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    @Transient
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private Set<OfferedClass> offeredClasses = new HashSet<>();

    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        /*this.age = age;

         */
    }

    public Student(String name,
                   String email,
                   LocalDate dob /*,
                   Integer age*/) {
        this.name = name;
        this.email = email;
        this.dob = dob;
       /* this.age = age;

        */
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /* public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    } */

    public Integer getAge() {
        if (this.dob == null) {
            return null;
        }
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

     public void setAge(Integer age) {
        this.age = age;
    }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }

    public Set<OfferedClass> getOfferedClasses() { return offeredClasses; }

    public void setOfferedClasses(Set<OfferedClass> offeredClasses) { this.offeredClasses = offeredClasses; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
