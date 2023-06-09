package com.example.demo.repository;

import com.example.models.Student;
import com.example.repository.StudentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentsRepositoryTest {
    @Autowired
    private StudentsRepository studentsRepository;

    @Test
    @DirtiesContext
    void findStudentsByParamsEmailExistsTest() {
        //
        //Given
        //
        Student student = new Student(
                "testName",
                "testSurename",
                "testemail@mail.com",
                "testUser",
                1,
                1,
                "testRegistrationNumber",
                null
        );

        studentsRepository.save(student);
        //
        //When
        //
        String email = "testemail@mail.com";

        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                email,
                null,
                0,
                0,
                null
        );
        //
        //Then
        //
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsEmailNonexistentTest() {
        //
        //Given
        //
        String email = "testmail@mail.com";
        //
        //When
        //
        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                email,
                null,
                0,
                0,
                null
        );
        //
        //Then
        //
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findStudentsByParamsUsernameExistsTest() {
        //
        //Given
        //
        Student student = new Student(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                1,
                1,
                "testRegistrationNumber",
                null
        );

        studentsRepository.save(student);
        //
        //When
        //
        String username = "testUser";

        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                null,
                username,
                0,
                0,
                null
        );
        //
        //Then
        //
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsUsernameNonexistentTest() {
        //
        //Given
        //
        String username = "testemail@mail.com";
        //
        //When
        //
        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                null,
                username,
                0,
                0,
                null
        );
        //
        //Then
        //
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findStudentsByParamsRegistrationNumberExistsTest() {
        //
        //Given
        //
        Student student = new Student(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                1,
                1,
                "testRegistrationNumber",
                null
        );

        studentsRepository.save(student);
        //
        //When
        //
        String regisNr = "testRegistrationNumber";

        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                null,
                null,
                1,
                1,
                regisNr
        );
        //
        //Then
        //
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsRegistrationNumberNonexistentTest() {
        //
        //Given
        //
        String regisNr = "testRegistrationNumber";
        //
        //When
        //
        List<Student> result = studentsRepository.findStudentsByParams(
                null,
                null,
                null,
                null,
                null,
                1,
                1,
                regisNr
        );
        //
        //Then
        //
        assertTrue(result.isEmpty());
    }
}