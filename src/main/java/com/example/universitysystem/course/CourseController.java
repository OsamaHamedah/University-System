package com.example.universitysystem.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponseDto> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping(path = "{courseId}")
    public CourseResponseDto getCourse(@PathVariable("courseId") Long courseId) {
        return courseService.getCourse(courseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto registerNewCourse(@RequestBody CourseRequestDto request) {
        return courseService.addNewCourse(request);
    }

    @PutMapping(path = "{courseId}")
    public CourseResponseDto updateCourse(@PathVariable("courseId") Long courseId,
                                          @RequestBody CourseRequestDto request) {
        return courseService.updateCourse(courseId, request);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
    }
}