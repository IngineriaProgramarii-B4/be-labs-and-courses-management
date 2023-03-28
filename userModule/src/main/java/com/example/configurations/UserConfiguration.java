package com.example.configurations;

import com.example.models.Student;
import com.example.models.AppUser;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class UserConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            Student stud1 = new Student(
                    1,
                    "Florin",
                    "Rotaru",
                    "florin.eugen@uaic.ro",
                    "florin02",
                    "password&xAZ1",
                    2,
                    4,
                    "123FAKE92929",
                    new HashSet<>(Arrays.asList("IP", "SO")));
            userRepository.saveAll(List.of(stud1));
        };
    }
}
