package com.example.universitysystem.teacher;

import com.example.universitysystem.department.Department;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toEntity(TeacherRequestDto dto, Department department) {
        return new Teacher(dto.name(), dto.email(), department);
    }

    public TeacherResponseDto toResponse(Teacher teacher) {
        Department department = teacher.getDepartment();
        return new TeacherResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                department != null ? department.getId() : null,
                department != null ? department.getName() : null
        );
    }
}