package com.example.universitysystem.enrollment;

import java.util.List;

public record StudentScheduleResponseDto(Long studentId, String studentName,
                                         int totalClasses, int totalCredits,
                                         List<ScheduleItemDto> classes) {
}