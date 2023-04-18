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
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void getUsersTest() throws Exception {
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
    public void getStudentsTest() throws Exception {
        List<Student> listStudents = List.of(stud1, stud2, stud3);

        Map<String, Object> args = Collections.emptyMap();

        when(usersService.getStudentsByParams(args)).thenReturn(listStudents);

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
    public void getTeachersTest() throws Exception {
        List<Teacher> listTeachers = List.of(teacher1);

        Map<String, Object> args = Collections.emptyMap();

        when(usersService.getTeachersByParams(args)).thenReturn(listTeachers);

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
    public void getAdminsTest() throws Exception {
        List<Admin> listAdmins = List.of(admin1);

        Map<String, Object> args = Collections.emptyMap();

        when(usersService.getAdminsByParams(args)).thenReturn(listAdmins);

        String url = "/api/v1/admins";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        String expected = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expected = objectMapper.writeValueAsString(listAdmins);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(result, expected);
    }

    @Test
    void saveStudentTest() {

        UsersController usersControllerMock = mock(UsersController.class);

        ArgumentCaptor<Student> studentToCapture = ArgumentCaptor.forClass(Student.class);

        doNothing().when(usersControllerMock).updateStudent(studentToCapture.capture());

        usersControllerMock.updateStudent(stud1);

        assertEquals(stud1, studentToCapture.getValue());
    }

    @Test
    void saveTeacherTest() {

        UsersController usersControllerMock = mock(UsersController.class);

        ArgumentCaptor<Teacher> teacherToCapture = ArgumentCaptor.forClass(Teacher.class);

        doNothing().when(usersControllerMock).updateTeacher(teacherToCapture.capture());

        usersControllerMock.updateTeacher(teacher1);

        assertEquals(teacher1, teacherToCapture.getValue());
    }

    @Test
    void saveAdminTest() {

        UsersController usersControllerMock = mock(UsersController.class);

        ArgumentCaptor<Admin> adminToCapture = ArgumentCaptor.forClass(Admin.class);

        doNothing().when(usersControllerMock).updateAdmin(adminToCapture.capture());

        usersControllerMock.updateAdmin(admin1);

        assertEquals(admin1, adminToCapture.getValue());
    }
}
