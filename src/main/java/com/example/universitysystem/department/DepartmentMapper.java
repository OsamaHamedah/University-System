package com.example.universitysystem.department;

import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDto dto) {
        return new Department(dto.name(), dto.location());
    }

    public DepartmentResponseDto toResponse(Department department) {
        return new DepartmentResponseDto(department.getId(), department.getName(), department.getLocation());
    }
}