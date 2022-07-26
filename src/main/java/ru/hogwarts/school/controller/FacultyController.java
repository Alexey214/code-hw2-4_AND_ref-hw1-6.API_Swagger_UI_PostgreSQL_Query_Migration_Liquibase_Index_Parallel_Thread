package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty facultyTmp = facultyService.getFaculty(id);
        if (facultyTmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyTmp);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Collection<Faculty>> getAllFacultyWhitAge(@PathVariable String color) {
        Collection<Faculty> facultyTmp = facultyService.getAllFacultyWhitColor(color);
        if (facultyTmp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyTmp);
    }

    @GetMapping("findByNameOrColor")
    public ResponseEntity<Collection<Faculty>> findByNameOrColorBetween(@RequestParam(required = false) String name,
                                                                        @RequestParam(required = false) String color) {
        Collection<Faculty> facultyTmp = facultyService.findByNameOrColor(name, color);
        if (facultyTmp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyTmp);
    }

    @GetMapping("findFacultyStudents/{faculty}")
    public Set<String> findByFacultyOfStudent(@PathVariable String faculty) {
        return facultyService.findFacultyStudents(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty facultyTmp = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(facultyTmp);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty facultyTmp = facultyService.updateFaculty(faculty);
        if (facultyTmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyTmp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
