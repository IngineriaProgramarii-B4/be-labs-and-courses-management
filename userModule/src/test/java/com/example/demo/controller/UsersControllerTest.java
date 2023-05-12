package com.example.demo.controller;

import com.example.controllers.RemindersController;
import com.example.controllers.UsersController;
import com.example.models.Admin;
import com.example.models.Student;
import com.example.models.Teacher;
import com.example.models.User;
import com.example.repository.UsersRepository;
import com.example.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.*;

import static com.example.security.JWTGenerator.extractEmailFromTokenWithoutVerification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Mock
    UsersRepository usersRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    private Student stud1, stud2, stud3;
    private Teacher teacher1;
    private Admin admin1;

    @BeforeEach
    public void setup() {

        stud1 = new Student(
                "Florin",
                "Rotaru",
                "florin.eugen@uaic.ro",
                "florin02",
                2,
                4,
                "123FAKE92929",
                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));

        teacher1 = new Teacher(
                "Ciobaca",
                "Stefan",
                "stefan.ciobaca@uaic.com",
                "stefan.ciobaca",
                "C401",
                new HashSet<>(Arrays.asList("PA", "PF")),
                "Prof");

        stud2 = new Student(
                "Antip",
                "Andrei",
                "andrei.antip@uaic.ro",
                "andreiul",
                1,
                2,
                "123BOSS135",
                new HashSet<>(Arrays.asList("OOP", "SO", "PS", "FAI")));

        stud3 = new Student(
                "Olariu",
                "Andreea",
                "andreea.olariu@uaic.ro",
                "andreea.olariu",
                2,
                1,
                "12300000GSGVGDS1",
                new HashSet<>(Arrays.asList("RC", "LFAC", "BD", "AG", "QC")));

        admin1 = new Admin(
                "Cuzic",
                "Diana",
                "diana.cuzic@gmail.com",
                "dianacuzic",
                "P1",
                "Secretariat");
    }

    @Test
    void getUsersByParamsTest() throws Exception {
        //Given
        String url = "/api/v1/users";

        List<User> listUsers = List.of(stud1, stud2, stud3, admin1, teacher1);

        Map<String, Object> args = Collections.emptyMap();

        //When
        when(usersService.getUsersByParams(args)).thenReturn(listUsers);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        //Then
        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listUsers);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }

    @Test
    void getUsersByParamsUsernameTest() throws Exception {
        //Given
        String url = "/api/v1/users?username=andreea.olariu";

        List<User> listUsers = List.of(stud3);

        Map<String, Object> args = new HashMap<>();

        args.put("username", "andreea.olariu");

        //When
        when(usersService.getUsersByParams(args)).thenReturn(listUsers);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        //Then
        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listUsers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }

    @Test
    void getUsersByParamsNonExisting() throws Exception {
        //Given
        String url = "/api/v1/users?username=NonExisting";

        //When
        when(usersService.getUsersByParams(Map.of("username", "NonExisting"))).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), result);
    }

    @Test
    void getLoggedUserTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        //Given
        given(usersRepository.findUsersByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                eq(stud1.getEmail()),
                nullable(String.class)))
                .willReturn(List.of(stud1));
        when(usersService.getUsersByParams(Map.of("email", stud1.getEmail()))).thenReturn(List.of(stud1));

        String url = "/api/v1/users/loggedUser";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmbG9yaW4uZXVnZW5AdWFpYy5ybyIsInJvbGUiOiJTVFVERU5UIiwiaWF0IjoxNjgzNTMzODEwLCJleHAiOjE2ODM1MzM4ODB9.i2iRyk1YvxYwBNLsZNXtdPypL2PyiY2dtTzwbfabFDgvTG4UWDbA0T6o_fFaWrUeW6YW9uefb7Ma6MDEMZrkUw";

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url).content(token)).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.CREATED.value(), result);

        String value = null;
        try {
            value = extractEmailFromTokenWithoutVerification(token);
        } catch (RuntimeException e) {
            // Use assertThrows to catch the exception and verify its type and message
            Exception exception = assertThrows(Exception.class, () -> extractEmailFromTokenWithoutVerification(token));
            String actualMessage = exception.getMessage();
            String expectedMessage = "An error occurred at object mapping";
            assertEquals(expectedMessage, actualMessage);

        }
        assertEquals(stud1.getEmail(), value);

    }

    @Test
    void getLoggedUserNotFoundTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String token = "hafbsjfs";
        String url = "/api/v1/users/loggedUser";
        // Act
        ResponseEntity<User> response = usersController.getLoggedUser(token);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usersService, never()).getUsersByParams(anyMap());

    }

}
