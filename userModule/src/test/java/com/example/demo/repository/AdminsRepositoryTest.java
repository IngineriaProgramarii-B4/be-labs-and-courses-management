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

        //INPUT
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

        //OUTPUT
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

        //EXPECTING
        assertEquals(true, result.containsAll(expected));
    }

    @Test
    void findAdminsByParamsEmailNonexistentTest() {

        //OUTPUT
        String email = "abracadabra@mail.com";

        List<Admin> result = adminsRepository.findAdminsByParams(
                null,
                null,
                null,
                email,
                null,
                null,
                null
        );

        //EXPECTING
        assertTrue(result.isEmpty());
    }

    @Test
    @DirtiesContext
    void findAdminsByParamsUsernameExistsTest() {

        //INPUT
        Admin admin = new Admin(
                "testName",
                "testSurname",
                "testemail@mail.com",
                "testUser",
                "testOffice",
                "testDepartment"

        );

        adminsRepository.save(admin);

        //OUTPUT
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

        //EXPECTING
        assertTrue(result.contains(admin));
    }

    @Test
    void findAdminsByParamsUsernameNonexistentTest() {

        //OUTPUT
        String username = "abracadabra";

        List<Admin> result = adminsRepository.findAdminsByParams(
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