package com.example.demo.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.example.demo.objects.Student;
import com.example.demo.objects.Subject;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.SubjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
        Student studentX = new Student("3333", "Marie", "Doe", 2, "B", "mariedoe@example.com", "password");

    }

    @Test
    public void testAddStudent() {
        String registrationNumber = "A112";
        String firstName = "John";
        String lastName = "Doe";
        String degree = "PhD";
        int year = 2;
        String grupa = "A2";
        String mail = "johndoe@example.com";
        String password = "password";
        Date createdAt = new Date();
        Date updatedAt = new Date();
        Student student = new Student(registrationNumber, firstName, lastName, year, grupa, mail, password);

        studentService.addStudent(student);
        verify(studentRepository, times(1)).save(any());

    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setRegistrationNumber("1234");
        student.setYear(2021);
        student.setGrupa("A");

        when(studentRepository.findByRegistrationNumber(student.getRegistrationNumber())).thenReturn(student);

        studentService.removeStudent(student);
        verify(studentRepository).delete(student);
    }

    @Test
    @Transactional
    public void testGetStudentByRegistrationNumber() {
        // Given
        String registrationNumber = "12345";
        Student expectedStudent = new Student(registrationNumber, "John", "Doe", 2, "A2", "johndoe@example.com", "password");
        when(studentRepository.findByRegistrationNumber(registrationNumber)).thenReturn(expectedStudent);

        // When
        Student retrievedStudent = studentService.getStudentByRegistrationNumber(registrationNumber);

        // Then
        Assertions.assertNotNull(retrievedStudent);
        Assertions.assertEquals(expectedStudent.getFirstName(), retrievedStudent.getFirstName());
        Assertions.assertEquals(expectedStudent.getLastName(), retrievedStudent.getLastName());
        Assertions.assertEquals(expectedStudent.getRegistrationNumber(), retrievedStudent.getRegistrationNumber());
    }

    @Test
    public void testGetStudentByYear() {
        Student s1 = new Student("A112", "John", "Doe", 1, "A2", "johndoe@example.com", "password");
        Student s2 = new Student("B225", "Jane", "Doe", 2, "B4", "janedoe@example.com", "mama");
        when(studentRepository.findByYear(1)).thenReturn(Arrays.asList(s1));
        when(studentRepository.findByYear(2)).thenReturn(Arrays.asList(s2));

        List<Student> students1 = studentService.getStudentByYear(1);
        List<Student> students2 = studentService.getStudentByYear(2);

        assertEquals(1, students1.size());
        assertEquals(s1.getRegistrationNumber(), students1.get(0).getRegistrationNumber());
        assertEquals(1, students2.size());
        assertEquals(s2.getRegistrationNumber(), students2.get(0).getRegistrationNumber());
    }

    @Test
    public void testGetStudentByGrupaAndYear() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("123456", "John", "Doe", 2, "A", "johndoe@example.com", "password"));
        expectedStudents.add(new Student("654321", "Jane", "Doe", 2, "A", "janedoe@example.com", "mama"));
        when(studentRepository.findByGrupaAndYear(anyString(), anyInt())).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentByGrupaAndYear("A", 2022);

        Assertions.assertEquals(expectedStudents.size(), actualStudents.size());
        Assertions.assertEquals(expectedStudents.get(0).getFirstName(), actualStudents.get(0).getFirstName());
        Assertions.assertEquals(expectedStudents.get(0).getLastName(), actualStudents.get(0).getLastName());
        Assertions.assertEquals(expectedStudents.get(1).getFirstName(), actualStudents.get(1).getFirstName());
        Assertions.assertEquals(expectedStudents.get(1).getLastName(), actualStudents.get(1).getLastName());
    }

    @Test
    void testGetStudentByFirstName() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student("111111", "John", "Doe", 1, "A", "johndoe@example.com", "password");
        Student student2 = new Student("222222", "John", "Smith", 2, "B", "johnsmith@example.com", "password");
        students.add(student1);
        students.add(student2);

        when(studentRepository.findByFirstName("John")).thenReturn(students);

        List<Student> result = studentService.getStudentByFirstName("John");
        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("Smith", result.get(1).getLastName());
    }

    @Test
    void testGetStudentByLastName() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student("111111", "John", "Doe", 1, "A", "johndoe@example.com", "password");
        Student student2 = new Student("222222", "Marie", "Doe", 2, "B", "mariedoe@example.com", "password");
        students.add(student1);
        students.add(student2);

        when(studentRepository.findByLastName("Doe")).thenReturn(students);

        List<Student> result = studentService.getStudentByLastName("Doe");
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Marie", result.get(1).getFirstName());
    }
    @Test
    public void testUpdateStudent(){
       Student existingStudent = new Student("111111", "John", "Doe", 1, "A", "johndoe@example.com", "password");
       when(studentRepository.findByRegistrationNumber(existingStudent.getRegistrationNumber())).thenReturn(existingStudent);

       Student updatedStudent = new Student("111111", "John", "Smith", 2, "B", "johndoe@example.com", "password");
       studentService.updateStudent(updatedStudent);

       verify(studentRepository, times(1)).findByRegistrationNumber(existingStudent.getRegistrationNumber());
       verify(studentRepository, times(1)).save(existingStudent);

        assertEquals(existingStudent.getLastName(), updatedStudent.getLastName());
        assertEquals(existingStudent.getGrupa(), updatedStudent.getGrupa());
        assertEquals(existingStudent.getYear(), updatedStudent.getYear());

    }

}