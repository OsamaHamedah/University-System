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
    private final CourseMapper courseMapper;


    public CourseService(CourseRepository courseRepository, DepartmentRepository departmentRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseResponseDto> getCourses() {
        return courseRepository.findAll().stream().map(courseMapper::toResponse).toList();
    }

    public CourseResponseDto getCourse(Long courseId) {
        return courseMapper.toResponse(findCourse(courseId));
    }

    public CourseResponseDto addNewCourse(CourseRequestDto request) {
        if (courseRepository.findCourseByCode(request.code()).isPresent()) {
            throw new IllegalStateException("Course already exists");
        }
        Department department = resolveDepartment(request.departmentId());
        Course saved = courseRepository.save(courseMapper.toEntity(request, department));
        return courseMapper.toResponse(saved);
    }

    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new IllegalStateException("Course with id " + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public CourseResponseDto updateCourse(Long courseId, CourseRequestDto request) {
        Course course = findCourse(courseId);

        String name = request.name();
        String code = request.code();
        Integer credits = request.credits();

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

        Department department = resolveDepartment(request.departmentId());
        if (department != null) {
            course.setDepartment(department);
        }
        return courseMapper.toResponse(course);

    }

    private Course findCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));
    }

    private Department resolveDepartment(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
    }
}