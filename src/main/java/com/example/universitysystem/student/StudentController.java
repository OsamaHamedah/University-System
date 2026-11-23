package com.example.universitysystem.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public StudentResponseDto getStudent(@PathVariable("studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto registerNewStudent(@RequestBody StudentRequestDto request) {
        return studentService.addNewStudent(request);
    }

    @PutMapping(path = "{studentId}")
    public StudentResponseDto updateStudent(@PathVariable("studentId") Long studentId,
                                            @RequestBody StudentRequestDto request) {
        return studentService.updateStudent(studentId, request);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
}
