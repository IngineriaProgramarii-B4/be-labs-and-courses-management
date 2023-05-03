package com.example;

import com.example.grades.Grade;
import com.example.subject.Subject;
import com.example.user_impl.student.Student;
import com.example.user_impl.student.StudentRepository;
import com.example.user_impl.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private Student student;
    private Grade grade;
    @BeforeEach
    public void setup() {
        studentService = new StudentService(studentRepository);
        student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        Subject subject = new Subject("PP");
        grade = new Grade(7, subject, "12.02.1996");

        studentService.save(student);
    }
    @Test
    public void canGetAllStudents() {
        // when
        studentService.getStudentDataBase();

        // then
        verify(studentRepository).getAllStudents();
    }

    @Test
    public void canGetStudentById() {

        // given

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        // when
        Optional<Student> get = Optional.ofNullable(studentService.getStudentById(student.getId()));

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Optional<Student> captured = Optional.ofNullable(studentArgumentCaptor.getValue());

        assertNotNull(get);
        assertEquals(get, captured);

        // given not existent student
        assertNull(studentService.getStudentById(9999));
    }
    @Test
    public void canAddStudent() {
        // given

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);
    }

    @Test
    public void canDeleteStudent() {
        // given

        // when
        studentService.delete(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).delete(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);

        // given null student

        Student shouldBeNull = studentService.delete(null);
        assertNull(shouldBeNull);
    }

    @Test
    public void canAddGrade() {

        // given

        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        // when
        Grade added = studentService.addGrade(student.getId(), grade);

        // then

        assertEquals(added, grade);
    }

    @Test
    public void canDeleteGrade() {

        // given

        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade deleted = studentService.deleteGrade(studentService.getStudentById(student.getId()).getId(), grade.getId());
        // then
        assertTrue(grade.isDeleted());

        // given not existent grade
        try {
            studentService.deleteGrade(studentService.getStudentById(student.getId()).getId(), 9999);
        }
        // then
        catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    @Test
    public void canGetGradeById() {

        // given
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade returned = studentService.getGradeById(studentService.getStudentById(student.getId()).getId(), grade.getId());

        // then
        assertEquals(returned, grade);

        // given not existent grade
        try {
            studentService.getGradeById(studentService.getStudentById(student.getId()).getId(), 9999);
        }
        // then
        catch (IllegalStateException e) {
            System.out.println(e);
        }
    }

    @Test
    public void canUpdateGrade() {

        // given
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade updated = studentService.updateGrade(studentService.getStudentById(student.getId()).getId(), 6, null, grade.getId());

        assertEquals(updated, grade);


        try {
            // given invalid grade
            studentService.updateGrade(studentService.getStudentById(student.getId()).getId(), 0, null, grade.getId());
        }
        // then
        catch (IllegalStateException e) {
            System.out.println(e);
        }
        finally {
            // given invalid evaluation date format
            try {
                studentService.updateGrade(studentService.getStudentById(student.getId()).getId(), 3, "ad.ad.ad", grade.getId());
            }
            // then
            catch (IllegalArgumentException e){
                System.out.println(e);
            }
        }
    }

}
