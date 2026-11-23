package com.example.universitysystem.offeredclass;

public record OfferedClassRequestDto(String semester, String section, Integer capacity, String schedule,
                                     Long courseId, Long teacherId) {
}