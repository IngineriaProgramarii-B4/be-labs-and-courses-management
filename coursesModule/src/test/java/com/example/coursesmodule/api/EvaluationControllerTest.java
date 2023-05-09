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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getEvaluationMethodsSubjectNotFound(){
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.getEvaluationMethods(title));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getEvaluationMethodByComponent() throws Exception {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar"))
                .thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        Evaluation evaluation = new Evaluation(3L, "Seminar", 0.5F, "Test", false);
        when(evaluationService.getEvaluationMethodByComponent(subject.getTitle(), "Seminar")).thenReturn(Optional.of(evaluation));
        Evaluation result = evaluationController.getEvaluationMethodByComponent(subject.getTitle(), "Seminar");
        assertEquals(evaluation.getId(), result.getId());
        assertEquals(evaluation.getComponent(), result.getComponent());
        assertEquals(evaluation.getValue(), result.getValue());
        assertEquals(evaluation.getDescription(), result.getDescription());
    }

    @Test
    void getEvaluationMethodByComponentSubjectNotFound(){
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.getEvaluationMethodByComponent(title, "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void getEvaluationMethodByComponentComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.getEvaluationMethodByComponent(subject.getTitle(), "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void getEvaluationMethodByComponentEvaluationNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar"))
                .thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(evaluationService.getEvaluationMethodByComponent(subject.getTitle(), "Seminar")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.getEvaluationMethodByComponent(subject.getTitle(), "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Evaluation method not found", exception.getReason());
    }

    @Test
    void addEvaluationSuccessful(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Seminar", 0.5F, "Exam", false);

        when(componentService.getComponentByType(subject.getTitle(), evaluation.getComponent()))
                .thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));

        when(evaluationService.addEvaluationMethod(subject.getTitle(), evaluation)).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(subject.getTitle(), evaluation));
        assertEquals(HttpStatus.CREATED, exception.getStatusCode());
    }

    @Test
    void addEvaluationInvalid(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                        , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Course", 0.5F, "Exam", false);
        when(componentService.getComponentByType(subject.getTitle(), evaluation.getComponent())).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(subject.getTitle(), evaluation));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
    }
    @Test
    void addEvaluationDeletedEval(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Course", 0.3F, "Exam", true);
        when(componentService.getComponentByType(subject.getTitle(), evaluation.getComponent())).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.addEvaluationMethod(subject.getTitle(), evaluation));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
    }
    @Test
    void addEvaluationInvalidValue(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(4L, "Course", 1.2F, "Exam", true);
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

    @Test
    void deleteEvaluationMethodByComponent(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(evaluationService.deleteEvaluationMethodByComponent(subject.getTitle(), "Seminar")).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.deleteEvaluationMethodByComponent(subject.getTitle(), "Seminar"));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        assertEquals("Evaluation method deleted successfully", exception.getReason());
    }

    @Test
    void deleteEvaluationMethodByComponentSubjectNotFound(){
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.deleteEvaluationMethodByComponent(title, "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void deleteEvaluationMethodByComponentComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.deleteEvaluationMethodByComponent(subject.getTitle(), "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void deleteEvaluationMethodByComponentEvaluationNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                new ArrayList<>(), false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.deleteEvaluationMethodByComponent(subject.getTitle(), "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Evaluation method not found", exception.getReason());
    }

    @Test
    void updateEvaluationMethodByComponent(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        Evaluation evaluation = new Evaluation(3L, "Seminar", 0.5F, "Exam", false);
        when(evaluationService.updateEvaluationMethodByComponent(subject.getTitle(), "Seminar", evaluation)).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.updateEvaluationMethodByComponent(subject.getTitle(), "Seminar", evaluation));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        assertEquals("Evaluation method updated successfully", exception.getReason());
    }

    @Test
    void updateEvaluationMethodByComponentSubjectNotFound(){
        String title = "Algebraic Foundations of Science";
        when(subjectService.getSubjectByTitle(title)).thenReturn(Optional.empty());
        Evaluation evaluation = new Evaluation(3L, "Seminar", 0.5F, "Exam", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.updateEvaluationMethodByComponent(title, "Seminar", evaluation));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void updateEvaluationMethodByComponentComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.empty());
        Evaluation evaluation = new Evaluation(3L, "Seminar", 0.5F, "Exam", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.updateEvaluationMethodByComponent(subject.getTitle(), "Seminar", evaluation));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void updateEvaluationMethodByComponentEvaluationNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, new ArrayList<>(), false)),
                new ArrayList<>(), false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        Evaluation evaluation = new Evaluation(3L, "Seminar", 0.5F, "Exam", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.updateEvaluationMethodByComponent(subject.getTitle(), "Seminar", evaluation));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Evaluation method not found", exception.getReason());
    }

}