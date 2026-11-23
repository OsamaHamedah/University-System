package com.example.universitysystem.student;

import java.time.LocalDate;

public record StudentResponseDto(Long id, String name, String email, LocalDate dob, Integer age,
                                 Long departmentId, String departmentName) {
}