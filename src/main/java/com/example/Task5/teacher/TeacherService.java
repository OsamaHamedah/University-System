package com.example.Task5.teacher;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public void addNewTeacher(Teacher teacher) {
        if (teacherRepository.findTeacherByEmail(teacher.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
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
        String department = updatedTeacher.getDepartment();

        if (name != null && !name.isEmpty() && !Objects.equals(teacher.getName(), name)) {
            teacher.setName(name);
        }
        if (department != null && !department.isEmpty() && !Objects.equals(teacher.getDepartment(), department)) {
            teacher.setDepartment(department);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(teacher.getEmail(), email)) {
            if (teacherRepository.findTeacherByEmail(email).isPresent()) {
                throw new IllegalStateException("Email is taken");
            }
            teacher.setEmail(email);
        }
    }
}