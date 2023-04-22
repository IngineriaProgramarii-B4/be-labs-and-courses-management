package com.example.coursesmodule;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.repository.SubjectRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SubjectConfig {

    @Bean
    CommandLineRunner commandLineRunner(SubjectRepo repository) {
        return args -> {
            Subject subject1 = new Subject(1, "Maths", 4, 1, 1, "description",
                    Arrays.asList(new Component(1, "Course", 10, Arrays.asList(new Resource(1, "Book", "https://www.google.com/")))),
                    new ArrayList<>());
            repository.save(subject1);
        };
    }
}
