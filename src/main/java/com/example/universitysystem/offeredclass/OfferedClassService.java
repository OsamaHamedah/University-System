package com.example.universitysystem.offeredclass;

import com.example.universitysystem.course.Course;
import com.example.universitysystem.course.CourseRepository;
import com.example.universitysystem.student.Student;
import com.example.universitysystem.student.StudentRepository;
import com.example.universitysystem.teacher.Teacher;
import com.example.universitysystem.teacher.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class OfferedClassService {

    private final OfferedClassRepository offeredClassRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public OfferedClassService(OfferedClassRepository offeredClassRepository,
                               CourseRepository courseRepository,
                               TeacherRepository teacherRepository,
                               StudentRepository studentRepository) {
        this.offeredClassRepository = offeredClassRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<OfferedClass> getOfferedClasses() {
        return offeredClassRepository.findAll();
    }

    public OfferedClass getOfferedClass(Long offeredClassId) {
        return offeredClassRepository.findById(offeredClassId)
                .orElseThrow(() -> new IllegalStateException("Offered class with id " + offeredClassId + " not found"));
    }

    public void addNewOfferedClass(OfferedClass offeredClass) {
        Course course = resolveCourse(offeredClass.getCourse());
        Teacher teacher = resolveTeacher(offeredClass.getTeacher());
        if (course == null) {
            throw new IllegalStateException("Course is required");
        }
        if (teacher == null) {
            throw new IllegalStateException("Teacher is required");
        }
        if (offeredClassRepository.existsByCourseIdAndSemesterAndSection(
                course.getId(), offeredClass.getSemester(), offeredClass.getSection())) {
            throw new IllegalStateException("This course is already offered for that semester and section");
        }
        offeredClass.setCourse(course);
        offeredClass.setTeacher(teacher);
        offeredClass.setStudents(new HashSet<>());
        offeredClassRepository.save(offeredClass);
    }

    public void deleteOfferedClass(Long offeredClassId) {
        if (!offeredClassRepository.existsById(offeredClassId)) {
            throw new IllegalStateException("Offered class with id " + offeredClassId + " not found");
        }
        offeredClassRepository.deleteById(offeredClassId);
    }

    @Transactional
    public void updateOfferedClass(Long offeredClassId, OfferedClass updated) {
        OfferedClass offeredClass = getOfferedClass(offeredClassId);

        if (updated.getSemester() != null && !updated.getSemester().isEmpty()
                && !Objects.equals(offeredClass.getSemester(), updated.getSemester())) {
            offeredClass.setSemester(updated.getSemester());
        }
        if (updated.getSection() != null && !updated.getSection().isEmpty()
                && !Objects.equals(offeredClass.getSection(), updated.getSection())) {
            offeredClass.setSection(updated.getSection());
        }
        if (updated.getCapacity() != null && !Objects.equals(offeredClass.getCapacity(), updated.getCapacity())) {
            offeredClass.setCapacity(updated.getCapacity());
        }
        if (updated.getSchedule() != null && !updated.getSchedule().isEmpty()
                && !Objects.equals(offeredClass.getSchedule(), updated.getSchedule())) {
            offeredClass.setSchedule(updated.getSchedule());
        }
        Course course = resolveCourse(updated.getCourse());
        if (course != null) {
            offeredClass.setCourse(course);
        }
        Teacher teacher = resolveTeacher(updated.getTeacher());
        if (teacher != null) {
            offeredClass.setTeacher(teacher);
        }
    }

    @Transactional
    public void enrollStudent(Long offeredClassId, Long studentId) {
        OfferedClass offeredClass = getOfferedClass(offeredClassId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        if (offeredClass.getStudents().contains(student)) {
            throw new IllegalStateException("Student is already enrolled in this class");
        }
        if (offeredClass.getCapacity() != null && offeredClass.getStudents().size() >= offeredClass.getCapacity()) {
            throw new IllegalStateException("Class is full");
        }
        offeredClass.getStudents().add(student);
    }

    @Transactional
    public void removeStudent(Long offeredClassId, Long studentId) {
        OfferedClass offeredClass = getOfferedClass(offeredClassId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        if (!offeredClass.getStudents().remove(student)) {
            throw new IllegalStateException("Student is not enrolled in this class");
        }
    }

    private Course resolveCourse(Course course) {
        if (course == null || course.getId() == null) {
            return null;
        }
        return courseRepository.findById(course.getId())
                .orElseThrow(() -> new IllegalStateException("Course with id " + course.getId() + " not found"));
    }

    private Teacher resolveTeacher(Teacher teacher) {
        if (teacher == null || teacher.getId() == null) {
            return null;
        }
        return teacherRepository.findById(teacher.getId())
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + teacher.getId() + " not found"));
    }
}