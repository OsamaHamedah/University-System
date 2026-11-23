package com.example.universitysystem.teacher;

public record TeacherResponseDto(Long id, String name, String email, Long departmentId, String departmentName) {
}