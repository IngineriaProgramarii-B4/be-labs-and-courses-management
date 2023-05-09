//package com.example.demo.service;
//
//import com.example.models.Grade;
//import com.example.models.Student;
//import com.example.models.Subject;
//import com.example.repository.StudentsRepository;
//import com.example.services.StudentsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.ArgumentMatchers.nullable;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//class StudentsServiceTest2 {
//    @InjectMocks
//    StudentsService studentsService;
//
//    @Mock
//    StudentsRepository studentsRepository;
//
//    private Student student;
//    private Grade grade;
//    @BeforeEach
//    public void setup() {
//        student = new Student(
//                UUID.randomUUID(),
//                "Florin",
//                "Rotaru",
//                "florin.eugen@uaic.ro",
//                "florin02",
//                2,
//                4,
//                "123FAKE92929",
//                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
//        Subject subject = new Subject("PP");
//        grade = new Grade(7, subject, "12.02.1996");
//    }
//    @Test
//    public void canGetAllStudents() {
//        // given
//        given(studentsRepository.findStudentsByParams(
//                nullable(UUID.class),
//                nullable(String.class),
//                nullable(String.class),
//                nullable(String.class),
//                nullable(String.class),
//                anyInt(),
//                anyInt(),
//                nullable(String.class)))
//                .willReturn(List.of(student));
//        // when
//        Map<String, Object> args = new HashMap<>();
//        List<Student> result = studentsService.getStudentsByParams(args);
//
//        // then
//        assertTrue(result.contains(student));
//    }
//
//    @Test
//    public void canGetStudentById() {
//        // given
//
//        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));
//
//        // when
//        Optional<Student> get = Optional.ofNullable(studentsService.getStudentById(student.getId()));
//
//        //then
//        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
//        studentsRepository.save(studentArgumentCaptor.capture());
//
//        // Verify that save() method is called with the expected argument
//        verify(studentsRepository).save(studentArgumentCaptor.capture());
//
//        Optional<Student> captured = Optional.ofNullable(studentArgumentCaptor.getValue());
//
//        assertNotNull(get);
//        assertEquals(get, captured);
//
//        // given not existent student
//        assertNull(studentsService.getStudentById(UUID.randomUUID()));
//    }
//    @Test
//    public void canAddStudent() {
//        // given
//
//        // then
//        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
//        studentsRepository.save(studentArgumentCaptor.capture());
//        verify(studentsRepository).save(studentArgumentCaptor.capture());
//
//        Optional<Student> get = Optional.ofNullable(studentsService.getStudentById(student.getId()));
//        Optional<Student> captured = Optional.ofNullable(studentArgumentCaptor.getValue());
//        assertEquals(captured, get);
//    }
//
//    /* In serviciu nu exista metoda de delete */
//
////    @Test
////    public void canDeleteStudent() {
////        // given
////
////        // when
////        studentsService.delete(student);
////
////        // then
////        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
////        verify(studentsRepository).delete(studentArgumentCaptor.capture());
////
////        Student captured = studentArgumentCaptor.getValue();
////        assertEquals(captured, student);
////
////        // given null student
////
////        Student shouldBeNull = studentsService.delete(null);
////        assertNull(shouldBeNull);
////    }
//
//    @Test
//    public void canAddGrade() {
//        // given
//        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));
//
//        given(studentsService.getStudentById(student.getId()))
//                .willReturn(student);
//
//        // when
//        Grade added = studentsService.addGrade(studentsService.getStudentById(student.getId()).getId(), grade);
//        // then
//        assertEquals(added, grade);
//    }
//
//    @Test
//    public void canDeleteGrade() {
//
//        // given
//
//        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));
//
//
//        given(studentsService.getStudentById(student.getId()))
//                .willReturn(student);
////        given(studentsService.addGrade(student.getId(), grade))
////                .willReturn(grade);
//
//        studentsService.addGrade(student.getId(), grade);
//
//        // when
//        Grade deleted = studentsService.deleteGrade(studentsService.getStudentById(student.getId()).getId(), grade.getId());
//        // then
//        assertTrue(grade.isDeleted());
//
//        // given not existent grade
//        try {
//            studentsService.deleteGrade(studentsService.getStudentById(student.getId()).getId(), 9999);
//        }
//        // then
//        catch (NullPointerException e) {
//            System.out.println(e);
//        }
//    }
//
//    @Test
//    public void canGetGradeById() {
//
//        // given
//        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));
//
//        given(studentsService.getStudentById(student.getId()))
//                .willReturn(student);
//
//        studentsService.addGrade(student.getId(), grade);
//        // when
//        Grade returned = studentsService.getGradeById(studentsService.getStudentById(student.getId()).getId(), grade.getId());
//
//        // then
//        assertEquals(returned, grade);
//
//        // given not existent grade
//        try {
//            studentsService.getGradeById(studentsService.getStudentById(student.getId()).getId(), 9999);
//        }
//        // then
//        catch (IllegalStateException e) {
//            System.out.println(e);
//        }
//    }
//
//    @Test
//    public void canUpdateGrade() {
//
//        // given
//        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        assertEquals(Optional.of(student), studentsRepository.findById(student.getId()));
//
//        given(studentsService.getStudentById(student.getId()))
//                .willReturn(student);
//        studentsService.addGrade(student.getId(), grade);
//
//        // when
//        Grade updated = studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 6, null, grade.getId());
//
//        assertEquals(updated, grade);
//
//
//        try {
//            // given invalid grade
//            studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 0, null, grade.getId());
//        }
//        // then
//        catch (IllegalStateException e) {
//            System.out.println(e);
//        }
//        finally {
//            // given invalid evaluation date format
//            try {
//                studentsService.updateGrade(studentsService.getStudentById(student.getId()).getId(), 3, "ad.ad.ad", grade.getId());
//            }
//            // then
//            catch (IllegalArgumentException e){
//                System.out.println(e);
//            }
//        }
//    }
//}
