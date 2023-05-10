package com.example.demo;

import com.example.demo.objects.Student;
import com.example.demo.objects.Teacher;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.UUID;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.example.demo")
public class DemoApplication  implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	GradeService gradeService;
	@Autowired
	SubjectService subjectService;
	@Autowired
	LectureService lectureService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	StudentService studentService;

	@Override
	public void run(String... args) {

		Student student=new Student("152142", "Student", "prost", 2, "b5", "student", "stdada");
		studentService.removeStudent(student);
	studentService.addStudent(student);
//	Teacher teacher=new Teacher("Gigel", "gigescu", "profesor", "gigescu@", "parola", "214124");
//	teacherService.addTeacher(teacher);
	}
}
//start sonar in C:\Users\Dragos\Downloads\sonarqube-10.0.0.68432\sonarqube-10.0.0.68432\bin\windows-x86-64
// mvn clean verify sonar:sonar in C:\Users\Dragos\Do\demo

//TODO Inlocuit java.Data cu altceva care nu este deprecated