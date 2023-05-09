package com.example;

import com.example.grades.Grade;
import com.example.subject.Subject;
import com.example.user_impl.student.Student;
import com.example.user_impl.student.StudentController;
import com.example.user_impl.student.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;
    private Student student;
    private Subject subject;
    private Grade grade;
    List<Student> students = new ArrayList<>();
    List<Grade> grades = new ArrayList<>();
    @BeforeEach
    void setUp(){
        subject = new Subject("Mocked");
        student = new Student(301234, "mocked@gmail.com", "Test");
        grade = new Grade(7, subject, "12.02.1996");

        student.addGrade(grade);

        students.add(student);
        grades.add(grade);
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void getAllStudentsAPI() throws Exception {
        when(studentService.getStudentDataBase()).thenReturn(students);
        MvcResult studentResult = mvc.perform(get("/api/v1/catalog/students")
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
        List<Student> studentResponse = objectMapper.readValue(studentResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(studentResponse);
        assertEquals(students, studentResponse);
    }
    @Test
    void createStudentAPI() throws Exception {
        when(studentService.save(any(Student.class))).thenReturn(student);
        MvcResult studentResult = mvc.perform(post("/api/v1/catalog/students")
                        .content(asJsonString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Student studentCreated = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);
        assertNotNull(studentCreated);
        assertEquals(student,studentCreated);


    }
    @Test
    void getStudentByIdAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        int id = student.getId();
        MvcResult studentResult = mvc.perform(get("/api/v1/catalog/students/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentCreated = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);

        assertNotNull(studentCreated);
        assertEquals(student,studentCreated);

    }

    @Test
    void getStudentByIdGradesOnSubjectAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        MvcResult gradesList = mvc.perform(get("/api/v1/catalog/students/{id}/{subject}",  student.getId(), subject.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Grade> gradesResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(gradesResponse);

        int gradesIds = 0;
        int gradesResponseIds = 0;
        for (Grade grade1 : grades)
            gradesIds += grade1.getId();
        for (Grade grade2 : gradesResponse)
            gradesResponseIds += grade2.getId();

        assertEquals(gradesIds, gradesResponseIds);
    }
    @Test
    void getStudentByIdGradesAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        MvcResult gradesList = mvc.perform(get("/api/v1/catalog/students/{id}/grades",  student.getId(), subject.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Grade> gradesResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(gradesResponse);

        int gradesIds = 0;
        int gradesResponseIds = 0;
        for (Grade grade1 : grades)
            gradesIds += grade1.getId();
        for (Grade grade2 : gradesResponse)
            gradesResponseIds += grade2.getId();

        assertEquals(gradesIds, gradesResponseIds);
    }

    @Test
    void getStudentByIdGetGradeByIdAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        when(studentService.getGradeById(student.getId(), grade.getId())).thenReturn(grade);

        MvcResult gradesList = mvc.perform(get("/api/v1/catalog/students/{id}/grades/{id}",  student.getId(), grade.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Grade gradeResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(),Grade.class);

        assertNotNull(gradeResponse);
        assertEquals(gradeResponse.getId(),grade.getId());
    }

    @Test
    void createGradeAPI() throws Exception {
        //GradeId pus in postGradeResult inainte ca metoda Student.addGrade() sa aloce un ID.
        Grade gradeForPost = new Grade(10, subject, "12.12.1222");
        gradeForPost.setId(1);

        when(studentService.getStudentById(student.getId())).thenReturn(student);
        when(studentService.addGrade(eq(student.getId()), any(Grade.class))).thenAnswer((Answer<Grade>) invocationOnMock -> {
            student.addGrade(gradeForPost);
            return grade;
        });

        MvcResult postGradeResult = mvc.perform(post("/api/v1/catalog/students/{id}/grades", student.getId())
                        .content(asJsonString(gradeForPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Grade gradeResponse = objectMapper.readValue(postGradeResult.getResponse().getContentAsString(), Grade.class);

        assertNotNull(gradeResponse);
        assertEquals(gradeResponse.getId(),gradeForPost.getId());

        // given null grade
        mvc.perform(post("/api/v1/catalog/students/{id}/grades", student.getId())
                        .content(asJsonString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());

        assertEquals(2, student.getGrades().size());
    }

    @Test
    void deleteGradeAPI() throws Exception {

        when(studentService.getStudentById(student.getId())).thenReturn(student);
        when(studentService.deleteGrade(student.getId(), grade.getId())).thenAnswer((Answer<Grade>) invocationOnMock -> {
            grade.setDeleted();
            return grade;
        });

        MvcResult deleteGradeResult = mvc.perform(delete("/api/v1/catalog/students/{id}/grades/{gradeId}", student.getId(), grade.getId())
                        .content(asJsonString(grade))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Grade gradeResponse = objectMapper.readValue(deleteGradeResult.getResponse().getContentAsString(), Grade.class);

        assertNotNull(gradeResponse);
        assertEquals(gradeResponse.getId(), grade.getId());
        assertTrue(grade.isDeleted());
        //assertEquals(student.getGrades().size(), 0);
    }

    @Test
    void deleteStudentAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        when(studentService.delete(student)).thenAnswer((Answer<Student>) invocationOnMock -> {
            students.remove(student);
            return student;
        });

        MvcResult deleteStudentResult = mvc.perform(delete("/api/v1/catalog/students/{id}", student.getId())
                        .content(asJsonString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentDeleted = objectMapper.readValue(deleteStudentResult.getResponse().getContentAsString(), Student.class);

        assertNotNull(studentDeleted);
        assertEquals(student, studentDeleted);
        assertEquals(0, students.size());

    }
    @Test
    void updateStudentAPI() throws Exception {
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        when(studentService.getGradeById(student.getId(), grade.getId())).thenReturn(grade);

        String evalDateTest = "12.12.2002";
        int valueTest = 3;

        when(studentService.updateGrade(anyInt(), any(Integer.class), anyString(), anyInt())).thenAnswer((Answer<Grade>) invocationOnMock -> {
            Grade toUpdate = student.getGradeById(grade.getId());
            toUpdate.setEvaluationDate(evalDateTest);
            toUpdate.setValue(valueTest);
            return toUpdate;
        });

        MvcResult updateGradeResult = mvc.perform(put("/api/v1/catalog/students/{id}/grades/{gradeId}", student.getId(), grade.getId())
                        .param("evaluationDate", asJsonString(evalDateTest))
                        .param("value", asJsonString(valueTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Grade gradeResponse = objectMapper.readValue(updateGradeResult.getResponse().getContentAsString(), Grade.class);

        assertNotNull(gradeResponse);
        assertEquals(gradeResponse.getId(), grade.getId());

        assertEquals("12.12.2002", gradeResponse.getEvaluationDate());
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
