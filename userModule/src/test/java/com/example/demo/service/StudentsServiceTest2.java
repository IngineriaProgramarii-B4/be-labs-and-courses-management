package com.example.demo.service;

import com.example.models.Grade;
import com.example.models.Student;
import com.example.models.Subject;
import com.example.repository.StudentsRepository;
import com.example.services.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentsServiceTest2 {
    @InjectMocks
    StudentsService studentsService;
    @Mock
    private StudentsRepository studentsRepository;
    @Mock
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
        Subject subject = new Subject("PP");
        grade = new Grade(7, subject, "12.02.1996");

        studentsService.saveStudent(student);
    }
    @Test
    public void canGetAllStudents() {
        // when
        studentsService.getStudentsByParams(Map.of());

        // then
        verify(studentsRepository).findStudentsByParams(null, null, null, null, null, null, null, null);
    }

    @Test
    public void canGetStudentById() {

        // given

        when(studentsRepository.findById(student.getId().toString())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId().toString()));

        // when
        Optional<Student> get = Optional.ofNullable(studentsService.getStudentById(student.getId()));

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());

        Optional<Student> captured = Optional.ofNullable(studentArgumentCaptor.getValue());

        assertNotNull(get);
        assertEquals(get, captured);

        // given not existent student
        assertNull(studentsService.getStudentById(UUID.randomUUID()));
    }
    @Test
    public void canAddStudent() {
        // given

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);
    }

//    @Test
//    public void canDeleteStudent() {
//        // given
//
//        // when
//        studentsService.delete(student);
//
//        // then
//        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
//        verify(studentsRepository).delete(studentArgumentCaptor.capture());
//
//        Student captured = studentArgumentCaptor.getValue();
//        assertEquals(captured, student);
//
//        // given null student
//
//        Student shouldBeNull = studentsService.delete(null);
//        assertNull(shouldBeNull);
//    }

    @Test
    public void canAddGrade() {

        // given

        when(studentsRepository.findById(UUID.randomUUID().toString())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId().toString()));

        // when
        Grade added = studentsService.addGrade(student.getId(), grade);

        // then

        assertEquals(added, grade);
    }

    @Test
    public void canDeleteGrade() {

        // given

        when(studentsRepository.findById(UUID.randomUUID().toString())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId().toString()));

        studentsService.addGrade(student.getId(), grade);

        // when
        Grade deleted = studentsService.deleteGrade(studentsService.getStudentById(student.getId()).getId(), grade.getId());
        // then
        assertTrue(grade.isDeleted());

        // given not existent grade
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
        when(studentsRepository.findById(UUID.randomUUID().toString())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId().toString()));

        studentsService.addGrade(student.getId(), grade);

        // when
        Grade returned = studentsService.getGradeById(studentsService.getStudentById(student.getId()).getId(), grade.getId());

        // then
        assertEquals(returned, grade);

        // given not existent grade
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
        when(studentsRepository.findById(UUID.randomUUID().toString())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentsRepository.findById(student.getId().toString()));

        studentsService.addGrade(student.getId(), grade);

        // when
        Grade updated = studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 6, null, grade.getId());

        assertEquals(updated, grade);


        try {
            // given invalid grade
            studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 0, null, grade.getId());
        }
        // then
        catch (IllegalStateException e) {
            System.out.println(e);
        }
        finally {
            // given invalid evaluation date format
            try {
                studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 3, "ad.ad.ad", grade.getId());
            }
            // then
            catch (IllegalArgumentException e){
                System.out.println(e);
            }
        }
    }
}
