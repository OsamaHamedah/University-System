package com.example.universitysystem.course;

public record CourseRequestDto(String name, String code, Integer credits, Long departmentId) {
}