package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.ComponentService;
import com.example.coursesmodule.service.EvaluationService;
import com.example.coursesmodule.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationControllerTest {

    @Mock
    private SubjectService subjectService;

    @Mock
    private EvaluationService evaluationService;

    @Mock
    private ComponentService componentService;

    private MockMvc mockMvc;

    @InjectMocks
    private EvaluationController evaluationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(evaluationController).build();
    }
    @Test
    void getEvaluationMethods() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        List<Evaluation> evaluations = List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Exam", false));
        when(evaluationService.getEvaluationMethods(subject.getTitle())).thenReturn(evaluations);

        List<Evaluation> result = evaluationController.getEvaluationMethods(subject.getTitle());
        assertEquals(evaluations, result);
        assertEquals(2, result.size());
        assertEquals(3L, result.get(0).getId());
        assertEquals(4L, result.get(1).getId());
        assertEquals("Seminar", result.get(0).getComponent());
        assertEquals("Laboratory", result.get(1).getComponent());
        assertEquals(0.5F, result.get(0).getValue());
        assertEquals(0.5F, result.get(1).getValue());
        assertEquals("Test", result.get(0).getDescription());
        assertEquals("Exam", result.get(1).getDescription());
    }

    @Test
    void addEvaluationInvalid(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                        , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Laboratory", 0.5F, "Exam", false);
        when(componentService.getComponentByType(subject.getTitle(), evaluation.getComponent())).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(subject.getTitle(), evaluation));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
    }

    @Test
    void addEvaluationSubjectNotFound(){
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());
        Evaluation evaluation = new Evaluation(4L, "Laboratory", 0.5F, "Exam", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(title, evaluation));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void addEvaluationComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Laboratory", 0.5F, "Exam", false);
        when(componentService.getComponentByType(subject.getTitle(), evaluation.getComponent())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(subject.getTitle(), evaluation));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}