package com.example.demo.controller;

import com.example.controllers.AdminsController;
import com.example.models.Admin;
import com.example.services.AdminsService;
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

@WebMvcTest(AdminsController.class)
public class AdminsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminsService adminsService;

    private Admin admin1;

    @BeforeEach
    public void setup() {
        admin1 = new Admin(
                UUID.fromString("jknsdn"),
                "Cuzic",
                "Diana",
                "diana.cuzic@gmail.com",
                "dianacuzic",
                "P1",
                "Secretariat");
    }

    @Test
    public void getAdminsByParamsTest() throws Exception {
        List<Admin> listAdmins = List.of(admin1);

        Map<String, Object> args = Collections.emptyMap();

        when(adminsService.getAdminsByParams(args)).thenReturn(listAdmins);

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
    public void getAdminsByParamsDepartmentTest() throws Exception {
        List<Admin> listAdmins = List.of(admin1);

        Map<String, Object> args = new HashMap<>();

        args.put("department", "Secretariat");

        when(adminsService.getAdminsByParams(args)).thenReturn(listAdmins);

        String url = "/api/v1/admins?department=Secretariat";

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
    void updateAdminTest() {

        AdminsController adminsControllerMock = mock(AdminsController.class);

        ArgumentCaptor<Admin> adminToCapture = ArgumentCaptor.forClass(Admin.class);

        doNothing().when(adminsControllerMock).updateAdmin(adminToCapture.capture().getId(), adminToCapture.capture());

        adminsControllerMock.updateAdmin(admin1.getId(), admin1);

        assertEquals(admin1, adminToCapture.getValue());
    }
}
