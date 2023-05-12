package com.example.demo.service;

import com.example.models.Grade;
import com.example.models.Student;
import com.example.models.Subject;
import com.example.repository.GradeRepository;
import com.example.repository.StudentsRepository;
import com.example.services.GradeService;
import com.example.services.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GradeServiceTest {

    @InjectMocks
    GradeService gradeService;
    @Mock
    GradeRepository gradeRepository;
    private Student student;
    private Grade grade;

    @BeforeEach
    public void setup() {
        student = new Student(
                UUID.randomUUID(),
                "Florin",
                "Rotaru",
                "florin.eugen@uaic.ro",
                "florin02",
                2,
                4,
                "123FAKE92929",
                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
        Subject subject = new Subject(69, "Mocked", 6, 2, 3, null, null,null,false);
        grade = new Grade(7, subject, "12.02.1996");
    }
    @Test
    void canGetGradeById() {
        when(gradeRepository.findById(grade.getId())).thenReturn(Optional.of(grade));
        assertEquals(Optional.of(grade), gradeRepository.findById(grade.getId()));
        // given existing grade
        Optional<Grade> get = Optional.ofNullable(gradeService.getGradeById(grade.getId()));

        //then

        ArgumentCaptor<Grade> gradeArgumentCaptor = ArgumentCaptor.forClass(Grade.class);
        gradeRepository.save(gradeArgumentCaptor.capture());

        verify(gradeRepository).save(gradeArgumentCaptor.capture());

        Optional<Grade> captured = Optional.ofNullable(gradeArgumentCaptor.getValue());

        assertNotNull(get);
        assertEquals(get, captured);

        // given not existing grade
        assertNull(gradeService.getGradeById(new Random().nextInt()));
    }

    @Test
    void canGetGradesTest() {
        // when
        gradeService.getGrades();

        // then
        verify(gradeRepository).findAll();

    }
}