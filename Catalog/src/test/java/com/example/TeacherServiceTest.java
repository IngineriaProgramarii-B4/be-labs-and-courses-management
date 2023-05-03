package com.example;


import com.example.grades.GradeRepository;

import com.example.user_impl.teacher.Teacher;
import com.example.user_impl.teacher.TeacherRepository;
import com.example.user_impl.teacher.TeacherService;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TeacherServiceTest {
    @InjectMocks
    TeacherService teacherService;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private Teacher teacher;
    @BeforeEach
    public void setup() {
        teacherService = new TeacherService(teacherRepository);
        teacher = new Teacher(1, "mihaelescu@gmail.com", "Florin");
    }

    @Test
    void canGetAllTeachers() {
        // when
        teacherService.getTeacherDataBase();

        // then
        verify(teacherRepository).getAllTeachers();
    }

    @Test
    void canGetTeacherById() {
        when(teacherRepository.getTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        assertEquals(Optional.of(teacher), teacherRepository.getTeacherById(teacher.getId()));
        teacherService.save(teacher);

        Teacher get_result = teacherService.getTeacherById(teacher.getId());

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertNotNull(get_result);
        assertEquals(get_result, captured);

        // given invalid teacher id
        try {
            teacherService.getTeacherById(9999);
        }
        catch (IllegalStateException e) {
            System.out.println(e);
        }
    }
    @Test
    void canAddTeacher() {

        when(teacherRepository.getTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        assertEquals(Optional.of(teacher), teacherRepository.getTeacherById(teacher.getId()));
        teacherService.save(teacher);

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertEquals(captured, teacher);
    }

    @Test
    void canDeleteTeacher() {

        when(teacherRepository.getTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        assertEquals(Optional.of(teacher), teacherRepository.getTeacherById(teacher.getId()));

        teacherService.save(teacher);
        teacherService.delete(teacher);
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertEquals(captured, teacher);
    }

}