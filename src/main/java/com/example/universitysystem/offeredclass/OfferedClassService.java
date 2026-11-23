package com.example.universitysystem.offeredclass;

import com.example.universitysystem.course.Course;
import com.example.universitysystem.course.CourseRepository;
import com.example.universitysystem.student.Student;
import com.example.universitysystem.student.StudentRepository;
import com.example.universitysystem.teacher.Teacher;
import com.example.universitysystem.teacher.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class OfferedClassService {

    private final OfferedClassRepository offeredClassRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final OfferedClassMapper offeredClassMapper;

    public OfferedClassService(OfferedClassRepository offeredClassRepository,
                               CourseRepository courseRepository,
                               TeacherRepository teacherRepository,
                               StudentRepository studentRepository,
                               OfferedClassMapper offeredClassMapper) {
        this.offeredClassRepository = offeredClassRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.offeredClassMapper = offeredClassMapper;
    }

    public List<OfferedClassResponseDto> getOfferedClasses() {
        return offeredClassRepository.findAll().stream().map(offeredClassMapper::toResponse).toList();
    }

    public OfferedClassResponseDto getOfferedClass(Long offeredClassId) {
        return offeredClassMapper.toResponse(findOfferedClass(offeredClassId));
    }

    public OfferedClassResponseDto addNewOfferedClass(OfferedClassRequestDto request) {
        Course course = resolveCourse(request.courseId());
        Teacher teacher = resolveTeacher(request.teacherId());
        if (course == null) {
            throw new IllegalStateException("Course is required");
        }
        if (teacher == null) {
            throw new IllegalStateException("Teacher is required");
        }
        if (offeredClassRepository.existsByCourseIdAndSemesterAndSection(
                course.getId(), request.semester(), request.section())) {
            throw new IllegalStateException("This course is already offered for that semester and section");
        }
        OfferedClass saved = offeredClassRepository.save(offeredClassMapper.toEntity(request, course, teacher));
        return offeredClassMapper.toResponse(saved);
    }

    public void deleteOfferedClass(Long offeredClassId) {
        if (!offeredClassRepository.existsById(offeredClassId)) {
            throw new IllegalStateException("Offered class with id " + offeredClassId + " not found");
        }
        offeredClassRepository.deleteById(offeredClassId);
    }

    public OfferedClassResponseDto updateOfferedClass(Long offeredClassId, OfferedClassRequestDto request) {
        OfferedClass offeredClass = findOfferedClass(offeredClassId);

        if (request.semester() != null && !request.semester().isEmpty()
                && !Objects.equals(offeredClass.getSemester(), request.semester())) {
            offeredClass.setSemester(request.semester());
        }
        if (request.section() != null && !request.section().isEmpty()
                && !Objects.equals(offeredClass.getSection(), request.section())) {
            offeredClass.setSection(request.section());
        }
        if (request.capacity() != null && !Objects.equals(offeredClass.getCapacity(), request.capacity())) {
            offeredClass.setCapacity(request.capacity());
        }
        if (request.schedule() != null && !request.schedule().isEmpty()
                && !Objects.equals(offeredClass.getSchedule(), request.schedule())) {
            offeredClass.setSchedule(request.schedule());
        }
        Course course = resolveCourse(request.courseId());
        if (course != null) {
            offeredClass.setCourse(course);
        }
        Teacher teacher = resolveTeacher(request.teacherId());
        if (teacher != null) {
            offeredClass.setTeacher(teacher);
        }
        return offeredClassMapper.toResponse(offeredClass);
    }

    public OfferedClassResponseDto enrollStudent(Long offeredClassId, Long studentId) {
        OfferedClass offeredClass = findOfferedClass(offeredClassId);
        Student student = findStudent(studentId);

        if (offeredClass.getStudents().contains(student)) {
            throw new IllegalStateException("Student is already enrolled in this class");
        }
        if (offeredClass.getCapacity() != null && offeredClass.getStudents().size() >= offeredClass.getCapacity()) {
            throw new IllegalStateException("Class is full");
        }
        offeredClass.getStudents().add(student);
        return offeredClassMapper.toResponse(offeredClass);
    }

    public OfferedClassResponseDto removeStudent(Long offeredClassId, Long studentId) {
        OfferedClass offeredClass = findOfferedClass(offeredClassId);
        Student student = findStudent(studentId);

        if (!offeredClass.getStudents().remove(student)) {
            throw new IllegalStateException("Student is not enrolled in this class");
        }
        return offeredClassMapper.toResponse(offeredClass);
    }

    private OfferedClass findOfferedClass(Long offeredClassId) {
        return offeredClassRepository.findById(offeredClassId)
                .orElseThrow(() -> new IllegalStateException("Offered class with id " + offeredClassId + " not found"));
    }

    private Student findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));
    }

    private Course resolveCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));
    }

    private Teacher resolveTeacher(Long teacherId) {
        if (teacherId == null) {
            return null;
        }
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + teacherId + " not found"));
    }
}