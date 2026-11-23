package com.example.universitysystem.department;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentResponseDto> getDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toResponse).toList();
    }

    public DepartmentResponseDto getDepartment(Long departmentId) {
        return departmentMapper.toResponse(findDepartment(departmentId));
    }

    public DepartmentResponseDto addNewDepartment(DepartmentRequestDto request) {
        if (departmentRepository.findDepartmentByName(request.name()).isPresent()) {
            throw new IllegalStateException("Department with name " + request.name() + " already exists");
        }
        Department saved = departmentRepository.save(departmentMapper.toEntity(request));
        return departmentMapper.toResponse(saved);
    }

    public void deleteDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new IllegalStateException("Department with id " + departmentId + " not found");
        }
        departmentRepository.deleteById(departmentId);
    }

    @Transactional
    public DepartmentResponseDto updateDepartment(Long departmentId, DepartmentRequestDto request) {
        Department department = findDepartment(departmentId);

        String name = request.name();
        String location = request.location();

        if (location != null && !location.isEmpty() && !Objects.equals(department.getLocation() , location)) {
            department.setLocation(location);
        }
        if(name != null && !name.isEmpty() && !Objects.equals(department.getName() , name)) {
            if (departmentRepository.findDepartmentByName(name).isPresent()) {
                throw new IllegalStateException("Department with name " + name + " already exists");
            }
            department.setName(name);
        }
        return departmentMapper.toResponse(department);
    }
    private Department findDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
    }
}
