package com.example.coursesmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.coursesmodule.*")
@EntityScan("com.example.coursesmodule.*")
@SpringBootApplication
public class CoursesModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursesModuleApplication.class, args);
    }
}
