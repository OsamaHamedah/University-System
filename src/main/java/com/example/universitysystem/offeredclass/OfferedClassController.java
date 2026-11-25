package com.example.universitysystem.offeredclass;

import org.springframework.http.HttpStatus;
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
    public List<OfferedClassResponseDto> getOfferedClasses() {
        return offeredClassService.getOfferedClasses();
    }

    @GetMapping(path = "{offeredClassId}")
    public OfferedClassResponseDto getOfferedClass(@PathVariable("offeredClassId") Long offeredClassId) {
        return offeredClassService.getOfferedClass(offeredClassId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferedClassResponseDto registerNewOfferedClass(@RequestBody OfferedClassRequestDto request) {
        return offeredClassService.addNewOfferedClass(request);
    }

    @PutMapping(path = "{offeredClassId}")
    public OfferedClassResponseDto updateOfferedClass(@PathVariable("offeredClassId") Long offeredClassId,
                                                      @RequestBody OfferedClassRequestDto request) {
        return offeredClassService.updateOfferedClass(offeredClassId, request);
    }

    @DeleteMapping(path = "{offeredClassId}")
    public void deleteOfferedClass(@PathVariable("offeredClassId") Long offeredClassId) {
        offeredClassService.deleteOfferedClass(offeredClassId);
    }
}