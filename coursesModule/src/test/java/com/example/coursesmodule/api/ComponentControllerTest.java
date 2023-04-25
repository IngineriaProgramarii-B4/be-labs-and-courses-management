package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.ComponentService;
import com.example.coursesmodule.service.SubjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ComponentControllerTest {

    @Mock
    private SubjectService subjectService;
    @Mock
    private ComponentService componentService;

    private MockMvc mockMvc;

    @InjectMocks
    private ComponentController componentController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(componentController).build();
    }

    @Test
    void getComponents() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        List<Component> components = new ArrayList<>();
        components.add(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/"))));
        when(componentService.getComponents(subject.getTitle())).thenReturn(components);

        List<Component> result = componentController.getComponents(subject.getTitle());

        assertEquals(1, result.size());
        assertEquals("Seminar", result.get(0).getType());
        assertEquals(14, result.get(0).getNumberWeeks());
        assertEquals(1, result.get(0).getResources().size());
    }

    @Test
    void testAddComponentSuccessful() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        Component component = new Component(3,"Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")));

        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.addComponent(title, component)).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.addComponent(title, component);
        });
        assertEquals(HttpStatus.CREATED, exception.getStatusCode());
        verify(componentService, times(1)).addComponent(title, component);
    }

    @Test
    void testAddInvalidComponent() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        Component component = new Component(3,"Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")));

        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.addComponent(title, component)).thenReturn(0);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.addComponent(title, component);
        });
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
        verify(componentService, times(1)).addComponent(title, component);
    }

    @Test
    void testAddComponentInvalidSubject() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        Component component = new Component(3,"Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")));

        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.addComponent(title, component);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(0)).addComponent(title, component);
    }

    @Test
    void getComponentByTypeSuccessful() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Component component = new Component(3,"Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")));
        when(componentService.getComponentByType(title, "Seminar")).thenReturn(Optional.of(component));

        Component result = componentController.getComponentByType(title, "Seminar");

        assertEquals("Seminar", result.getType());
        assertEquals(14, result.getNumberWeeks());
        assertEquals(1, result.getResources().size());
    }

    @Test
    void getComponentByTypeInvalidSubject() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.getComponentByType(title, "Seminar");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(0)).getComponentByType(title, "Seminar");
    }

    @Test
    void getComponentByTypeInvalidComponent() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(title, "Seminar")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.getComponentByType(title, "Seminar");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(1)).getComponentByType(title, "Seminar");
    }

    @Test
    void deleteComponentByTypeSuccesful() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.deleteComponentByType(title, "Seminar")).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.deleteComponentByType(title, "Seminar");
        });
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        verify(componentService, times(1)).deleteComponentByType(title, "Seminar");
    }

    @Test
    void deleteComponentByTypeInvalidSubject() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.deleteComponentByType(title, "Seminar");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(0)).deleteComponentByType(title, "Seminar");
    }

    @Test
    void deleteComponentByTypeInvalidComponent() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.deleteComponentByType(title, "Seminar")).thenReturn(0);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.deleteComponentByType(title, "Seminar");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(1)).deleteComponentByType(title, "Seminar");
    }

    @Test
    void updateComponentByType() {

        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Component component = new Component(3,"Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")));
        when(componentService.updateComponentByType(title, "Seminar", component)).thenReturn(1);

        componentController.updateComponentByType(title, "Seminar", component);
        verify(subjectService, times(1)).getSubjectByTitle(title);
        verify(componentService, times(1)).updateComponentByType(title, "Seminar", component);
    }

    @Test
    void updateComponentByTypeInvalidSubject() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, List.of(new Resource(3, "Seminar", "https://www.flt-info.eu/course/afcs/")))),
                new ArrayList<>());
        subjectService.addSubject(subject);
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            componentController.updateComponentByType(title, "Seminar", new Component());
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(componentService, times(0)).updateComponentByType(title, "Seminar", new Component());
    }

    @Test
    void testUpdateComponentByTypeComponentInvalid() throws Exception {
        String title = "Algebraic Foundations of Science";
        String type = "Seminar";
        mockMvc.perform(MockMvcRequestBuilders.put("/subjects/{subjectTitle}/components/type={type}", title, type)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Seminar\",\"numberWeeks\":14,\"resources\":[{\"name\":\"Seminar\",\"url\":\"https://www.flt-info.eu/course/afcs/\"}]}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}