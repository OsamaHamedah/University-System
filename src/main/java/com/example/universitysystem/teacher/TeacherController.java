package com.example.universitysystem.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherResponseDto> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping(path = "{teacherId}")
    public TeacherResponseDto getTeacher(@PathVariable("teacherId") Long teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponseDto registerNewTeacher(@RequestBody TeacherRequestDto request) {
        return teacherService.addNewTeacher(request);
    }

    @PutMapping(path = "{teacherId}")
    public TeacherResponseDto updateTeacher(@PathVariable("teacherId") Long teacherId,
                                            @RequestBody TeacherRequestDto request) {
        return teacherService.updateTeacher(teacherId, request);
    }

    @DeleteMapping(path = "{teacherId}")
    public void deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }
}