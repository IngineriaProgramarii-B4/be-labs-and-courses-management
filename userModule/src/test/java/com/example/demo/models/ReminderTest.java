package com.example.demo.models;

import com.example.models.Reminder;
import io.github.classgraph.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReminderTest {
    @Test
    void testGetDeleted()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setDeleted(false);

        //
        //Then
        //
        assertFalse(reminder.getDeleted());

    }

    @Test
    void testSetDeleted()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setDeleted(false);

        //
        //Then
        //
        assertFalse(reminder.getDeleted());

    }

    @Test
    void testSetCreatorUsername()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setCreatorUsername("testUsername");

        //
        //Then
        //
        assertEquals(reminder.getCreatorUsername(), "testUsername");

    }

    @Test
    void testSetCreatorId()
    {
        //
        //Given
        //
        UUID id = new UUID(0x101abc, 0x99eff);
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setCreatorId(id);

        //
        //Then
        //
        assertEquals(reminder.getCreatorId(), id);

    }

    @Test
    void testSetId()
    {
        //
        //Given
        //
        UUID id = new UUID(0x101abc, 0x99eff);
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setId(id);

        //
        //Then
        //
        assertEquals(reminder.getId(), id);

    }

    @Test
    void testSetDueDateTime()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        LocalDateTime localDateTime = LocalDateTime.now();
        reminder.setDueDateTime(localDateTime);

        //
        //Then
        //
        assertEquals(reminder.getDueDateTime(), localDateTime);

    }

    @Test
    void testSetTitle()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setTitle("testTitle");

        //
        //Then
        //
        assertEquals(reminder.getTitle(), "testTitle");

    }


    @Test
    void testSetDescription()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        reminder.setDescription("test description");

        //
        //Then
        //
        assertEquals(reminder.getDescription(), "test description");

    }

    @Test
    void testEqualsToNull()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();

        //
        //When
        //
        Reminder nullReminder = null;


        //
        //Then
        //
        assertFalse(reminder.equals(nullReminder));

    }

    @Test
    void testNotEqual()
    {
        //
        //Given
        //
        Reminder reminder1 = new Reminder();
        Reminder reminder2 = new Reminder();
        //
        //When
        //
        reminder1.setTitle("testName");
        reminder1.setDescription("firstDescription");
        reminder2.setTitle("testName");
        reminder2.setDescription("secondDescription");



        //
        //Then
        //
        assertFalse(reminder1.equals(reminder2));

    }

    @Test
    void testToString()
    {
        //
        //Given
        //
        Reminder reminder = new Reminder();
        //
        //When
        //
        reminder.setTitle("firstReminder");
        reminder.setDescription("this is an important reminder");
        reminder.setDeleted(false);
        //
        //Then
        //
        assertEquals(reminder.toString(), "Reminder{creatorId=null, creatorUsername='null', id=null, dueDateTime=null, title='firstReminder', description='this is an important reminder', deleted=false}");
    }

    @Test
    void testHashCode()
    {
        //
        //Given
        //
        Reminder reminder1 = new Reminder();
        Reminder reminder2 = new Reminder();
        //
        //When
        //

        reminder1.setTitle("testTitle");
        reminder1.setDescription("test description");
        reminder2.setTitle("testTitle");
        reminder2.setDescription("test description");
        //
        //Then
        //
        assertEquals(reminder1.hashCode(), reminder2.hashCode());
    }





}
