package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentIllegalArgumentException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord createStudent(StudentRecord studentRecord) {
        Student student = recordMapper.toEntity(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId()).orElseThrow(FacultyNotFoundException::new);
            student.setFaculty(faculty);
        }
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord readStudent(Long id) {
        return studentRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(StudentNotFoundException::new);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).
                orElseThrow(StudentNotFoundException::new);
    }

    public StudentRecord updateStudent(StudentRecord studentRecord) {
        Student studentTmp = studentRepository.findById(studentRecord.getId())
                .orElseThrow(StudentNotFoundException::new);
        studentTmp.setAge(studentRecord.getAge());
        studentTmp.setName(studentRecord.getName());
        return recordMapper.toRecord(studentRepository.save(studentTmp));
    }

    public StudentRecord deleteStudent(Long id) {
        Student studentTmp = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(studentTmp);
        return recordMapper.toRecord(studentTmp);
    }


    public List<StudentRecord> readAllStudentWhitAge(int age) {
        return studentRepository.findAllByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        if (minAge > maxAge) {
            throw new StudentIllegalArgumentException();
        }
        return studentRepository.findByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord findByFacultyOfStudent(long id) {
        Student studentTmp = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        if (studentTmp.getFaculty() == null) {
            throw new FacultyNotFoundException();
        }
        return recordMapper.toRecord(studentTmp.getFaculty());
    }
}
