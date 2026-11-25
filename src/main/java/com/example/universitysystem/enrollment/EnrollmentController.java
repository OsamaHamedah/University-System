package com.example.universitysystem.enrollment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/student/{studentId}")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping(path = "enrollments/{offeredClassId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponseDto enroll(@PathVariable("studentId") Long studentId,
                                        @PathVariable("offeredClassId") Long offeredClassId) {
        return enrollmentService.enroll(studentId, offeredClassId);
    }

    @DeleteMapping(path = "enrollments/{offeredClassId}")
    public EnrollmentResponseDto withdraw(@PathVariable("studentId") Long studentId,
                                          @PathVariable("offeredClassId") Long offeredClassId) {
        return enrollmentService.withdraw(studentId, offeredClassId);
    }

    @GetMapping(path = "schedule")
    public StudentScheduleResponseDto getSchedule(@PathVariable("studentId") Long studentId,
                                                  @RequestParam(name = "semester", required = false) String semester) {
        return enrollmentService.getSchedule(studentId, semester);
    }
}