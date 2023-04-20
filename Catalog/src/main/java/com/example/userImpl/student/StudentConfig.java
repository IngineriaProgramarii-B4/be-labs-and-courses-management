package com.example.userImpl.student;

import com.example.grades.Grade;
import com.example.subject.Subject;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
//            Student student = new Student(301232,"student1@gmail.com", "Mihai");
//            Student student1 = new Student(301233,"student2@gmail.com", "Andrei");
//
//
//            Subject subject = new Subject("IP");
//            Subject subject1 = new Subject("PA");
//
//            Grade grade1=new Grade(5,subject1, "12.12.2004");
//
//            student.addGrade(grade1);
//            student1.addGrade(new Grade(9, subject, "08.12.2007"));
//            student.addGrade(new Grade(10,subject,"17.03.2008"));
//
////            Grade grade2=new Grade(7,new Subject("IP"), "12.12.2005");
//
////            Grade grade4=new Grade(4,new Subject("IP"), "07.12.2009");
////            student.addGrade(grade2);
//           // student1.addGrade(grade3);
////            student1.addGrade(grade4);

//            repository.saveAll(List.of(student,student1));

        };
    }
}
