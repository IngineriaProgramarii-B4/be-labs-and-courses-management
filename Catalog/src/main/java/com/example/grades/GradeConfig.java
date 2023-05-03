package com.example.grades;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GradeConfig {

    @Bean
    CommandLineRunner commanderLineRunner(GradeRepository repository){
        return args -> {
        };
    }
}
