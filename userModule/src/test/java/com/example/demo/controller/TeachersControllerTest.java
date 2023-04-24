package com.example.demo.controller;

import com.example.controllers.TeachersController;
import com.example.models.Teacher;
import com.example.services.TeachersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

@WebMvcTest(TeachersController.class)
public class TeachersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeachersService teachersService;

    private Teacher teacher1;

    @BeforeEach
    public void setup() {
        teacher1 = new Teacher(
                "Ciobaca",
                "Stefan",
                "stefan.ciobaca@uaic.com",
                "stefan.ciobaca",
                "C401",
                new HashSet<>(Arrays.asList("PA", "PF")),
                "Prof");
    }

    @Test
    public void getTeachersByParamsTest() throws Exception {
        List<Teacher> listTeachers = List.of(teacher1);

        Map<String, Object> args = Collections.emptyMap();

        when(teachersService.getTeachersByParams(args)).thenReturn(listTeachers);

        String url = "/api/v1/teachers";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listTeachers);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }

    @Test
    public void getTeachersByParamsTitleTest() throws Exception {
        List<Teacher> listTeachers = List.of(teacher1);

        Map<String, Object> args = new HashMap<>();

        args.put("title", "Prof");

        when(teachersService.getTeachersByParams(args)).thenReturn(listTeachers);

        String url = "/api/v1/teachers?title=Prof";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listTeachers);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }

    @Test
    void updateTeacherTest() {

        TeachersController teachersControllerMock = mock(TeachersController.class);

        ArgumentCaptor<Teacher> teacherToCapture = ArgumentCaptor.forClass(Teacher.class);

        doNothing().when(teachersControllerMock).updateTeacher(teacherToCapture.capture());

        teachersControllerMock.updateTeacher(teacher1);

        assertEquals(teacher1, teacherToCapture.getValue());
    }
}
