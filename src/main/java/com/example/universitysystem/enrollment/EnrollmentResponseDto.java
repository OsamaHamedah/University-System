package com.example.universitysystem.enrollment;

public record EnrollmentResponseDto(Long studentId, String studentName,
                                    Long offeredClassId, String courseCode, String courseName,
                                    String semester, String section,
                                    int enrolledCount, Integer capacity, String message) {
}