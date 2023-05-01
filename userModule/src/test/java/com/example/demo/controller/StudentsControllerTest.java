package com.example.demo.controller;

import com.example.controllers.StudentsController;
import com.example.models.Student;
import com.example.services.StudentsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentsController.class)
public class StudentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentsService studentsService;

    private Student stud1, stud2, stud3;

    @BeforeEach
    public void setup() {
        stud1 = new Student(
                UUID.fromString("fa303972-8c68-4621-957d-a448c7b1ea69"),
                "Florin",
                "Rotaru",
                "florin.eugen@uaic.ro",
                "florin02",
                2,
                4,
                "123FAKE92929",
                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
        stud2 = new Student(
                UUID.fromString("ddc60c05-2402-4349-9cbf-f4c414cbbd5e"),
                "Antip",
                "Andrei",
                "andrei.antip@uaic.ro",
                "andreiul",
                1,
                2,
                "123BOSS135",
                new HashSet<>(Arrays.asList("OOP", "SO", "PS", "FAI")));
        stud3 = new Student(
                UUID.fromString("ddc60c05-2402-4349-9cbf-f4c414cbbd5e"),
                "Olariu",
                "Andreea",
                "andreea.olariu@uaic.ro",
                "andreea.olariu",
                2,
                1,
                "12300000GSGVGDS1",
                new HashSet<>(Arrays.asList("RC", "LFAC", "BD", "AG", "QC")));
    }

    @Test
    public void getStudentsByParamsTest() throws Exception {
        List<Student> listStudents = List.of(stud1, stud2, stud3);
        Map<String, Object> args = Collections.emptyMap();
        when(studentsService.getStudentsByParams(args)).thenReturn(listStudents);
        String url = "/api/v1/students";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String expected = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listStudents);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(result, expected);
    }

    @Test
    public void getStudentsByParamsYearTest() throws Exception {
        List<Student> listStudents = List.of(stud1, stud3);

        Map<String, Object> args = new HashMap<>();

        args.put("year", "2");

        when(studentsService.getStudentsByParams(args)).thenReturn(listStudents);

        String url = "/api/v1/students?year=2";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listStudents);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }

    @Test
    void updateStudentTest() {
        StudentsController studentsControllerMock = mock(StudentsController.class);
        ArgumentCaptor<Student> studentToCapture = ArgumentCaptor.forClass(Student.class);
        UUID fakeUUID = UUID.fromString("fa303972-8c68-4621-957d-a448c7b1ea69");
        doNothing().when(studentsControllerMock).updateStudent(fakeUUID, studentToCapture.capture());
        studentsControllerMock.updateStudent(fakeUUID, stud1);
        assertEquals(stud1, studentToCapture.getValue());
    }
}
