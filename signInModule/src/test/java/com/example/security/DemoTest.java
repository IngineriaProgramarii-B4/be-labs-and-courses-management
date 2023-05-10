package com.example.security;

import com.example.security.controllers.Demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = Demo.class)
class DemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "STUDENT")
    void testStudentEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/demo-controller/student"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a STUDENT page"));
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void testTeacherEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/demo-controller/teacher"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a TEACHER page"));
    }


}