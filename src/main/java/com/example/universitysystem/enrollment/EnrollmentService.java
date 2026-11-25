package com.example.universitysystem.enrollment;

import com.example.universitysystem.exception.ConflictException;
import com.example.universitysystem.exception.ResourceNotFoundException;
import com.example.universitysystem.offeredclass.OfferedClass;
import com.example.universitysystem.offeredclass.OfferedClassRepository;
import com.example.universitysystem.student.Student;
import com.example.universitysystem.student.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final OfferedClassRepository offeredClassRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(StudentRepository studentRepository,
                             OfferedClassRepository offeredClassRepository,
                             EnrollmentMapper enrollmentMapper) {
        this.studentRepository = studentRepository;
        this.offeredClassRepository = offeredClassRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    public EnrollmentResponseDto enroll(Long studentId, Long offeredClassId) {
        Student student = findStudent(studentId);
        OfferedClass offeredClass = findOfferedClass(offeredClassId);

        if (offeredClass.getStudents().contains(student)) {
            throw new ConflictException("Student with id " + studentId
                    + " is already enrolled in offered class " + offeredClassId);
        }
        Integer capacity = offeredClass.getCapacity();
        if (capacity != null && offeredClass.getStudents().size() >= capacity) {
            throw new ConflictException("Offered class with id " + offeredClassId
                    + " is full (capacity " + capacity + ")");
        }
        offeredClass.getStudents().add(student);
        return enrollmentMapper.toResponse(student, offeredClass, "Student enrolled successfully");
    }

    public EnrollmentResponseDto withdraw(Long studentId, Long offeredClassId) {
        Student student = findStudent(studentId);
        OfferedClass offeredClass = findOfferedClass(offeredClassId);

        if (!offeredClass.getStudents().remove(student)) {
            throw new ResourceNotFoundException("Student with id " + studentId
                    + " is not enrolled in offered class " + offeredClassId);
        }
        return enrollmentMapper.toResponse(student, offeredClass, "Student withdrawn successfully");
    }

    public StudentScheduleResponseDto getSchedule(Long studentId, String semester) {
        Student student = findStudent(studentId);

        Comparator<OfferedClass> bySemesterThenCode =
                Comparator.comparing(OfferedClass::getSemester, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(this::courseCode, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));

        List<OfferedClass> classes = student.getOfferedClasses().stream()
                .filter(c -> semester == null || semester.isBlank() || semester.equalsIgnoreCase(c.getSemester()))
                .sorted(bySemesterThenCode)
                .toList();

        return enrollmentMapper.toSchedule(student, classes);
    }

    private String courseCode(OfferedClass offeredClass) {
        return offeredClass.getCourse() != null ? offeredClass.getCourse().getCode() : null;
    }

    private Student findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
    }

    private OfferedClass findOfferedClass(Long offeredClassId) {
        return offeredClassRepository.findById(offeredClassId)
                .orElseThrow(() -> new ResourceNotFoundException("Offered class with id " + offeredClassId + " not found"));
    }
}