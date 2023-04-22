package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.service.ComponentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ComponentControllerTest {

    @Mock
    private ComponentService componentService;

    private MockMvc mockMvc;

    @InjectMocks
    private ComponentController componentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(componentController).build();
    }

    @Test
    void getComponents(String title) {
        /*List<Component> components = new ArrayList<>();
        components.add(new Component(1, "Course", 8, new ArrayList<>()));
        components.add(new Component(2, "Seminar", 2, new ArrayList<>()));
        when(componentService.getComponents(title)).thenReturn(components);*/
    }

    @Test
    void addComponent() {
    }

    @Test
    void getComponentByType() {
    }

    @Test
    void deleteComponentByType() {
    }

    @Test
    void updateComponentByType() {
    }
}