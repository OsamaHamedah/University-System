package com.example.universitysystem.department;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }
    @GetMapping(path = "{departmentId}")
    public Department getDepartment(@PathVariable("departmentId") Long departmentId) {
        return departmentService.getDepartment(departmentId);
    }
    @PostMapping
    public void registerNewDepartment(@RequestBody Department department) {
        departmentService.addNewDepartment(department);
    }

    @DeleteMapping(path = "{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
    @PutMapping(path = "{departmentId}")
    public void updateDepartment(@PathVariable("departmentId") Long departmentId, @RequestBody Department department) {
        departmentService.updateDepartment(departmentId, department);
    }

}
