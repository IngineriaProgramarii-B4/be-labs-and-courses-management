package com.example.configurations;

import com.example.models.Admin;
import com.example.models.Student;
import com.example.models.Teacher;
import com.example.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class UsersConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
        return args -> {
            Student stud1 = new Student(
                    "Florin",
                    "Rotaru",
                    "florin.eugen@uaic.ro",
                    "florin02",
                    2,
                    4,
                    "123FAKE92929",
                    new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
            Teacher teacher1 = new Teacher(
                    "Ciobaca",
                    "Stefan",
                    "stefan.ciobaca@uaic.com",
                    "stefan.ciobaca",
                    "C401",
                    new HashSet<>(Arrays.asList("PA", "PF")),
                    "Prof");
            Student stud2 = new Student(
                    "Antip",
                    "Andrei",
                    "andrei.antip@uaic.ro",
                    "andreiul",
                    1,
                    2,
                    "123BOSS135",
                    new HashSet<>(Arrays.asList("OOP", "SO", "PS", "FAI")));
            Student stud3 = new Student(
                    "Olariu",
                    "Andreea",
                    "andreea.olariu@uaic.ro",
                    "andreea.olariu",
                    2,
                    1,
                    "12300000GSGVGDS1",
                    new HashSet<>(Arrays.asList("RC", "LFAC", "BD", "AG", "QC")));
            Student stud4 = new Student(
                    "Duluta",
                    "George",
                    "george.duluta@yahoo.com",
                    "sfdgsdf",
                    2,
                    1,
                    "12300000GdsaS1",
                    new HashSet<>(Arrays.asList("RC", "LFAC", "BD", "AG", "QC")));
            Admin admin1 = new Admin(
                    "Cuzic",
                    "Diana",
                    "diana.cuzic@gmail.com",
                    "dianacuzic",
                    "P1",
                    "Secretariat");
            usersRepository.saveAll(List.of(stud1, teacher1, stud2, admin1, stud3, stud4));
        };
    }
}