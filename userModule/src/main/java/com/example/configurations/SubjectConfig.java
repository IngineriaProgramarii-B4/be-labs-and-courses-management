package com.example.configurations;


import com.example.models.*;
import com.example.repository.SubjectRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SubjectConfig {

    @Bean
    CommandLineRunner subjectCommandLineRunner(SubjectRepo repository) {
        String course = "Course";
        String seminar = "Seminar";
        String laboratory = "Laboratory";
        return args -> {
            Subject subject1 = new Subject(1, "Maths", 4, 1, 1, """
                    This course is designed to provide students with a comprehensive understanding of mathematical concepts and principles. The course covers a wide range of topics, including algebra, calculus, statistics, geometry, and trigonometry.
                    Students will learn how to solve complex mathematical problems, develop analytical and critical thinking skills, and enhance their ability to reason logically. They will also develop a deep appreciation for the beauty and elegance of mathematics and its practical applications in various fields, such as engineering, physics, finance, and computer science.
                    The course includes lectures, interactive discussions, and hands-on activities to help students grasp abstract mathematical concepts and apply them to real-world problems. Students will also have opportunities to collaborate with their peers, engage in group projects, and receive individualized feedback from the instructor.
                    Upon completion of the course, students will have a solid foundation in mathematics, which will prepare them for advanced courses in math or related disciplines, as well as careers in fields that require strong quantitative skills.""",
                    List.of(new Component(1, course, 10, new ArrayList<>(),false)),
                    new ArrayList<>(), false);
            Subject subject2 = new Subject(2, "Physics", 5, 1, 2, "description Physics",
                    List.of(new Component(2, course, 9, new ArrayList<>(), false)),
                    List.of(new Evaluation(1L, course, 0.5f, "Exam", false)), false);
            Subject subject3 = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                    List.of(new Component(3, seminar, 14, new ArrayList<>(), false)),
                    new ArrayList<>(), false);
            Subject subject4 = new Subject(4, "Introduction to Cryptography", 4, 2, 2, "description IC",
                    List.of(new Component(3, course, 14, new ArrayList<>(), false)),
                    new ArrayList<>(), false);
            Subject subject5 = new Subject(5, "Introduction to Computer Science", 5, 2, 2, "description ICS",
                    List.of(new Component(4, laboratory, 10, new ArrayList<>(), false)),
                    new ArrayList<>(), false);
            repository.save(subject1);
            repository.save(subject2);
            repository.save(subject3);
            repository.save(subject4);
            repository.save(subject5);
            Subject testSubject = new Subject(1, "Test", 4, 1, 1, "description Test",
                    List.of(new Component(1, course, 10, new ArrayList<>(), false)),
                    new ArrayList<>(), false);
            repository.save(testSubject);
        };
    }
}
