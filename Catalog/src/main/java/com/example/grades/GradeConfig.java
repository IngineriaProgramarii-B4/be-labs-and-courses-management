package com.example.grades;

import com.example.subject.Subject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GradeConfig {

    @Bean
    CommandLineRunner commanderLineRunner(GradeRepository repository){
        return args -> {
//            Grade grade1=new Grade(5,new Subject("PA"), "12.12.2004");
//            Grade grade2=new Grade(7,new Subject("IP"), "12.12.2005");
//            Grade grade3=new Grade(9, new Subject("PA"), "08.12.2007");
//            Grade grade4=new Grade(4,new Subject("IP"), "07.12.2009");
        };
    }
}
