package com.example.universitysystem.offeredclass;

import com.example.universitysystem.student.StudentSummaryDto;

import java.util.List;

public record OfferedClassResponseDto(Long id, String semester, String section, Integer capacity, String schedule,
                                      Long courseId, String courseName, String courseCode,
                                      Long teacherId, String teacherName,
                                      int enrolledCount, List<StudentSummaryDto> students) {
}