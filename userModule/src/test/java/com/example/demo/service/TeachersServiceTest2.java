package com.example.demo.service;

import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
import com.example.services.TeachersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeachersServiceTest2 {
    @InjectMocks
    TeachersService teachersService;
    @Mock
    private TeachersRepository teachersRepository;
    @Mock
    private Teacher teacher;
    @BeforeEach
    public void setup() {
        Teacher teacher1 = new Teacher(
                "Ciobaca",
                "Stefan",
                "stefan.ciobaca@uaic.com",
                "stefan.ciobaca",
                "C401",
                new HashSet<>(Arrays.asList("PA", "PF")),
                "Prof");
    }

    @Test
    void canGetAllTeachers() {
        // when
        teachersService.getTeachersByParams(Map.of());

        // then
        verify(teachersRepository).findTeachersByParams(null, null, null, null, null, null, null);
    }

    @Test
    void canGetTeacherById() {
        when(teachersRepository.findById(teacher.getId().toString())).thenReturn(Optional.of(teacher));
        assertEquals(Optional.of(teacher), teachersRepository.findById(teacher.getId().toString()));
        teachersService.saveTeacher(teacher);

        Teacher get_result = teachersService.getTeachersByParams(Map.of("id", teacher.getId().toString())).get(0);

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teachersRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertNotNull(get_result);
        assertEquals(get_result, captured);

        // given invalid teacher id
        try {
            teachersService.getTeachersByParams(Map.of("id", UUID.randomUUID()));
        }
        catch (IllegalStateException e) {
            System.out.println(e);
        }
    }
    @Test
    void canAddTeacher() {

        when(teachersRepository.findById(teacher.getId().toString())).thenReturn(Optional.of(teacher));
        assertEquals(Optional.of(teacher), teachersRepository.findById(teacher.getId().toString()));
        teachersService.saveTeacher(teacher);

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teachersRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertEquals(captured, teacher);
    }

//    @Test
//    void canDeleteTeacher() {
//
//        when(teachersRepository.findById(teacher.getId().toString())).thenReturn(Optional.of(teacher));
//        assertEquals(Optional.of(teacher), teachersRepository.findById(teacher.getId().toString()));
//
//        teachersService.saveTeacher(teacher);
//        teachersService.delete(teacher);
//        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
//        verify(teacherRepository).save(teacherArgumentCaptor.capture());
//
//        Teacher captured = teacherArgumentCaptor.getValue();
//
//        assertEquals(captured, teacher);
//    }

}
