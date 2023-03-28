package com.example.coursesmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RestController
public class CoursesModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursesModuleApplication.class, args);
    }
}
