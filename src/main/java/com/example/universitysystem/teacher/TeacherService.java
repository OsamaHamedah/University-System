package com.example.universitysystem.teacher;

import com.example.universitysystem.department.Department;
import com.example.universitysystem.department.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.teacherMapper = teacherMapper;
    }

    public List<TeacherResponseDto> getTeachers() {
        return teacherRepository.findAll().stream().map(teacherMapper::toResponse).toList();
    }

    public TeacherResponseDto getTeacher(Long teacherId) {
        return teacherMapper.toResponse(findTeacher(teacherId));
    }

    public TeacherResponseDto addNewTeacher(TeacherRequestDto request) {
        if (teacherRepository.findTeacherByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        Department department = resolveDepartment(request.departmentId());
        Teacher saved = teacherRepository.save(teacherMapper.toEntity(request, department));
        return teacherMapper.toResponse(saved);
    }

    public void deleteTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalStateException("Teacher with id " + teacherId + " not found");
        }
        teacherRepository.deleteById(teacherId);
    }

    @Transactional
    public TeacherResponseDto updateTeacher(Long teacherId, TeacherRequestDto request) {
        Teacher teacher = findTeacher(teacherId);

        String name = request.name();
        String email = request.email();

        if (name != null && !name.isEmpty() && !Objects.equals(teacher.getName(), name)) {
            teacher.setName(name);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(teacher.getEmail(), email)) {
            if (teacherRepository.findTeacherByEmail(email).isPresent()) {
                throw new IllegalStateException("Email is taken");
            }
            teacher.setEmail(email);
        }
        Department department = resolveDepartment(request.departmentId());
        if (department != null) {
            teacher.setDepartment(department);
        }
        return teacherMapper.toResponse(teacher);
    }

    private Teacher findTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + teacherId + " not found"));
    }

    private Department resolveDepartment(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " not found"));
    }
}