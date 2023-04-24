package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.SubjectService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    private MockMvc mockMvc;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    //passed
    @Test
    void getAllSubjects() throws Exception {

        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Math", 5, 1, 1, "description A", new ArrayList<>(), new ArrayList<>()));
        subjects.add(new Subject(2, "English", 4, 1, 1, "description B", new ArrayList<>(), new ArrayList<>()));
        when(subjectService.getAllSubjects()).thenReturn(subjects);

        List<Subject> result = subjectController.getAllSubjects();

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getTitle());
        assertEquals("English", result.get(1).getTitle());
        assertEquals(5, result.get(0).getCredits());
        assertEquals(4, result.get(1).getCredits());
        assertEquals(1, result.get(0).getYear());
        assertEquals(1, result.get(1).getYear());
        assertEquals(1, result.get(0).getSemester());
        assertEquals(1, result.get(1).getSemester());
        assertEquals("description A", result.get(0).getDescription());
        assertEquals("description B", result.get(1).getDescription());
        assertEquals(0, result.get(0).getComponentList().size());
        assertEquals(0, result.get(1).getComponentList().size());
        assertEquals(0, result.get(0).getEvaluationList().size());
        assertEquals(0, result.get(1).getEvaluationList().size());
    }

    //passed
    @Test
    void addSubject() throws Exception {
        Subject subject = new Subject(1, "Math", 5, 1, 1, "description", new ArrayList<>(), new ArrayList<>());
        when(subjectService.addSubject(any())).thenReturn(1);

        mockMvc.perform(post("/api/v1/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 1,
                            "title": "Math",
                            "credits": 5,
                            "year": 1,
                            "semester": 1,
                            "description": "description",
                            "components": [],
                            "evaluations": []
                        }"""))
                .andExpect(status().isCreated());
    }

    //passed
    @Test
    void addSubjectThatAlreadyExists() {
        Subject subject = new Subject(1, "Math", 5, 1, 1, "description", new ArrayList<>(), new ArrayList<>());
        when(subjectService.addSubject(any())).thenReturn(0);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.addSubject(subject));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
        verify(subjectService, times(1)).addSubject(any());
    }

    //passed
    @Test
    void deleteSubjectByTitle() throws Exception {
        String title = "Math";
        when(subjectService.deleteSubjectByTitle(title)).thenReturn(1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.deleteSubjectByTitle(title));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        verify(subjectService, times(1)).deleteSubjectByTitle(eq(title));
    }

    //passed
    @Test
    void deleteSubjectByTitleNotFound() {
        String title = "Math";
        when(subjectService.deleteSubjectByTitle(eq(title))).thenReturn(0);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.deleteSubjectByTitle(title));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(subjectService, times(1)).deleteSubjectByTitle(eq(title));

    }


    //passed
    @Test
    void updateSubjectById() {
        String title = "Math";
        Subject updatedSubject = new Subject(1, "Math", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>());
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.of(new Subject()));
        when(subjectService.updateSubjectByTitle(title, updatedSubject)).thenReturn(1);

        subjectController.updateSubjectByTitle(title, updatedSubject);

        verify(subjectService, times(1)).getSubjectByTitle(title);
        verify(subjectService, times(1)).updateSubjectByTitle(title, updatedSubject);
    }

    //passed
    @Test
    void updateSubjectByIdNotFound() {
        String title = "Physics";
        Subject updatedSubject = new Subject(2, "Physics", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>());
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.updateSubjectByTitle(title, updatedSubject));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(subjectService, times(1)).getSubjectByTitle(title);
        verify(subjectService, times(0)).updateSubjectByTitle(title, updatedSubject);
    }

    //passed
    @Test
    void getSubjectByTitle() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>());
        subjectService.addSubject(subject);

        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));

        Subject result = subjectController.getSubjectByTitle("Algebraic Foundations of Science");

        assertEquals("Algebraic Foundations of Science", result.getTitle());
        assertEquals(5, result.getCredits());
        assertEquals(1, result.getYear());
        assertEquals(2, result.getSemester());
        assertEquals("not gonna pass", result.getDescription());
        assertEquals(0, result.getComponentList().size());
        assertEquals(0, result.getEvaluationList().size());
    }

    //passed
    @Test
    void getSubjectByTitleNotFound() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.getSubjectByTitle("Math"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    //failed
    @Test
    void getSubjectsByYearAndSemesterSuccessful() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Math", 5, 1, 1, "description", new ArrayList<>(), new ArrayList<>()));
        subjects.add(new Subject(2, "English", 5, 1, 1, "description", new ArrayList<>(), new ArrayList<>()));
        when(subjectService.getSubjectsByYearAndSemester(1, 1)).thenReturn(subjects);

        List<Subject> result = subjectController.getSubjectsByYearAndSemester(1, 1);

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getTitle());
        assertEquals(5, result.get(0).getCredits());
        assertEquals(1, result.get(0).getYear());
        assertEquals(1, result.get(0).getSemester());
        assertEquals("description", result.get(0).getDescription());
        assertEquals("English", result.get(1).getTitle());
        assertEquals(5, result.get(1).getCredits());
        assertEquals(1, result.get(1).getYear());
        assertEquals(1, result.get(1).getSemester());
        assertEquals("description", result.get(1).getDescription());

    }

    //passed
    @Test
    void getSubjectsByYearAndSemesterUnsuccessful() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subjectController.getSubjectsByYearAndSemester(4, 4));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
    }
}