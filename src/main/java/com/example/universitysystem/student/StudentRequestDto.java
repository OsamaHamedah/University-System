package com.example.universitysystem.student;

import java.time.LocalDate;

public record StudentRequestDto(String name, String email, LocalDate dob, Long departmentId) {
}