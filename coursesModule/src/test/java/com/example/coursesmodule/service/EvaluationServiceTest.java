package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @Mock
    private CourseDao courseDao;

    private EvaluationService evaluationService;

    @BeforeEach
    void setUp() {
        evaluationService = new EvaluationService(courseDao);
    }

    /*@Test
    void validateEvalReturnsTrueForValidComponent() {
        Subject subject = new Subject(1, "Maths", 4, 1, 1, "description",
                List.of(new Component(1, "Course", 10, new ArrayList<>(),false)),
                new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Maths")).thenReturn(Optional.of(subject));
        Evaluation evaluation = new Evaluation(1L,"Course", 0.2f, "Exam", false);
        assertTrue(evaluationService.validateEval("Maths", evaluation));
    }*/

    @Test
    void validateEvalReturnsFalseForInvalidComponent() {
        Evaluation evaluation = new Evaluation(1L,"Invalid", 0.2f, "Exam", false);
        assertFalse(evaluationService.validateEval("Maths", evaluation));
    }

    @Test
    void validateEvalReturnsFalseForInvalidValue(){
        Evaluation evaluation = new Evaluation(1L,"Course", 1.2f, "Exam", false);
        assertFalse(evaluationService.validateEval("Maths", evaluation));
    }

    @Test
    void validateEvalReturnsFalseForDeletedEvaluation(){
        Evaluation evaluation = new Evaluation(1L,"Course", 0.2f, "Exam", true);
        assertFalse(evaluationService.validateEval("Maths", evaluation));
    }

    @Test
    void validateComponentReturnsTrueForInvalidComponent() {
        assertFalse(evaluationService.validateComponent("Maths", "Seminar"));
        assertFalse(evaluationService.validateComponent("Maths", "Invalid"));
    }

    @Test
    void getEvaluationMethods() {
        Subject subject = new Subject(1, "Maths", 4, 1, 1, "description",
                List.of(new Component(1, "Course", 10, new ArrayList<>(),false)),
                new ArrayList<>(), false);
        Evaluation evaluation = new Evaluation(1L,"Course", 0.2f, "Exam", false);
        subject.setEvaluationList(List.of(evaluation));
        when(courseDao.getEvaluationMethods("Maths")).thenReturn(List.of(evaluation));
        assertEquals(List.of(evaluation), evaluationService.getEvaluationMethods("Maths"));
    }

    /*@Test
    void getEvaluationMethodByComponent() {
        Subject subject = new Subject(1, "Maths", 4, 1, 1, "description",
                List.of(new Component(1, "Course", 10, new ArrayList<>(),false)),
                new ArrayList<>(), false);
        Evaluation evaluation = new Evaluation(1L,"Course", 0.2f, "Exam", false);
        subject.setEvaluationList(List.of(evaluation));
        when(courseDao.getEvaluationMethodByComponent("Maths", "Course")).thenReturn(Optional.of(evaluation));
        assertEquals(Optional.of(evaluation), evaluationService.getEvaluationMethodByComponent("Maths", "Course"));
    }*/

    @Test
    void getEvaluationMethodByComponentReturnsEmptyOptionalForInvalidComponent() {
        assertEquals(Optional.empty(), evaluationService.getEvaluationMethodByComponent("Maths", "Invalid"));
    }
    @Test
    void addEvaluationMethod() {
    }

    @Test
    void addEvaluationMethodReturnsFalseForInvalidComponent() {
        Evaluation evaluation = new Evaluation(1L,"Invalid", 0.2f, "Exam", false);
        assertEquals(0, evaluationService.addEvaluationMethod("Maths", evaluation));
    }

    @Test
    void deleteEvaluationMethodByComponent() {

    }

    @Test
    void deleteEvaluationMethodByComponentReturnsFalseForInvalidComponent() {
        assertEquals(0, evaluationService.deleteEvaluationMethodByComponent("Maths", "Invalid"));
    }

    @Test
    void updateEvaluationMethodByComponent() {
    }

    @Test
    void updateEvaluationMethodByComponentReturnsFalseForInvalidComponent() {
        assertEquals(0, evaluationService.updateEvaluationMethodByComponent("Maths", "Invalid", new Evaluation()));
    }
}