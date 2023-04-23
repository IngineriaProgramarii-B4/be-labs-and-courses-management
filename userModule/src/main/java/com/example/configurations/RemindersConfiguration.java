package com.example.configurations;

import com.example.models.Reminder;
import com.example.models.Student;
import com.example.repository.RemindersRepository;
import com.example.repository.StudentsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemindersConfiguration {

    @Bean
    CommandLineRunner commandLineRunnerForReminders(StudentsRepository studentsRepository, RemindersRepository remindersRepository) {
        Student tempStudent = new Student(
                "Organized",
                "Student",
                "ilovereminders@yahoo.com",
                "reminderUser409",
                3,
                2,
                "123REM456",
                null
        );

        Student tempStudent2 = new Student(
                "logged",
                "user",
                "reminderOwner@yahoo.com",
                "loggeduser",
                3,
                2,
                "542REM456",
                null
        );

        studentsRepository.save(tempStudent);

        studentsRepository.save(tempStudent2);

        return args -> {
            Reminder reminder1 = new Reminder(
                    tempStudent,
                    "25.04.2023 14:30",
                    "Examen la pedagogie",
                    "Examen maine la pedagogie la ora 16 in C2"
            );

            Reminder reminder2 = new Reminder(
                    tempStudent2,
                    "06.06.2023 11:30",
                    "Examen-IP",
                    "Primul examen din sesiune"
            );

            Reminder reminder3 = new Reminder(
                    tempStudent2,
                    "08.06.2023 10:00",
                    "Examen-OF",
                    "Al doilea examen din sesiune"
            );

            remindersRepository.save(reminder1);
            remindersRepository.save(reminder2);
            remindersRepository.save(reminder3);
        };
    }
}
