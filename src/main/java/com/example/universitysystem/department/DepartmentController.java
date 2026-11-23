package com.example.universitysystem.department;

import org.springframework.http.HttpStatus;
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
    public List<DepartmentResponseDto> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping(path = "{departmentId}")
    public DepartmentResponseDto getDepartment(@PathVariable("departmentId") Long departmentId) {
        return departmentService.getDepartment(departmentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponseDto registerNewDepartment(@RequestBody DepartmentRequestDto request) {
        return departmentService.addNewDepartment(request);
    }

    @PutMapping(path = "{departmentId}")
    public DepartmentResponseDto updateDepartment(@PathVariable("departmentId") Long departmentId,
                                                  @RequestBody DepartmentRequestDto request) {
        return departmentService.updateDepartment(departmentId, request);
    }

    @DeleteMapping(path = "{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
