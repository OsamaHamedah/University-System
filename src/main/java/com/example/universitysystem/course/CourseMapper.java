package com.example.universitysystem.course;

import com.example.universitysystem.department.Department;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto dto, Department department) {
        Course course = new Course(dto.name(), dto.code(), dto.credits());
        course.setDepartment(department);
        return course;
    }

    public CourseResponseDto toResponse(Course course) {
        Department department = course.getDepartment();
        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getCredits(),
                department != null ? department.getId() : null,
                department != null ? department.getName() : null
        );
    }
}