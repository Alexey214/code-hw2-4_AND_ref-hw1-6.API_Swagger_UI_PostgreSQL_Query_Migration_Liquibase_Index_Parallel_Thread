//package ru.hogwarts.school.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import ru.hogwarts.school.component.RecordMapper;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.recorder.FacultyRecord;
//import ru.hogwarts.school.recorder.StudentRecord;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class StudentControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private RecordMapper recordMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void getStudentById_Positive() {
//        String url = "http://localhost:" + port + "/student/" + getStudent().getId();
//
//        ResponseEntity<Student> entity = restTemplate.getForEntity(url, Student.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody()).isEqualTo(recordMapper.toRecord(getStudent()));
//    }
//
//    @Test
//    void getStudentById_Negative() {
//        String url = "http://localhost:" + port + "/student/" + Long.MAX_VALUE;
//
//        ResponseEntity<StudentRecord> entity = restTemplate.getForEntity(url, StudentRecord.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//
//    @Test
//    void getFacultyStudentById_Positive() {
//        String url = "http://localhost:" + port + "/student/" + getStudent().getId() + "/faculty";
//
//        ResponseEntity<FacultyRecord> entity = restTemplate.getForEntity(url, FacultyRecord.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody()).isEqualTo(recordMapper.toRecord(getStudent().getFaculty()));
//    }
//
//    @Test
//    void getFacultyStudentById_Negative() {
//        String url = "http://localhost:" + port + "/student/" + Long.MAX_VALUE + "/faculty";
//
//        ResponseEntity<FacultyRecord> entity = restTemplate.getForEntity(url, FacultyRecord.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void getStudents() throws Exception {
//        List<StudentRecord> allStudents = getAllStudent().stream().map(recordMapper::toRecord).collect(Collectors.toList());
//        String jsonResult = objectMapper.writeValueAsString(allStudents);
//        String url = "http://localhost:" + port + "/student";
//
//        ResponseEntity<Collection> entity = restTemplate.getForEntity(url, Collection.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody())
//                .isNotEmpty()
//                .hasSize(allStudents.size());
//        JSONAssert.assertEquals(jsonResult, objectMapper.writeValueAsString(entity.getBody()), false);
//
//    }
//
//    @Test
//    void getStudentsByAge() throws Exception {
//        List<StudentRecord> allStudents = getAllStudent().stream().map(recordMapper::toRecord).collect(Collectors.toList());
//        int targetAge = allStudents.get(0).getAge();
//        String url = "http://localhost:" + port + "/student?age=" + targetAge;
//        List<StudentRecord> StudentsByTargetAge = allStudents.stream()
//                .filter(studentDto -> studentDto.getAge() == targetAge).collect(Collectors.toList());
//        String jsonResult = objectMapper.writeValueAsString(StudentsByTargetAge);
//
//        ResponseEntity<Collection> entity = restTemplate.getForEntity(url, Collection.class);
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody())
//                .isNotEmpty()
//                .hasSize(StudentsByTargetAge.size());
//        JSONAssert.assertEquals(jsonResult, objectMapper.writeValueAsString(entity.getBody()), false);
//    }
//
//
//    @Test
//    void getStudentsByAgeBetween() throws Exception {
//        List<StudentRecord> allStudents = getAllStudent().stream().map(recordMapper::toRecord).collect(Collectors.toList());
//        int minAge = allStudents.get(0).getAge();
//        int maxAge = allStudents.get(allStudents.size() - 2).getAge();
//        String url = "http://localhost:" + port + "/student/" + "?min=" + minAge + "&max=" + maxAge;
//        List<StudentRecord> studentsByTargetAge = allStudents.stream()
//                .filter(studentDto -> studentDto.getAge() >= minAge && studentDto.getAge() <= maxAge)
//                .collect(Collectors.toList());
//        String jsonResult = objectMapper.writeValueAsString(studentsByTargetAge);
//
//
//
//        ResponseEntity<List<StudentRecord>> entity = restTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<StudentRecord>>() {
//                });
//
//
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody())
//                .isNotEmpty()
//                .hasSize(studentsByTargetAge.size());
//        assertThat(entity.getBody()).containsExactlyInAnyOrderElementsOf(studentsByTargetAge::listIterator); // без доп библиотеки
//        JSONAssert.assertEquals(jsonResult, objectMapper.writeValueAsString(entity.getBody()), false);
//    }
//
//    @Test
//    void createStudent_Positive() throws Exception {
//        Student student = getStudent();
//        student.setId(null);
//        String url = "http://localhost:" + port + "/student";
//        ResponseEntity<StudentRecord> response = restTemplate.postForEntity(url, student, StudentRecord.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().getAge()).isEqualTo(student.getAge());
//        assertThat(response.getBody().getName()).isEqualTo(student.getName());
//        assertThat(response.getBody().getId()).isGreaterThan(0L);
//    }
//
//    @Test
//    void createStudent_ValidationError() throws Exception {
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//        body.add("id", "0");
//        body.add("name", "string");
//        body.add("age", "0");
//        String url = "http://localhost:" + port + "/student";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<?> httpEntity = new HttpEntity<Object>(headers, body);
//        System.out.println(httpEntity);
//
//        ResponseEntity<StudentRecord> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, StudentRecord.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//    }
//
//    @Test
//    void updateStudent() throws Exception {
//        String newName = "NewStudentName";
//        Student student = getStudent();
//        student.setName(newName);
//        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(recordMapper.toRecord(student));
//        String url = "http://localhost:" + port + "/student";
//        ResponseEntity<StudentRecord> exchange = restTemplate.exchange(url, HttpMethod.PUT, objectHttpEntity, StudentRecord.class);
//
//        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(exchange.getBody().getName()).isEqualTo(newName);
//
//    }
//
//
//    @Test
//    void deleteStudent() throws Exception {
//        String url0 = "http://localhost:" + port + "/student";
//        Student student = getStudent();
//        Long id = student.getId();
//        String url = url0 + "/" + id;
//        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(student);
//
//        ResponseEntity<StudentRecord> exchange = restTemplate.exchange(url, HttpMethod.DELETE, objectHttpEntity, StudentRecord.class);
//        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(exchange.getBody().getId()).isEqualTo(id);
//
//        // Negative
//        ResponseEntity<StudentRecord> exchangeNotFound = restTemplate.exchange(url, HttpMethod.DELETE, objectHttpEntity, StudentRecord.class);
//        assertThat(exchangeNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    private Student getStudent() {
//        List<Student> allStudent = getAllStudent();
//        return allStudent.get(0);
//    }
//
//    private List<Student> getAllStudent() {
//        List<Student> list = new ArrayList<>();
//        //insert into students (id, age, name, avatar_id, faculty_id) values (1, 11, 'name1',null,1);
//        Student student1 = new Student();
//        student1.setId(1L);
//        student1.setAge(11);
//        student1.setName("name1");
//        student1.setFaculty(getFaculty());
//        list.add(student1);
//        //insert into students (id, age, name, avatar_id, faculty_id) values (2, 11, 'name2',null,1);
//        Student student2 = new Student();
//        student2.setId(2L);
//        student2.setAge(11);
//        student2.setName("name2");
//        student2.setFaculty(getFaculty());
//        list.add(student2);
//        //insert into students (id, age, name, avatar_id, faculty_id) values (3, 13, 'name3',null,1);
//        Student student3 = new Student();
//        student3.setId(3L);
//        student3.setAge(13);
//        student3.setName("name3");
//        student3.setFaculty(getFaculty());
//        list.add(student3);
//        //insert into students (id, age, name, avatar_id, faculty_id) values (4, 14, 'name4',null,1);
//        Student student4 = new Student();
//        student4.setId(4L);
//        student4.setAge(14);
//        student4.setName("name4");
//        student4.setFaculty(getFaculty());
//        list.add(student4);
//        //insert into students (id, age, name, avatar_id, faculty_id) values (5, 15, 'name4',null,1);
//        Student student5 = new Student();
//        student5.setId(5L);
//        student5.setAge(15);
//        student5.setName("name5");
//        student5.setFaculty(getFaculty());
//        list.add(student5);
//        return list;
//    }
//
//    private Faculty getFaculty() {
//        //insert into faculties(id, color, name) values (1, 'color', 'name');
//        Faculty faculty = new Faculty();
//        faculty.setId(1L);
//        faculty.setName("name");
//        faculty.setColor("color");
//        return faculty;
//    }
//
//    @Test
//    void createStudent() {
//    }
//
//    @Test
//    void readStudent() {
//    }
//
////    @Test
////    void updateStudent() {
////    }
//
////    @Test
////    void deleteStudent() {
////    }
//
//    @Test
//    void getAllStudentWhitAge() {
//    }
//
//    @Test
//    void findByAgeBetween() {
//    }
//
//    @Test
//    void findByFacultyOfStudent() {
//    }
//
//    @Test
//    void findCountOfAllStudents() {
//    }
//
//    @Test
//    void findAverageStudentAge() {
//    }
//
//    @Test
//    void findLastsStudents() {
//    }
//}