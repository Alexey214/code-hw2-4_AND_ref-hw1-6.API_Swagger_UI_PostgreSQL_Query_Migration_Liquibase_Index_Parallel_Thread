package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Faculty> findAllByColor(String color);

    @Query("SELECT s FROM Student AS s, Faculty AS f WHERE s.faculty.id = f.id AND f.id = ?1")
    List<Student> findFacultyStudents(long id);

}
