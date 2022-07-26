package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    @Query("SELECT s.name FROM Student AS s, Faculty AS f WHERE s.faculty.id = f.id AND f.name LIKE ?1")
    Set<String> findFacultyStudents(String faculty);

}
