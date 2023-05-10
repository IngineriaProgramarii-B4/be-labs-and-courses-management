package com.example.demo;

import com.example.demo.objects.Seminar;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

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
	SeminarService seminarService;
	@Autowired
	AdminService adminService;

	@Override
	public void run(String... args) {

		seminarService.addSeminars("mate1");
		List<Seminar> seminar = seminarService.getSeminarByName("mate1");

		seminarService.updateSeminar(seminar.get(0), "pr1");
	}
}
