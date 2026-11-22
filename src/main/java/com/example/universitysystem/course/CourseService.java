package com.example.universitysystem.course;

import com.example.universitysystem.department.Department;
import com.example.universitysystem.department.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));
    }

    public void addNewCourse(Course course) {
        if (courseRepository.findCourseByCode(course.getCode()).isPresent()) {
            throw new IllegalStateException("Course already exists");
        }
        course.setDepartment(resolveDepartment(course.getDepartment()));
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        if(!courseRepository.existsById(courseId)) {
            throw new IllegalStateException("Course with id " + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, Course updateCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));

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

        Department department = resolveDepartment(updateCourse.getDepartment());
        if (department != null) {
            course.setDepartment(department);
        }
    }

    private Department resolveDepartment(Department department) {
        if (department == null || department.getId() == null) {
            return null;
        }
        return departmentRepository.findById(department.getId())
                .orElseThrow(() -> new IllegalStateException("Department with id " + department.getId() + " not found"));
    }
}