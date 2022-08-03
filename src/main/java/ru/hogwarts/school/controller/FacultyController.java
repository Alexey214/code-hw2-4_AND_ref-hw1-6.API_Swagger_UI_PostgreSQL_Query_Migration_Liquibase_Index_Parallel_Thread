package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<FacultyRecord> createFaculty(@RequestBody FacultyRecord facultyRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyService.createFaculty(facultyRecord));
    }

    @GetMapping("/{id}")
    public FacultyRecord getFaculty(@PathVariable("id") Long id) {
        return facultyService.readFaculty(id);
    }

    @PutMapping
    public FacultyRecord updateFaculty(@RequestBody FacultyRecord facultyRecord) {
        return facultyService.updateFaculty(facultyRecord);
    }

    @DeleteMapping("/{id}")
    public FacultyRecord deleteFaculty(@PathVariable("id") Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/color/{color}")
    public List<FacultyRecord> getAllFacultyWhitColor(@RequestParam("color") String color) {
        return facultyService.readAllFacultyWhitColor(color);
    }

    @GetMapping("/findByNameOrColor")
    public List<FacultyRecord> findByNameOrColorBetween(@RequestParam("colorOrName") String colorOrName) {
        return facultyService.findByNameOrColor(colorOrName);
    }

    @GetMapping("/{id}/students")
    public List<StudentRecord> findByFacultyOfStudent(@PathVariable("id") Long id) {
        return facultyService.findFacultyStudent(id);
    }
}
