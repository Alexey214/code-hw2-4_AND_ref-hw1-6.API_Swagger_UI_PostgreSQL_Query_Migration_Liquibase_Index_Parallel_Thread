package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    @Query("SELECT f.name FROM Student AS s, Faculty AS f WHERE s.faculty.id = f.id AND s.name LIKE ?1")
    String findByFaculty(String name);

}
