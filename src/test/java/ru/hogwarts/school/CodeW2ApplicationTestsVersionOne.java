//package ru.hogwarts.school;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.util.UriComponentsBuilder;
//import ru.hogwarts.school.controller.StudentController;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.recorder.StudentRecord;
//
//import java.net.URI;
//import java.util.Collection;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//
//class CodeW2ApplicationTestsVersionOne {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private StudentController studentController;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//
//	@Test
//	void contextLoads() throws Exception {
//		assertThat(studentController).isNotNull();
//	}
//
//	@Test
//	public void testGetStudent() throws Exception {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=15", String.class))
//				.isNotNull();
//	}
//
//	@Test
//	public void createStudent() {
//		Student student = givenStudentWith("StudentName", 25);
//		ResponseEntity<Student> response = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
//		thenStudentHasBeenCreated(response);
//	}
//
//	private Student givenStudentWith(String name, int age) {
//		return new Student(name, age);
//	}
//
//	private ResponseEntity<StudentRecord> whenSendingCreateStudentRequest(URI uri, Student student) {
//		return restTemplate.postForEntity(uri, student, Student.class);
//	}
//
//	private UriComponentsBuilder getUriBuilder() {
//		return UriComponentsBuilder.newInstance()
//				.scheme("http")
//				.host("localhost")
//				.port(port)
//				.path("/hogwarts/student");
//	}
//
//	private void resetIds(Collection<Student> students) {
//		students.forEach(it -> it.setId(null));
//	}
//
//	private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//	}
//}
