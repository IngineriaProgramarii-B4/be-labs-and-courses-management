package com.example.demo.models;

import com.example.models.Student;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class StudentTest {
    @Test
    void tesToString()
    {
        //
        //Given
        //
        Student student = new Student();

        //
        //When
        //
        student.setFirstname("testFirstname");
        student.setLastname("testLastname");

        //
        //Then
        //
        assertEquals("Student{enrolledCourses=[], year=0, semester=0, registrationNumber='null', id=null, firstname='testFirstname', lastname='testLastname', email='null', username='null'}",student.toString());

    }

    @Test
    void testAddEnrolledCourse()
    {
        //
        //Given
        //
        Student student = new Student();
        Set<String> courses = new HashSet<>();
        courses.add("testCourse");
        //
        //When
        //
        student.addEnrolledCourse("testCourse");
        //
        //Then
        //
        assertEquals(student.getEnrolledCourses(), courses);

    }

    @Test
    void testHashCode()
    {
        //
        //Given
        //
        Student student1 = new Student();
        Student student2 = new Student();
        //
        //When
        //
        student1.setFirstname("testFirstname");
        student1.setLastname("testLastname");
        student2.setFirstname("testFirstname");
        student2.setLastname("testLastname");
        //
        //Then
        //
        assertEquals(student1.hashCode(), student2.hashCode());

    }
}
