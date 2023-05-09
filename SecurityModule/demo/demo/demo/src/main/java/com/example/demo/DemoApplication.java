package com.example.demo;

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
	UserService userService;
	@Autowired
	SubjectService subjectService;
	@Autowired
	LectureService lectureService;

	@Autowired
	SeminarService seminarService;
	@Autowired
	AdminService adminService;


	@Override
	public void run(String... args) {
		subjectService.deleteSubject("Ingineria");
		subjectService.deleteSubject("IngineriaProgramarii");
//		subjectService.addSubject(UUID.randomUUID(),"Ingineria", 2, 2, 6, 1, 1);
//		System.out.println(subjectService.getSubjectByName("Ingineria"));
//		subjectService.updateSubject("Ingineria","IngineriaProgramarii",1,5,2,2,2);
//		subjectService.addSubject(UUID.randomUUID(),"Ingineria", 2, 2, 6, 1, 1);
//		System.out.println(subjectService.getSubjectByName("Ingineria"));
//		System.out.println(subjectService.getSubjectByYear(1));
//		System.out.println(subjectService.getSubjectByYearAndSemester(1,5));
		//lectureService.addLectures("IP");
		//lectureService.deleteLectures("IP");
		//System.out.println(lectureService.getLectureByName("IP"));
		seminarService.addSeminars("PA");
		//lectureService.updateLecture("IP", "Ingineria programarii");
	seminarService.updateSeminars("PA", "Programare");
	}
}
//start sonar =in C:\SonarQube\sonarqube-10.0.0.68432\sonarqube-10.0.0.68432\bin\windows-x86-64   =
// mvn clean verify sonar:sonar =in C:\Users\Andrada\Downloads\demo\demo\demo  =

//TODO Inlocuit java.Data cu altceva care nu este deprecated