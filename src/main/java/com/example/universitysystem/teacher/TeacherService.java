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

    public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + teacherId + " not found"));
    }

    public void addNewTeacher(Teacher teacher) {
        if (teacherRepository.findTeacherByEmail(teacher.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        teacher.setDepartment(resolveDepartment(teacher.getDepartment()));
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new IllegalStateException("Teacher with id " + id + " not found");
        }
        teacherRepository.deleteById(id);
    }

    @Transactional
    public void updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + id + " not found"));

        String name = updatedTeacher.getName();
        String email = updatedTeacher.getEmail();

        if (name != null && !name.isEmpty() && !Objects.equals(teacher.getName(), name)) {
            teacher.setName(name);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(teacher.getEmail(), email)) {
            if (teacherRepository.findTeacherByEmail(email).isPresent()) {
                throw new IllegalStateException("Email is taken");
            }
            teacher.setEmail(email);
        }
        Department department = resolveDepartment(updatedTeacher.getDepartment());
        if (department != null) {
            teacher.setDepartment(department);
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