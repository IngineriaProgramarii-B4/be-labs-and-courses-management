package com.example.demo.repository;

import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class TeachersRepositoryTest {

    @Autowired
    private TeachersRepository teachersRepository;

    @Test
    @DirtiesContext
    void findTeachersByParamsEmailExistsTest() {

        //INPUT
        Teacher teacher = new Teacher(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                "testOffice",
                null,
                "testTitle"
        );

        teachersRepository.save(teacher);

        //OUTPUT
        String email = "testemail@mail.com";

        List<Teacher> result = teachersRepository.findTeachersByParams(
                null,
                null,
                null,
                email,
                null,
                null,
                null
        );

        //EXPECTING
        assertTrue(result.contains(teacher));
    }

    @Test
    void findTeachersByParamsEmailNonexistentTest() {

        //OUTPUT
        String email = "testemail@gmail.com";

        List<Teacher> result = teachersRepository.findTeachersByParams(
                null,
                null,
                email,
                null,
                null,
                null,
                null
        );

        //EXPECTING
        assertTrue(result.isEmpty());
    }



    @Test
    @DirtiesContext
    void findTeachersByParamsUsernameExistsTest() {

        //INPUT
        Teacher teacher = new Teacher(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                "testOffice",
                null,
                "testTitle"
        );

        teachersRepository.save(teacher);

        //OUTPUT
        String username = "testUser";

        List<Teacher> result = teachersRepository.findTeachersByParams(
                null,
                null,
                null,
                null,
                username,
                null,
                null
        );

        //EXPECTING
        assertTrue(result.contains(teacher));
    }

    @Test
    void findTeachersByParamsUsernameNonexistentTest() {

        //OUTPUT
        String username = "testUser";

        List<Teacher> result = teachersRepository.findTeachersByParams(
                null,
                null,
                null,
                null,
                username,
                null,
                null
        );

        //EXPECTING
        assertTrue(result.isEmpty());
    }
}