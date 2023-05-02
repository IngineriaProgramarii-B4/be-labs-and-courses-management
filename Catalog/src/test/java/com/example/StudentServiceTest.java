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
    @BeforeEach
    public void setup() {
        studentService = new StudentService(studentRepository);
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
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        studentService.save(student);

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
    }
    @Test
    public void canAddStudent() {
        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        // when
        studentService.save(student);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);
    }

    @Test
    public void canDeleteStudent() {
        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");

        // when
        studentService.save(student);
        studentService.delete(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).delete(studentArgumentCaptor.capture());

        Student captured = studentArgumentCaptor.getValue();
        assertEquals(captured, student);
    }

    @Test
    public void canAddGrade() {

        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        Subject subject = new Subject("PP");
        Grade grade = new Grade(7, subject, "12.02.1996");
        studentService.save(student);

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        // when
        Grade added = studentService.addGrade(student.getId(), grade);

        // then

        assertEquals(added, grade);
    }

    @Test
    public void canDeleteGrade() {

        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        Subject subject = new Subject("PP");
        Grade grade = new Grade(7, subject, "12.02.1996");
        studentService.save(student);

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade deleted = studentService.deleteGrade(studentService.getStudentById(student.getId()).getId(), grade.getId());
        // then
        assertNull(deleted);
    }

    @Test
    public void canGetGradeById() {

        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        Subject subject = new Subject("PP");
        Grade grade = new Grade(7, subject, "12.02.1996");
        studentService.save(student);

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade returned = studentService.getGradeById(studentService.getStudentById(student.getId()).getId(), grade.getId());

        // then

        assertEquals(returned, grade);
    }

    @Test
    public void canUpdateGrade() {

        // given
        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
        Subject subject = new Subject("PP");
        Grade grade = new Grade(7, subject, "12.02.1996");
        studentService.save(student);

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(Optional.of(student), studentRepository.findById(student.getId()));

        studentService.addGrade(student.getId(), grade);

        // when
        Grade updated = studentService.updateGrade(studentService.getStudentById(student.getId()).getId(), 6, null, grade.getId());

        assertEquals(updated, grade);
    }

}
