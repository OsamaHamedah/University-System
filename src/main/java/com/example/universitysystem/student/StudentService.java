package com.example.universitysystem.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Email is taken");
        }
        studentRepository.save(student);
        //System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("Student with id " + studentId + " not found");
        }
        studentRepository.deleteById(studentId);

    }
    @Transactional
    public void updateStudent(Long studentId, Student updatedStudent) {
    Student student = studentRepository.findById(studentId).
            orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

    String name = updatedStudent.getName();
    String email = updatedStudent.getEmail();

    if (name!=null && !name.isEmpty() && !Objects.equals(student.getName(), name)){
    student.setName(name);
    }
    if (email!=null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)){
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Email is taken");
        }
        student.setEmail(email);
    }
    }
}