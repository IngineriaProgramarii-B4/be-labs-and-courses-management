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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TeacherServiceTest {
    @InjectMocks
    TeacherService teacherService;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private GradeRepository gradeRepository;
    private Teacher teacher;
    @BeforeEach
    public void setup() {
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    public void canGetAllStudents() {
        // when
        teacherService.getTeacherDataBase();

        // then
        verify(teacherRepository).getAllTeachers();
    }

//    @Test
//    public void canGetTeacherById() {
//        /* Testul asta ar trebui sa mearga daca id-urile din BD si ale profesorilor sunt matching. */
//        // given
//
//        Teacher teacher = new Teacher(1, "mihaelescu@gmail.com", "Florin");
//        teacherService.save(teacher);
//
//        Teacher get_result = teacherService.getTeacherById(teacher.getId());
//
//        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
//        verify(teacherRepository).save(teacherArgumentCaptor.capture());
//
//        Teacher captured = teacherArgumentCaptor.getValue();
//
//        assertNotNull(get_result);
//        assertEquals(get_result, captured);
//    }
    @Test
    public void canAddTeacher() {

        Teacher teacher = new Teacher(1, "popescu@gmail.com", "Florin");
        teacherService.save(teacher);

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertEquals(captured, teacher);
    }

    @Test
    public void canDeleteTeacher() {

        Teacher teacher = new Teacher(1, "popescu@gmail.com", "Florin");
        teacherService.save(teacher);
        teacherService.delete(teacher);
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher captured = teacherArgumentCaptor.getValue();

        assertEquals(captured, teacher);
    }

}