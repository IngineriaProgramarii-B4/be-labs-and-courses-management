package com.example.demo.repository;

import com.example.models.Admin;
import com.example.repository.AdminsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminsRepositoryTest {
    @Autowired
    private AdminsRepository adminsRepository;

    @Test
    @DirtiesContext
    void findAdminsByParamsEmailExistsTest() {

        //
        //Given
        //
        Admin admin = new Admin(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                "testOffice",
                "testDepartment"
        );

        List<Admin> expected = List.of(admin);

        adminsRepository.save(admin);

        //
        //When
        //
        String email = "testemail@mail.com";

        List<Admin> result = adminsRepository.findAdminsByParams(
                null,
                null,
                null,
                email,
                null,
                null,
                null
        );

        //
        //Then
        //
        assertEquals(true, result.containsAll(expected));
    }

    @Test
    void findAdminsByParamsEmailNonexistentTest() {
        //
        //Given
        //
        String email = "testemail@mail.com";
        //
        //When
        //
        List<Admin> result = adminsRepository.findAdminsByParams(
                null,
                null,
                null,
                email,
                null,
                null,
                null
        );
        //
        //Then
        //
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findAdminsByParamsUsernameExistsTest() {
        //
        //Given
        //
        Admin admin = new Admin(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                "testOffice",
                "testDepartment"

        );

        adminsRepository.save(admin);

        //When
        String username = "testUser";

        List<Admin> result = adminsRepository.findAdminsByParams(
                null,
                null,
                null,
                null,
                username,
                null,
                null
        );

        //Then
        assertTrue(result.contains(admin));
    }

    @Test
    void findAdminsByParamsUsernameNonexistentTest() {

        //Given
        String username = "testUser";
        //When
        List<Admin> result = adminsRepository.findAdminsByParams(
                null,
                null,
                null,
                null,
                username,
                null,
                null
        );

        //Then
        assertTrue(result.isEmpty());
    }
}