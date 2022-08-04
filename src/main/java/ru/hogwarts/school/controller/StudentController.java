package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRecord> createStudent(@RequestBody StudentRecord studentRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(studentRecord));
    }

    @GetMapping("/{id}")
    public StudentRecord readStudent(@PathVariable("id") Long id) {
        return studentService.readStudent(id);
    }

    @PutMapping
    public StudentRecord updateStudent(@RequestBody StudentRecord studentRecord) {
        return studentService.updateStudent(studentRecord);
    }

    @DeleteMapping("/{id}")
    public StudentRecord deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping
    public List<StudentRecord> getAllStudentWhitAge(@RequestParam(value = "age", required = false) int age) {
        return studentService.readAllStudentWhitAge(age);
    }

    @GetMapping(params = {"min", "max"})
    public List<StudentRecord> findByAgeBetween(@RequestParam(value = "min", required = false) int min,
                                                @RequestParam(value = "max", required = false) int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord findByFacultyOfStudent(@PathVariable("id") long id) {
        return studentService.findByFacultyOfStudent(id);
    }

    @GetMapping("/findCountOfAllStudents")
    public Integer findCountOfAllStudents() {
        return studentService.findCountOfAllStudents();
    }

    @GetMapping("/findAverageStudentAge")
    public Double findAverageStudentAge() {
        return studentService.findAverageStudentAge();
    }

    @GetMapping("/{lastStudents}/students")
    public List<StudentRecord> findLastsStudents(@PathVariable int lastStudents) {
        return studentService.findLastsStudents(lastStudents);
    }


}
