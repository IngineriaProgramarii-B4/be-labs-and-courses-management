package com.example.demo.models;

import com.example.models.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TeacherTest {
    @Test
    void testToString()
    {
        //
        //Given
        //
        Teacher teacher = new Teacher();
        //
        // When
        //
        teacher.setFirstname("testFirstname");
        teacher.setLastname("testLastname");
        teacher.setTitle("testTitle");
        //
        //Then
        //
        assertEquals(teacher.toString(), "Teacher{teachedSubjects=[], title='testTitle', office='null', id=null, firstname='testFirstname', lastname='testLastname', email='null', username='null'}");

    }

    @Test
    void testHashCode()
    {
        //
        //Given
        //
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();

        //
        // When
        //
        teacher1.setFirstname("testFirstname");
        teacher1.setLastname("testLastname");
        teacher2.setFirstname("testFirstname");
        teacher2.setLastname("testLastname-other");
        //
        //Then
        //
        assertNotEquals(teacher1.hashCode(), teacher2.hashCode());

    }
}
