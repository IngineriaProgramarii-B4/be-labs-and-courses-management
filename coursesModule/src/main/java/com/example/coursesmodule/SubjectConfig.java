package com.example.coursesmodule;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.repository.SubjectRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SubjectConfig {

    @Bean
    CommandLineRunner commandLineRunner(SubjectRepo repository) {
        return args -> {
            Subject subject1 = new Subject(1, "Maths", 4, 1, 1, "description",
                    List.of(new Component(1, "Course", 10, List.of(new Resource(1, "Book", "https://www.google.com/")))),
                    new ArrayList<>());
            Subject subject2 = new Subject(2, "Physics", 5, 1, 2, "description",
                    List.of(new Component(2, "Course", 9, List.of(new Resource(2, "Book", "https://www.google.com/")))),
                    List.of(new Evaluation(1L, "Course", 0.5f)));
            Subject subject3 = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                    List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Book", "https://www.flt-info.eu/course/crypto/")))),
                    new ArrayList<>());
            Subject subject4 = new Subject(4, "Introduction to Cryptography", 4, 2, 2, "description",
                    List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Book", "https://www.flt-info.eu/course/crypto/")))),
                    new ArrayList<>());
            Subject subject5 = new Subject(5, "Introduction to Computer Science", 5, 2, 2, "description",
                    List.of(new Component(4, "Course", 10, List.of(new Resource(4, "Book", "https://www.flt-info.eu/course/crypto/")))),
                    new ArrayList<>());
            repository.save(subject1);
            repository.save(subject2);
            repository.save(subject3);
            repository.save(subject4);
            repository.save(subject5);
        };
    }
}
