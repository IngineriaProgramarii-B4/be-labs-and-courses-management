package com.example.demo.models;

import com.example.models.Student;
import com.example.models.Teacher;
import com.example.models.User;
import jakarta.validation.constraints.AssertFalse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;

public class UserTest {
    @Test
    void testEquals()
    {
        //
        //Given
        //
        User user1 = new Student();

        //
        //When
        //
        User user2 = new Teacher();

        //
        //Then
        //
        assertFalse(user1.equals(user2));
    }
}
