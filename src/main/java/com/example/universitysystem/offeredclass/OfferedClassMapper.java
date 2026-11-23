package com.example.universitysystem.offeredclass;

import com.example.universitysystem.course.Course;
import com.example.universitysystem.student.StudentMapper;
import com.example.universitysystem.student.StudentSummaryDto;
import com.example.universitysystem.teacher.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferedClassMapper {

    private final StudentMapper studentMapper;

    public OfferedClassMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public OfferedClass toEntity(OfferedClassRequestDto dto, Course course, Teacher teacher) {
        OfferedClass offeredClass = new OfferedClass(dto.semester(), dto.section(), dto.capacity(), dto.schedule());
        offeredClass.setCourse(course);
        offeredClass.setTeacher(teacher);
        return offeredClass;
    }

    public OfferedClassResponseDto toResponse(OfferedClass offeredClass) {
        Course course = offeredClass.getCourse();
        Teacher teacher = offeredClass.getTeacher();
        List<StudentSummaryDto> students = offeredClass.getStudents().stream()
                .map(studentMapper::toSummary)
                .toList();

        return new OfferedClassResponseDto(
                offeredClass.getId(),
                offeredClass.getSemester(),
                offeredClass.getSection(),
                offeredClass.getCapacity(),
                offeredClass.getSchedule(),
                course != null ? course.getId() : null,
                course != null ? course.getName() : null,
                course != null ? course.getCode() : null,
                teacher != null ? teacher.getId() : null,
                teacher != null ? teacher.getName() : null,
                students.size(),
                students
        );
    }
}