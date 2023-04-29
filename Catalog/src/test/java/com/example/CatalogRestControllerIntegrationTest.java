//package com.example;
//
//import com.example.grades.Grade;
//import com.example.subject.Subject;
//import com.example.userImpl.student.Student;
//import com.example.userImpl.student.StudentRepository;
//import com.example.userImpl.teacher.TeacherRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.properties")
//public class CatalogRestControllerIntegrationTest {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private StudentRepository studentRepository;
//    @Autowired
//    private TeacherRepository teacherRepository;
//
//    @Test
//    public void getStudentsTest() throws Exception {
//        mvc.perform(get("/api/v1/catalog/students")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    public void givenStudent_whenPostStudent_thenResponse() throws Exception {
//        postStudentsTest(new Student(301234, "mihaelescu@gmail.com", "Florin"));
//    }
//
////    @Test
////    public void givenGradeAndStudent_whenPostGrade_thenResponse() throws Exception {
////        Subject subject = new Subject("PP");
////        Subject subject1 = new Subject("PZ");
////        Subject subject2 = new Subject("PX");
////        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
////        student.setGrades(null);
////
////        postGradesTest(new Grade(7, subject, "12.02.1996"), student);
////        postGradesTest(new Grade(8, subject1, "12.02.1996"), new Student(301235, "gabiceanu@gmail.com", "Gabi"));
////        postGradesTest(new Grade(9, subject2, "12.02.1996"), new Student(301236, "popica@gmail.com", "Popa"));
////    }
////    @Test
////    public void givenGradeAndStudent_whenPostGrade_thenResponse() throws Exception {
////        Subject subject = new Subject("PP");
////        Subject subject1 = new Subject("PZ");
////        Subject subject2 = new Subject("PX");
////        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
////        student.setGrades(null);
////
////        postGradesTest(new Grade(7, subject, "12.02.1996"), student);
//////        postGradesTest(new Grade(8, subject1, "12.02.1996"), new Student(301235, "gabiceanu@gmail.com", "Gabi"));
//////        postGradesTest(new Grade(9, subject2, "12.02.1996"), new Student(301236, "popica@gmail.com", "Popa"));
////    }
////
////    @Test
////    public void givenId_whenDeleteStudent_thenResponse() throws Exception {
////        Student student = new Student(301234, "mihaelescu@gmail.com", "Florin");
////        deleteStudentTest(student);
////    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void postStudentsTest(Student student) throws Exception {
//        student.setGrades(null);
//
//        MvcResult result = mvc.perform(post("/api/v1/catalog/students")
//                        .content(asJsonString(student))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andReturn();
//    }
//
//    public void postGradesTest(Grade grade, Student student) throws Exception {
//
//        MvcResult studentResult =  mvc.perform(post("/api/v1/catalog/students")
//                        .content(asJsonString(student))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        Student studentResponse = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);
//
//        MvcResult gradeResult = mvc.perform(post("/api/v1/catalog/students/{id}/grades", studentResponse.getId())
//                        .content(asJsonString(grade))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        Grade gradeResponse = objectMapper.readValue(gradeResult.getResponse().getContentAsString(), Grade.class);
//
//        assertNotNull(gradeResponse);
//        assertEquals(gradeResponse.getId(), grade.getId());
//    }
//
//    public void deleteStudentTest(Student student) throws Exception {
//        MvcResult studentResult =  mvc.perform(post("/api/v1/catalog/students")
//                        .content(asJsonString(student))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        Student studentResponse = objectMapper.readValue(studentResult.getResponse().getContentAsString(), Student.class);
//
//        MvcResult deleteResult =  mvc.perform(delete("/api/v1/catalog/students/{id}",studentResponse.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//
//        Student studentDeleted = objectMapper.readValue(deleteResult.getResponse().getContentAsString(), Student.class);
//
//        assertNotNull(studentDeleted);
//    }
//}
