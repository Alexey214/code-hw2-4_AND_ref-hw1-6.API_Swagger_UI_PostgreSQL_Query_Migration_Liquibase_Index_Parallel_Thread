package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord createFaculty(FacultyRecord facultyRecord) {
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord readFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(FacultyNotFoundException::new);
    }

    public FacultyRecord updateFaculty(FacultyRecord facultyRecord) {
        Faculty facultyTmp = facultyRepository.findById(facultyRecord.getId())
                .orElseThrow(FacultyNotFoundException::new);
        facultyTmp.setColor(facultyRecord.getColor());
        facultyTmp.setName(facultyRecord.getName());
        return recordMapper.toRecord(facultyRepository.save(facultyTmp));
    }

    public FacultyRecord deleteFaculty(Long id) {
        Faculty facultyTmp = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        List<Student> studentList = facultyRepository.findFacultyStudents(id);
        for (Student student :
                studentList) {
            student.setFaculty(null);
        }
        facultyRepository.delete(facultyTmp);
        return recordMapper.toRecord(facultyTmp);
    }

    public List<FacultyRecord> readAllFacultyWhitColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<FacultyRecord> findByNameOrColor(String colorOrName) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(colorOrName, colorOrName).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> findFacultyStudent(long id) {
        return facultyRepository.findFacultyStudents(id).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
