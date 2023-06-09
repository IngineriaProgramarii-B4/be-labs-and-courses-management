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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentsController.class)
class StudentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentsService studentsService;

    private Student stud1, stud2, stud3;

    @BeforeEach
    public void setup() {
        stud1 = new Student(
                UUID.randomUUID(),
                "Florin",
                "Rotaru",
                "florin.eugen@uaic.ro",
                "florin02",
                2,
                4,
                "123FAKE92929",
                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
        stud2 = new Student(
                UUID.randomUUID(),
                "Antip",
                "Andrei",
                "andrei.antip@uaic.ro",
                "andreiul",
                1,
                2,
                "123BOSS135",
                new HashSet<>(Arrays.asList("OOP", "SO", "PS", "FAI")));
        stud3 = new Student(
                UUID.randomUUID(),
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
    void getStudentsByParamsTest() throws Exception {
        //Given
        String url = "/api/v1/students";

        List<Student> listStudents = List.of(stud1, stud2, stud3);

        Map<String, Object> args = Collections.emptyMap();

        when(studentsService.getStudentsByParams(args)).thenReturn(listStudents);

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
    void getStudentsByParamsYearTest() throws Exception {
        //Given
        String url = "/api/v1/students?year=2";

        List<Student> listStudents = List.of(stud1, stud3);

        Map<String, Object> args = new HashMap<>();

        args.put("year", "2");

        //When
        when(studentsService.getStudentsByParams(args)).thenReturn(listStudents);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        //Then
        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listStudents);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }

    @Test
    void getStudentsByParamsNonExisting() throws Exception {
        //Given
        String url = "/api/v1/students?lastname=NonExisting";

        //When
        when(studentsService.getStudentsByParams(Map.of("lastname", "NonExisting"))).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), result);
    }

    @Test
    void updateStudentTest() throws Exception {
        //Given
        String url = "/api/v1/student/" + stud1.getId();

        //When
        when(studentsService.getStudentsByParams(Map.of("id", stud1.getId()))).thenReturn(List.of(stud1));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(ObjectToJson(stud1))).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.NO_CONTENT.value(), result);
    }

    @Test
    void updateStudentNonExistentTest() throws Exception {
        //Given
        String url = "/api/v1/student/" + stud1.getId();

        //When
        when(studentsService.getStudentsByParams(Map.of("id", stud1.getId()))).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(ObjectToJson(stud1))).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), result);
    }

    @Test
    void saveStudentTest() throws Exception {
        //Given
        String url = "/api/v1/students";

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(ObjectToJson(stud1))).andReturn();

        int result = mvcResult.getResponse().getStatus();

        //Then
        assertEquals(HttpStatus.CREATED.value(), result);
    }

    String ObjectToJson(Student student) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(student);
    }
}
