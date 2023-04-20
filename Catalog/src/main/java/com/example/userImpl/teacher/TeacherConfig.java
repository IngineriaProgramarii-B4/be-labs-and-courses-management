package com.example.userImpl.teacher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TeacherConfig {

    @Bean
    CommandLineRunner commandLineRunnerTeacher(TeacherRepository repository){
        return args -> {
//            Teacher teacher = new Teacher(0,"teacher1@gmail.com", "Ciobi");
//            Teacher teacher1 = new Teacher(1,"teacher2@gmail.com", "Olariu");
//            repository.saveAll(List.of(teacher,teacher1));
        };
    }
}
