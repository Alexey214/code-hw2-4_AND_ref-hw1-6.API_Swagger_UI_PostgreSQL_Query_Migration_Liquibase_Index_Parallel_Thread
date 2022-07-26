package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getAllFacultyWhitColor(String color) {
        List<Faculty> facultyList = new ArrayList<>(getAllFaculty().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList()));
        return facultyList;
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        Collection<Faculty> facultyList = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        return facultyList;
    }

    public Set<String> findFacultyStudents(String faculty) {
        return facultyRepository.findFacultyStudents(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }
}
