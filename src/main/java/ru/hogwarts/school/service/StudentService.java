package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).get();
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> getAllStudentWhitAge(int age) {
        List<Student> studentList = new ArrayList<>(getAllStudent().stream()
                .filter(student -> student.getAge() <= age)
                .collect(Collectors.toList()));
        return studentList;
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
