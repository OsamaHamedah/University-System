package com.example.universitysystem.enrollment;

public record ScheduleItemDto(Long offeredClassId, String semester, String section, String schedule,
                              String courseCode, String courseName, Integer credits,
                              Long teacherId, String teacherName) {
}