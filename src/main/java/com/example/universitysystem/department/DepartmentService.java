package com.example.universitysystem.department;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
    }

    public void addNewDepartment(Department department) {
        if(departmentRepository.findDepartmentByName(department.getName()).isPresent()) {
            throw new IllegalStateException("Department with name " + department.getName() + " already exists");
        }
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new IllegalStateException("Department with id " + departmentId + " not found");
        }
        departmentRepository.deleteById(departmentId);
    }

    @Transactional
    public void updateDepartment(Long departmentId, Department updatedDepartment) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
        String name = updatedDepartment.getName();
        String location = updatedDepartment.getLocation();

        if (location != null && !location.isEmpty() && !Objects.equals(department.getLocation() , location)) {
            department.setLocation(location);
        }
        if(name != null && !name.isEmpty() && !Objects.equals(department.getName() , name)) {
            if (departmentRepository.findDepartmentByName(name).isPresent()) {
                throw new IllegalStateException("Department with name " + name + " already exists");
            }
            department.setName(name);

        }

    }

}
