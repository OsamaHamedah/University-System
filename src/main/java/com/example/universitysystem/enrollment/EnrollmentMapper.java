package com.example.universitysystem.enrollment;

import com.example.universitysystem.course.Course;
import com.example.universitysystem.offeredclass.OfferedClass;
import com.example.universitysystem.student.Student;
import com.example.universitysystem.teacher.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentMapper {

    public EnrollmentResponseDto toResponse(Student student, OfferedClass offeredClass, String message) {
        Course course = offeredClass.getCourse();
        return new EnrollmentResponseDto(
                student.getId(),
                student.getName(),
                offeredClass.getId(),
                course != null ? course.getCode() : null,
                course != null ? course.getName() : null,
                offeredClass.getSemester(),
                offeredClass.getSection(),
                offeredClass.getStudents().size(),
                offeredClass.getCapacity(),
                message
        );
    }

    public ScheduleItemDto toScheduleItem(OfferedClass offeredClass) {
        Course course = offeredClass.getCourse();
        Teacher teacher = offeredClass.getTeacher();
        return new ScheduleItemDto(
                offeredClass.getId(),
                offeredClass.getSemester(),
                offeredClass.getSection(),
                offeredClass.getSchedule(),
                course != null ? course.getCode() : null,
                course != null ? course.getName() : null,
                course != null ? course.getCredits() : null,
                teacher != null ? teacher.getId() : null,
                teacher != null ? teacher.getName() : null
        );
    }

    public StudentScheduleResponseDto toSchedule(Student student, List<OfferedClass> classes) {
        List<ScheduleItemDto> items = classes.stream().map(this::toScheduleItem).toList();
        int totalCredits = items.stream()
                .mapToInt(item -> item.credits() != null ? item.credits() : 0)
                .sum();
        return new StudentScheduleResponseDto(student.getId(), student.getName(),
                items.size(), totalCredits, items);
    }
}