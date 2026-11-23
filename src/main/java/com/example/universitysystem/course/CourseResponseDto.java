package com.example.universitysystem.course;

public record CourseResponseDto(Long id, String name, String code, Integer credits,
                                Long departmentId, String departmentName) {
}