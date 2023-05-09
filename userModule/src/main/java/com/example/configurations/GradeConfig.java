package com.example.configurations;

import com.example.repository.GradeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// <-------------------------------- FROM CATALOG ----------------------------------> //

@Configuration
public class GradeConfig {

    @Bean
    CommandLineRunner commanderLineRunner(GradeRepository repository){
        return args -> {
        };
    }
}
