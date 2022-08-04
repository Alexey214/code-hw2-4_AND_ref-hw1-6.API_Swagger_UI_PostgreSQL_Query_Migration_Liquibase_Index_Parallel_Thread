package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.recorder.AvatarRecord;
import ru.hogwarts.school.recorder.FacultyRecord;
import ru.hogwarts.school.recorder.StudentRecord;

@Component
public class RecordMapper {

    public StudentRecord toRecord(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentRecord.setFaculty(toRecord(student.getFaculty()));
        }
        return studentRecord;
    }

    public FacultyRecord toRecord(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
        avatarRecord.setFilePath(avatar.getFilePath());
        avatarRecord.setFileSize(avatar.getFileSize());
        avatarRecord.setMediaType(avatar.getMediaType());
        avatarRecord.setData(avatar.getData());
        return avatarRecord;
    }


    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setAge(studentRecord.getAge());
        student.setName(studentRecord.getName());
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord) {
        Faculty faculty = new Faculty();
        faculty.setColor(facultyRecord.getColor());
        faculty.setName(facultyRecord.getName());
        return faculty;
    }

    public Avatar toEntity(AvatarRecord avatarRecord) {
        Avatar avatar = new Avatar();
        avatar.setId(avatarRecord.getId());
        avatar.setFilePath(avatarRecord.getFilePath());
        avatar.setFileSize(avatarRecord.getFileSize());
        avatar.setMediaType(avatarRecord.getMediaType());
        avatar.setData(avatarRecord.getData());
        return avatar;
    }
}
