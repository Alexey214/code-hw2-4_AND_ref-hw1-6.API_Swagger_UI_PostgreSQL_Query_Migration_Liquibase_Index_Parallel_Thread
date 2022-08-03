package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findAllByAge(int age);

    @Query("SELECT f.name FROM Student AS s, Faculty AS f WHERE s.faculty.id = f.id AND s.id = ?1")
    Student findByFaculty(long id);

}
