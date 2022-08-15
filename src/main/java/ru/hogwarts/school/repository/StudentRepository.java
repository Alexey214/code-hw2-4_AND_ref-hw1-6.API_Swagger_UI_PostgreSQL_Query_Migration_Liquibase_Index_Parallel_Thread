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

    @Query("SELECT name FROM Student")
    List<String> findNameList();

    @Query(value = "select count(*) from Student", nativeQuery = true)
    Integer findCountOfAllStudents();

    @Query(value = "select avg(age) from Student", nativeQuery = true)
    Double findAverageStudentAge();

    @Query(value = "select * from student order by id desc limit ?1", nativeQuery = true)
    List<Student> findLastsStudents(int lastStudents);
}
