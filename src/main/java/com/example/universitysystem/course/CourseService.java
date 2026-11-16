package com.example.universitysystem.course;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));
    }

    public void addNewCourse(Course course) {
        if(courseRepository.findCourseByCode(course.getCode()).isPresent()){
            throw new IllegalStateException("Course already exists");
        }
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        if(!courseRepository.existsById(courseId)) {
            throw new IllegalStateException("Course with id "  + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, Course updateCourse) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalStateException("Course with id " + courseId + " not found"));

        String name = updateCourse.getName();
        String code = updateCourse.getCode();
        Integer credits = updateCourse.getCredits();

        if(name != null && !name.isEmpty() && !Objects.equals(course.getName(), name)) {
            course.setName(name);
        }

        if(credits != null && !Objects.equals(course.getCredits(), credits)) {
            course.setCredits(credits);
        }

        if(code != null && !code.isEmpty() && !Objects.equals(course.getCode(), code)) {
            if (courseRepository.findCourseByCode(code).isPresent()) {
                throw new IllegalStateException("Course with code " + code + " already exists");
            }
            course.setCode(code);
        }
    }
}
