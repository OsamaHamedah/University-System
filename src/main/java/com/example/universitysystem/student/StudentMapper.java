package com.example.universitysystem.student;

import com.example.universitysystem.department.Department;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDto dto, Department department) {
        Student student = new Student(dto.name(), dto.email(), dto.dob());
        student.setDepartment(department);
        return student;
    }

    public StudentResponseDto toResponse(Student student) {
        Department department = student.getDepartment();
        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob(),
                student.getAge(),
                department != null ? department.getId() : null,
                department != null ? department.getName() : null
        );
    }

    public StudentSummaryDto toSummary(Student student) {
        return new StudentSummaryDto(student.getId(), student.getName());
    }
}