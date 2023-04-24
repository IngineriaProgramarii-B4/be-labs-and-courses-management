package com.example.demo.controller;

import com.example.controllers.UsersController;
import com.example.models.Admin;
import com.example.models.Student;
import com.example.models.Teacher;
import com.example.models.User;
import com.example.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

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
    public void getUsersByParamsTest() throws Exception {
        List<User> listUsers = List.of(stud1, stud2, stud3, admin1, teacher1);

        Map<String, Object> args = Collections.emptyMap();

        when(usersService.getUsersByParams(args)).thenReturn(listUsers);

        String url = "/api/v1/users";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listUsers);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }

    @Test
    public void getUsersByParamsUsernameTest() throws Exception {
        List<User> listUsers = List.of(stud3);

        Map<String, Object> args = new HashMap<>();

        args.put("username", "andreea.olariu");

        when(usersService.getUsersByParams(args)).thenReturn(listUsers);

        String url = "/api/v1/users?username=andreea.olariu";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listUsers);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }
}
