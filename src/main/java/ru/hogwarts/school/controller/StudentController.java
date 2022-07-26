package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student studentTmp = studentService.getStudent(id);
        if (studentTmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentTmp);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getAllStudentWhitAge(@PathVariable int age) {
        Collection<Student> studentTmp = studentService.getAllStudentWhitAge(age);
        if (studentTmp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentTmp);
    }

    @GetMapping("findByAge/{min},{max}")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@PathVariable int min, int max) {
        Collection<Student> studentTmp = studentService.findByAgeBetween(min, max);
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (studentTmp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studentTmp);
    }

    @GetMapping("facultyOfStudent/{name}")
    public String findByFacultyOfStudent(@PathVariable String name) {
        return studentService.findByFacultyOfStudent(name);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student studentTmp = studentService.createStudent(student);
        return ResponseEntity.ok(studentTmp);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student studentTmp = studentService.updateStudent(student);
        if (studentTmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentTmp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
