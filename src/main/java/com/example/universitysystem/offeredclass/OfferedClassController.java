package com.example.universitysystem.offeredclass;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/offered-class")
public class OfferedClassController {

    private final OfferedClassService offeredClassService;

    public OfferedClassController(OfferedClassService offeredClassService) {
        this.offeredClassService = offeredClassService;
    }

    @GetMapping
    public List<OfferedClass> getOfferedClasses() {
        return offeredClassService.getOfferedClasses();
    }

    @GetMapping(path = "{offeredClassId}")
    public OfferedClass getOfferedClass(@PathVariable("offeredClassId") Long offeredClassId) {
        return offeredClassService.getOfferedClass(offeredClassId);
    }

    @PostMapping
    public void registerNewOfferedClass(@RequestBody OfferedClass offeredClass) {
        offeredClassService.addNewOfferedClass(offeredClass);
    }

    @PutMapping(path = "{offeredClassId}")
    public void updateOfferedClass(@PathVariable("offeredClassId") Long offeredClassId,
                                   @RequestBody OfferedClass offeredClass) {
        offeredClassService.updateOfferedClass(offeredClassId, offeredClass);
    }

    @DeleteMapping(path = "{offeredClassId}")
    public void deleteOfferedClass(@PathVariable("offeredClassId") Long offeredClassId) {
        offeredClassService.deleteOfferedClass(offeredClassId);
    }

    @PutMapping(path = "{offeredClassId}/students/{studentId}")
    public void enrollStudent(@PathVariable("offeredClassId") Long offeredClassId,
                              @PathVariable("studentId") Long studentId) {
        offeredClassService.enrollStudent(offeredClassId, studentId);
    }

    @DeleteMapping(path = "{offeredClassId}/students/{studentId}")
    public void removeStudent(@PathVariable("offeredClassId") Long offeredClassId,
                              @PathVariable("studentId") Long studentId) {
        offeredClassService.removeStudent(offeredClassId, studentId);
    }
}