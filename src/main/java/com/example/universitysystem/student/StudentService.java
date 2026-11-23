package com.example.universitysystem.student;

import com.example.universitysystem.department.Department;
import com.example.universitysystem.department.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository,
                          DepartmentRepository departmentRepository,
                          StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.studentMapper = studentMapper;
    }

    public List<StudentResponseDto> getStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toResponse).toList();
    }

    public StudentResponseDto getStudent(Long studentId) {
        return studentMapper.toResponse(findStudent(studentId));
    }

    public StudentResponseDto addNewStudent(StudentRequestDto request) {
        if (studentRepository.findStudentByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        Department department = resolveDepartment(request.departmentId());
        Student saved = studentRepository.save(studentMapper.toEntity(request, department));
        return studentMapper.toResponse(saved);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with id " + studentId + " not found");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public StudentResponseDto updateStudent(Long studentId, StudentRequestDto request) {
        Student student = findStudent(studentId);

        String name = request.name();
        String email = request.email();
        LocalDate dob = request.dob();

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email is taken");
            }
            student.setEmail(email);
        }
        if (dob != null) {
            student.setDob(dob);
        }
        Department department = resolveDepartment(request.departmentId());
        if (department != null) {
            student.setDepartment(department);
        }
        return studentMapper.toResponse(student);
    }

    private Student findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));
    }

    private Department resolveDepartment(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
    }
}