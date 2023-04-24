package com.example.demo.repository;

import com.example.models.Student;
import com.example.models.User;
import com.example.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DirtiesContext
    void findUsersByParamsEmailExistsTest() {

        //INPUT
        Student student = new Student(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                0,
                0,
                "testRegistrationNumber",
                null
        );

        usersRepository.save(student);

        //OUTPUT
        String email = "testemail@mail.com";

        List<User> result = usersRepository.findUsersByParams(
                null,
                null,
                null,
                email,
                null);

        //EXPECTING
        assertTrue(result.contains(student));
    }

    @Test
    void findUsersByParamsEmailNonexistentTest() {

        //OUTPUT
        String email = "testemail@mail.com";

        List<User> result = usersRepository.findUsersByParams(
                null,
                null,
                null,
                email,
                null);

        //EXPECTING
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findUsersByParamsUsernameExistsTest() {

        //INPUT
        Student student = new Student(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                0,
                0,
                "testRegistrationNumber",
                null
        );

        usersRepository.save(student);

        //OUTPUT
        String username = "testUser";

        List<User> result = usersRepository.findUsersByParams(
                null,
                null,
                null,
                null,
                username);

        //EXPECTING
        assertTrue(result.contains(student));
    }

    @Test
    void findUsersByParamsUsernameNonexistentTest() {

        //OUTPUT
        String username = "testUser";

        List<User> result = usersRepository.findUsersByParams(
                null,
                null,
                null,
                null,
                username);

        //EXPECTING
        assertTrue(result.isEmpty());
    }
}