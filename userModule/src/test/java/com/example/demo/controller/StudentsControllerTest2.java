//package com.example.demo.controller;
//
//import com.example.controllers.StudentsController;
//import com.example.models.Grade;
//import com.example.models.Student;
//import com.example.models.Subject;
//import com.example.services.StudentsService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(StudentsController.class)
//class StudentsControllerTest2 {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private StudentsService studentsService;
//    private Student student;
//    private Subject subject;
//    private Grade grade;
//    List<Student> students = new ArrayList<>();
//    List<Grade> grades = new ArrayList<>();
//    @BeforeEach
//    void setUp(){
//        subject = new Subject("Mocked");
//        student = new Student(
//                UUID.randomUUID(),
//                "Florin",
//                "Rotaru",
//                "florin.eugen@uaic.ro",
//                "florin02",
//                2,
//                4,
//                "123FAKE92929",
//                new HashSet<>(Arrays.asList("IP", "PA", "SGBD", "TW", "SE")));
//        grade = new Grade( 7, subject, "12.02.1996");
//
//        student.addGrade(grade);
//        students.add(student);
//        grades.add(grade);
//    }
//    @AfterEach
//    void tearDown() {
//    }
//    @Test
//    void getAllStudentsAPI() throws Exception {
//            when(studentsService.getStudentsByParams(Map.of())).thenReturn(students);
//        MvcResult studentResult = mvc.perform(get("/api/v1/students")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//        List<Student> studentResponse = objectMapper.readValue(studentResult.getResponse().getContentAsString(), new TypeReference<>() {});
//
//        assertNotNull(studentResponse);
//        assertEquals(students, studentResponse);
//    }
//    @Test
//    void createStudentAPI() throws Exception {
//        when(studentsService.saveStudent(any(Student.class))).thenReturn(student);
//        MvcResult studentResult = mvc.perform(post("/api/v1/students")
//                        .content(asJsonString(student))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        Student studentCreated = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);
//        assertNotNull(studentCreated);
//        assertEquals(student,studentCreated);
//
//
//    }
//    @Test
//    void getStudentByIdAPI() throws Exception {
//        UUID id = student.getId();
//        when(studentsService.getStudentById(id)).thenReturn(student);
//        MvcResult studentResult = mvc.perform(get("/api/v1/students/{id}", id.toString())).andExpect(status().isOk()).andReturn();
//        Student studentCreated = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);
//        assertNotNull(studentCreated);
//        assertEquals(student,studentCreated);
//    }
//
//    /* In StudentsController la ce ati mutat voi nu exista acest get, am vazut ca e doar in Catalog */
//
//    @Test
//    void getStudentByIdGradesOnSubjectAPI() throws Exception {
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        MvcResult gradesList = mvc.perform(get("/api/v1/students/{id}/{subject}",  student.getId().toString(), subject.getName()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        List<Grade> gradesResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(), new TypeReference<>() {});
//        assertNotNull(gradesResponse);
//
//        int gradesIds = 0;
//        int gradesResponseIds = 0;
//        for (Grade grade1 : grades)
//            gradesIds += grade1.getId();
//        for (Grade grade2 : gradesResponse)
//            gradesResponseIds += grade2.getId();
//
//        assertEquals(gradesIds, gradesResponseIds);
//    }
//    @Test
//    void getStudentByIdGradesAPI() throws Exception {
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        MvcResult gradesList = mvc.perform(get("/api/v1/students/{id}/grades",  student.getId(), subject.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//        List<Grade> gradesResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(), new TypeReference<>() {});
//
//        assertNotNull(gradesResponse);
//
//        int gradesIds = 0;
//        int gradesResponseIds = 0;
//        for (Grade grade1 : grades)
//            gradesIds += grade1.getId();
//        for (Grade grade2 : gradesResponse)
//            gradesResponseIds += grade2.getId();
//
//        assertEquals(gradesIds, gradesResponseIds);
//    }
//
//    /* Nici acest GET nu exista, exista doar put / delete cu acest endpoint */
//    @Test
//    void getStudentByIdGetGradeByIdAPI() throws Exception {
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        when(studentsService.getGradeById(student.getId(), grade.getId())).thenReturn(grade);
//
//        MvcResult gradesList = mvc.perform(get("/api/v1/students/{id}/grades/{gradeId}",  student.getId().toString(), String.format("%d", grade.getId())))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Grade gradeResponse = objectMapper.readValue(gradesList.getResponse().getContentAsString(),Grade.class);
//
//        assertNotNull(gradeResponse);
//        assertEquals(gradeResponse.getId(),grade.getId());
//    }
//
//    @Test
//    void createGradeAPI() throws Exception {
//        //GradeId pus in postGradeResult inainte ca metoda Student.addGrade() sa aloce un ID.
//        Grade gradeForPost = new Grade(10, subject, "12.12.1222");
//        gradeForPost.setId(1);
//
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        when(studentsService.addGrade(eq(student.getId()), any(Grade.class))).thenAnswer((Answer<Grade>) invocationOnMock -> {
//            student.addGrade(gradeForPost);
//            return grade;
//        });
//
//        MvcResult postGradeResult = mvc.perform(post("/api/v1/students/{id}/grades", student.getId())
//                        .content(asJsonString(gradeForPost))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        Grade gradeResponse = objectMapper.readValue(postGradeResult.getResponse().getContentAsString(), Grade.class);
//
//        assertNotNull(gradeResponse);
//        assertEquals(gradeResponse.getId(),gradeForPost.getId());
//
//        // given null grade
//        mvc.perform(post("/api/v1/students/{id}/grades", student.getId())
//                        .content(asJsonString(null))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                // then
//                .andExpect(status().isBadRequest());
//
//        assertEquals(2, student.getGrades().size());
//    }
//
//    @Test
//    void deleteGradeAPI() throws Exception {
//
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        when(studentsService.deleteGrade(student.getId(), grade.getId())).thenAnswer((Answer<Grade>) invocationOnMock -> {
//            grade.setDeleted();
//            return grade;
//        });
//
//        MvcResult deleteGradeResult = mvc.perform(delete("/api/v1/students/{id}/grades/{gradeId}", student.getId(), grade.getId())
//                        .content(asJsonString(grade))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Grade gradeResponse = objectMapper.readValue(deleteGradeResult.getResponse().getContentAsString(), Grade.class);
//
//        assertNotNull(gradeResponse);
//        assertEquals(gradeResponse.getId(), grade.getId());
//        assertTrue(grade.isDeleted());
//        //assertEquals(student.getGrades().size(), 0);
//    }
//
////    @Test
////    void deleteStudentAPI() throws Exception {
////        when(studentsService.getStudentById(student.getId())).thenReturn(student);
////        when(studentsService.delete(student)).thenAnswer((Answer<Student>) invocationOnMock -> {
////            students.remove(student);
////            return student;
////        });
////
////        MvcResult deleteStudentResult = mvc.perform(delete("/api/v1/catalog/students/{id}", student.getId())
////                        .content(asJsonString(student))
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andReturn();
////
////        Student studentDeleted = objectMapper.readValue(deleteStudentResult.getResponse().getContentAsString(), Student.class);
////
////        assertNotNull(studentDeleted);
////        assertEquals(student, studentDeleted);
////        assertEquals(0, students.size());
////
////    }
//
//    /* Aici am vorbit cu tudor ca va trebui schimbata modul de a afisa data si vedem dupa aceea */
//    @Test
//    void updateStudentAPI() throws Exception {
//        when(studentsService.getStudentById(student.getId())).thenReturn(student);
//        when(studentsService.getGradeById(student.getId(), grade.getId())).thenReturn(grade);
//
//        String evalDateTest = "12.12.2002";
//        int valueTest = 3;
//
//        when(studentsService.updateGrade(UUID.randomUUID(), any(Integer.class), anyString(), anyInt())).thenAnswer((Answer<Grade>) invocationOnMock -> {
//            Grade toUpdate = student.getGradeById(grade.getId());
//            toUpdate.setEvaluationDate(evalDateTest);
//            toUpdate.setValue(valueTest);
//            return toUpdate;
//        });
//
//        MvcResult updateGradeResult = mvc.perform(put("/api/v1/students/{id}/grades/{gradeId}", student.getId(), grade.getId())
//                        .param("evaluationDate", asJsonString(evalDateTest))
//                        .param("value", asJsonString(valueTest))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Grade gradeResponse = objectMapper.readValue(updateGradeResult.getResponse().getContentAsString(), Grade.class);
//
//        assertNotNull(gradeResponse);
//        assertEquals(gradeResponse.getId(), grade.getId());
//
//        assertEquals("12.12.2002", gradeResponse.getEvaluationDate());
//    }
//    private static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
