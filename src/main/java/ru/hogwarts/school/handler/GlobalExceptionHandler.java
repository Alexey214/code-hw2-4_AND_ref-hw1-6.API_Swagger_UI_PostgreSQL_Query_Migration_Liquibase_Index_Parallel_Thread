package ru.hogwarts.school.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.FacultyIllegalArgumentException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentIllegalArgumentException;
import ru.hogwarts.school.exception.StudentNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException e) {
        LOGGER.error("Студент не найден!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Студент не найден!");
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<String> handleFacultyNotFoundException(FacultyNotFoundException e) {
        LOGGER.error("Факультет не найден!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Факультет не найден!");
    }

    @ExceptionHandler(StudentIllegalArgumentException.class)
    public ResponseEntity<String> handleStudentIllegalArgumentException(StudentIllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Некорректный запрос возраста!");
    }

    @ExceptionHandler(FacultyIllegalArgumentException.class)
    public ResponseEntity<String> handleFacultyIllegalArgumentException(FacultyIllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Неверные данные!");
    }
}
