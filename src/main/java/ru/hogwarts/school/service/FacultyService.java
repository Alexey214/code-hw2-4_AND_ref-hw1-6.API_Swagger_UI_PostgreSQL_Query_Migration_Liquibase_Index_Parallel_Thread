package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord createFaculty(FacultyRecord facultyRecord) {
        LOGGER.info("Добавляем факультет" + facultyRecord);
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord readFaculty(Long id) {
        LOGGER.info("Запрашиваем факультет" + id);
        return facultyRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(FacultyNotFoundException::new);
    }

    public FacultyRecord updateFaculty(FacultyRecord facultyRecord) {
        LOGGER.info("Обновляем факультет" + facultyRecord);
        Faculty facultyTmp = facultyRepository.findById(facultyRecord.getId())
                .orElseThrow(FacultyNotFoundException::new);
        facultyTmp.setColor(facultyRecord.getColor());
        facultyTmp.setName(facultyRecord.getName());
        return recordMapper.toRecord(facultyRepository.save(facultyTmp));
    }

    public FacultyRecord deleteFaculty(Long id) {
        LOGGER.warn("Удаляем факультет" + id);
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
        LOGGER.info("Запрашиваем факультет по цвету: " + color);
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<FacultyRecord> findByNameOrColor(String colorOrName) {
        LOGGER.info("Запрашиваем факультет по цвету или имени: " + colorOrName);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(colorOrName, colorOrName).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> findFacultyStudent(long id) {
        LOGGER.info("Запрашиваем студента факультета " + id);
        return facultyRepository.findFacultyStudents(id).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord findLongNameOfFaculty() {
        LOGGER.info("Запрашиваем самое длинное имя факультета");
        return facultyRepository.findAll().stream()
                .map(recordMapper::toRecord)
//                .map(FacultyRecord::getName)
                .max(Comparator.comparingInt(f -> f.getName().length()))
                .orElseThrow(FacultyNotFoundException::new);
    }
}
