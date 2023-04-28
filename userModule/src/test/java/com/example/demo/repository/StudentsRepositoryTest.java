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

        //INPUT
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

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsEmailNonexistentTest() {

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findStudentsByParamsUsernameExistsTest() {

        //INPUT
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

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsUsernameNonexistentTest() {

        //OUTPUT
        String username = "testemail@mail.com";

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

        //EXPECTING
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findStudentsByParamsRegistrationNumberExistsTest() {

        //INPUT
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

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.contains(student));
    }

    @Test
    void findStudentsByParamsRegistrationNumberNonexistentTest() {

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.isEmpty());
    }
}