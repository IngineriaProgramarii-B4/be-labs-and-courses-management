package com.example.demo.service;

import com.example.models.Grade;
import com.example.models.Student;
import com.example.models.Subject;
import com.example.repository.StudentsRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CatalogStudentsServiceTest {
    @InjectMocks
    StudentsService studentsService;
    @Mock
    StudentsRepository studentsRepository;

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

        studentsService.saveStudent(student);

    }
    @Test
    public void canGetAllStudents() {
        // given
        given(studentsRepository.findStudentsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                anyInt(),
                anyInt(),
                nullable(String.class)))
                .willReturn(List.of(student));
        // when
        Map<String, Object> args = new HashMap<>();
        List<Student> result = studentsService.getStudentsByParams(args);

        // then
        assertTrue(result.contains(student));
    }

    @Test
    public void canGetStudentById() {
        // given

        when(studentsRepository.findStudentById(student.getId())).thenReturn(student);
        assertEquals(Optional.of(student).get(), studentsRepository.findStudentById(student.getId()));

        // when
        Optional<Student> get = Optional.ofNullable(studentsService.getStudentById(student.getId()));

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        // Verify that save() method is called with the expected argument
        verify(studentsRepository).save(studentArgumentCaptor.capture());

        Optional<Student> captured = Optional.of(studentArgumentCaptor.getValue());

        assertNotNull(get);
        assertEquals(get, captured);

        // given not existing student
        assertNull(studentsService.getStudentById(UUID.randomUUID()));
    }
    @Test
    public void canSaveStudent() {
        // given
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);

        // given null student
        Student shouldBeNull = studentsService.saveStudent(null);
        assertNull(shouldBeNull);
    }
    @Test
    public void canDeleteStudent() {
        // given
        when(studentsRepository.findStudentById(student.getId())).thenReturn(student);
        // when
        Student deleted = studentsService.deleteStudent(student.getId());

        // then
        assertTrue(deleted.isDeleted());

        // given not existing student
        Student shouldBeNull = studentsService.deleteStudent(UUID.randomUUID());
        assertNull(shouldBeNull);
    }

    @Test
    public void canAddGrade() {
        // given
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));

        given(studentsService.getStudentById(student.getId()))
                .willReturn(student);

        // when
        Grade added = studentsService.addGrade(studentsService.getStudentById(student.getId()).getId(), grade);
        // then
        assertEquals(added, grade);
    }

    @Test
    public void canDeleteGrade() {

        // given

        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));


        given(studentsService.getStudentById(student.getId()))
                .willReturn(student);
//        given(studentsService.addGrade(student.getId(), grade))
//                .willReturn(grade);

        studentsService.addGrade(student.getId(), grade);

        // when
        studentsService.deleteGrade(studentsService.getStudentById(student.getId()).getId(), grade.getId());
        // then
        assertTrue(grade.isDeleted());

        // given not existing grade
        try {
            studentsService.deleteGrade(studentsService.getStudentById(student.getId()).getId(), 9999);
        }
        // then
        catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    @Test
    public void canGetGradeById() {

        // given
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));

        given(studentsService.getStudentById(student.getId()))
                .willReturn(student);

        studentsService.addGrade(student.getId(), grade);
        // when
        Grade returned = studentsService.getGradeById(studentsService.getStudentById(student.getId()).getId(), grade.getId());

        // then
        assertEquals(returned, grade);

        // given not existing grade
        try {
            studentsService.getGradeById(studentsService.getStudentById(student.getId()).getId(), 9999);
        }
        // then
        catch (IllegalStateException e) {
            System.out.println(e);
        }
    }

    @Test
    public void canUpdateGrade() {

        // given
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));

        given(studentsService.getStudentById(student.getId()))
                .willReturn(student);
        studentsService.addGrade(student.getId(), grade);

        // when
        Grade updated = studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 6, null, grade.getId());

        assertEquals(updated, grade);

        // given not existing student
        Student shouldBeNull = studentsService.deleteStudent(UUID.randomUUID());
        assertNull(shouldBeNull);

        try {
            // given invalid grade
            studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 0, null, grade.getId());

        }
        // then
        catch (IllegalStateException e) {
            System.out.println(e);

            // given invalid eval date

            studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 3, "ad.ad.ad", grade.getId());

            // then should be changed to default value
            assertEquals("01.01.1980", grade.getEvaluationDate());
        }
    }

    @Test
    void canUpdateGradeTest() {

        // given
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));

        given(studentsService.getStudentById(student.getId()))
                .willReturn(student);
        studentsService.addGrade(student.getId(), grade);

        // when updating with non-existing student ID
        Grade shouldBeNull = studentsService.updateGrade(UUID.randomUUID(), 6, null, grade.getId());
        assertNull(shouldBeNull);

        // when updating with null value
        Grade updatedNullValue = studentsService.updateGrade(student.getId(), null, null, grade.getId());
        assertEquals(grade, updatedNullValue);

        // when updating with invalid value
        assertThrows(IllegalStateException.class,
                () -> studentsService.updateGrade(student.getId(), 0, null, grade.getId()));

        // when updating with value outside of range
        assertThrows(IllegalStateException.class,
                () -> studentsService.updateGrade(student.getId(), 11, null, grade.getId()));

        // when updating with a valid value
        Grade updatedValue = studentsService.updateGrade(student.getId(), 8, null, grade.getId());
        assertEquals(8, updatedValue.getValue());

        // when updating with invalid evaluation date
        Grade updatedInvalidDate = studentsService.updateGrade(student.getId(), 8, "invalid date", grade.getId());
        assertEquals("01.01.1980", updatedInvalidDate.getEvaluationDate());

        // when updating with valid evaluation date
        Grade updatedValidDate = studentsService.updateGrade(student.getId(), 8, "10.05.2023", grade.getId());
        assertEquals("10.05.2023", updatedValidDate.getEvaluationDate());

        // when updating with the same evaluation date
        Grade updatedSameDate = studentsService.updateGrade(student.getId(), 8, "05.11.2022", grade.getId());
        assertEquals("05.11.2022", updatedSameDate.getEvaluationDate());

        // when updating with null value and different evaluation date
        Grade updatedNullValueAndDate = studentsService.updateGrade(student.getId(), null, "01.01.2022", grade.getId());
        assertEquals("01.01.2022", updatedNullValueAndDate.getEvaluationDate());
        assertEquals(grade, updatedNullValueAndDate);

        // when updating with the same evaluation date

        updatedSameDate = studentsService.updateGrade(student.getId(), 8, grade.getEvaluationDate(), grade.getId());
        assertEquals(grade.getEvaluationDate(), updatedSameDate.getEvaluationDate());


    }

    @Test
    void testToString() {
        // create a subject object
        Subject subject = new Subject(0, "Test", 6, 2022, 1, "Mathematics course", null, null, false);

        // create a grade object
        Grade grade = new Grade( 9, subject, "12.12.2012");

        // expected output
        String expectedOutput = "Grade{gradeId=0value=9, subject=Subject{id=0, title='Test', credits=6, year=2022, semester=1, description='Mathematics course', componentList=null, evaluationList=null, image=null, isDeleted=false}, evaluation date=12.12.2012}";

        // actual output
        String actualOutput = grade.toString();

        // compare the actual output with the expected output
        assertEquals(expectedOutput, actualOutput);
    }

}