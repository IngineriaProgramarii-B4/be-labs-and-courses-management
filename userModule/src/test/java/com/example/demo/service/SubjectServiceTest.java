package com.example.demo.service;

import com.example.dao.CourseDao;
import com.example.models.Component;
import com.example.models.Evaluation;
import com.example.models.Resource;
import com.example.models.Subject;
import com.example.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    private CourseDao courseDao;

    private MockMvc mockMvc;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        mockMvc= MockMvcBuilders.standaloneSetup(subjectService).build();
    }

    @Test
    void getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Math", 5, 1, 1, "description A", new ArrayList<>(), new ArrayList<>(), false));
        subjects.add(new Subject(2, "English", 4, 1, 1, "description B", new ArrayList<>(), new ArrayList<>(), false));
        when(courseDao.selectAllSubjects()).thenReturn(subjects);

        List<Subject> result = subjectService.getAllSubjects();

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getTitle());
        assertEquals("English", result.get(1).getTitle());
        assertEquals(5, result.get(0).getCredits());
        assertEquals(4, result.get(1).getCredits());
        assertEquals(1, result.get(0).getYear());
        assertEquals(1, result.get(1).getYear());
        assertEquals(1, result.get(0).getSemester());
        assertEquals(1, result.get(1).getSemester());
        assertEquals("description A", result.get(0).getDescription());
        assertEquals("description B", result.get(1).getDescription());
        assertEquals(0, result.get(0).getComponentList().size());
        assertEquals(0, result.get(1).getComponentList().size());
        assertEquals(0, result.get(0).getEvaluationList().size());
        assertEquals(0, result.get(1).getEvaluationList().size());
    }

    @Test
    void getSubjectByTitleExists() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        courseDao.insertSubject(subject);

        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(courseDao.selectAllSubjects()).thenReturn(List.of(subject));

        Optional<Subject> resultSuccess = subjectService.getSubjectByTitle("Algebraic Foundations of Science");

        assertTrue(resultSuccess.isPresent());
        assertEquals("Algebraic Foundations of Science", resultSuccess.get().getTitle());
        assertEquals(5, resultSuccess.get().getCredits());
        assertEquals(1, resultSuccess.get().getYear());
        assertEquals(2, resultSuccess.get().getSemester());
        assertEquals("not gonna pass", resultSuccess.get().getDescription());
        assertEquals(0, resultSuccess.get().getComponentList().size());
        assertEquals(0, resultSuccess.get().getEvaluationList().size());
    }

    @Test
    void getSubjectByTitleEmpty() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        courseDao.insertSubject(subject);

        when(courseDao.selectAllSubjects()).thenReturn(List.of(subject));

        Optional<Subject> resultEmpty = subjectService.getSubjectByTitle("Non-existent Subject");
        assertFalse(resultEmpty.isPresent());
    }

    @Test
    void getSubjectsByYearAndSemester() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(2, "Physics", 5, 1, 2, "description", List.of(new Component(2, "Course", 10, List.of(new Resource("Book.pdf", "savedResources/Book.pdf", "application/pdf", false)), false)),
                List.of(new Evaluation(1L, "Course", 0.5f, "description B", false)), false));
        when(courseDao.getSubjectsByYearAndSemester(1, 2)).thenReturn(subjects);

        List<Subject> result = subjectService.getSubjectsByYearAndSemester(1, 2);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals("Physics", result.get(0).getTitle());
        assertEquals(5, result.get(0).getCredits());
        assertEquals(1, result.get(0).getYear());
        assertEquals(2, result.get(0).getSemester());
        assertEquals("description", result.get(0).getDescription());
        assertEquals(1, result.get(0).getComponentList().size());
        assertEquals(1, result.get(0).getEvaluationList().size());

        verify(courseDao, times(1)).getSubjectsByYearAndSemester(1, 2);
    }

    @Test
    void addSubjectSuccessful() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        when(courseDao.insertSubject(subject)).thenReturn(1);

        assertEquals(1, subjectService.addSubject(subject));
    }

    @Test
    void addSubjectThatAlreadyExists() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        courseDao.insertSubject(subject);
        assertEquals(0, subjectService.addSubject(subject));
    }

    @Test
    void addSubjectWithSameName() {
        Subject subject1 = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        Subject subject2 = new Subject(6, "Algebraic Foundations of Science", 2, 3, 2, "yes gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        courseDao.insertSubject(subject1);

        assertEquals(0, subjectService.addSubject(subject1));
        assertEquals(0, subjectService.addSubject(subject2));
    }

    @Test
    void deleteSubjectByTitleSuccessful() {
        Subject subject = new Subject(4, "Algebraic Foundations of Science", 5, 1, 2, "not gonna pass", new ArrayList<>(), new ArrayList<>(), false);
        courseDao.insertSubject(subject);

        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(courseDao.selectAllSubjects()).thenReturn(List.of(subject));
        when(courseDao.deleteSubjectByTitle("Algebraic Foundations of Science")).thenReturn(1);

        assertEquals(1, subjectService.deleteSubjectByTitle("Algebraic Foundations of Science"));
    }

    @Test
    void deleteSubjectByTitleImageRenamed() {
        Subject subject = new Subject(4, "Maths", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        String absolutePath = new File("").getAbsolutePath();
        String folderPath = absolutePath + "/" + "savedResources/";

        File imageFile = new File(folderPath + "Maths_image.txt");
        File newFile = new File(folderPath + "DELETED_Maths_image.txt");
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Resource image = new Resource("image.txt", folderPath + "Maths_image.txt", "txt", false);
        subject.setImage(image);
        courseDao.insertSubject(subject);

        when(courseDao.selectSubjectByTitle("Maths")).thenReturn(Optional.of(subject));
        when(courseDao.selectAllSubjects()).thenReturn(List.of(subject));
        when(courseDao.deleteSubjectByTitle("Maths")).thenReturn(1);

        int result = subjectService.deleteSubjectByTitle("Maths");
        assertFalse(imageFile.exists());
        assertTrue(newFile.exists());
        assertEquals(1, result);

        if (imageFile.exists())
            imageFile.delete();
        newFile.delete();
    }

    @Test
    void deleteSubjectByTitleFailed() {
        assertEquals(0, subjectService.deleteSubjectByTitle("Non-existent subject"));
    }

    @Test
    void updateSubjectByTitleSuccessful() {
        String title = "Math";
        Subject updatedSubject = new Subject(1, "Math", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle(title)).thenReturn(Optional.of(new Subject()));
        when(courseDao.updateSubjectByTitle(title, updatedSubject)).thenReturn(1);

        assertEquals(1, subjectService.updateSubjectByTitle(title, updatedSubject));
        verify(courseDao, times(1)).selectSubjectByTitle(title);
        verify(courseDao, times(1)).updateSubjectByTitle(title, updatedSubject);
    }

    @Test
    void updateSubjectByTitleInvalidSubject() {
        String title = "Math";
        Subject updatedSubject = new Subject(1, "Math", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle(title)).thenReturn(Optional.of(new Subject()));
        when(courseDao.updateSubjectByTitle(title, updatedSubject)).thenReturn(0);

        assertEquals(0, subjectService.updateSubjectByTitle(title, updatedSubject));
        verify(courseDao, times(1)).selectSubjectByTitle(title);
        verify(courseDao, times(1)).updateSubjectByTitle(title, updatedSubject);
    }

    @Test
    void updateSubjectByTitleNotFound() {
        String title = "Math";
        Subject updatedSubject = new Subject(1, "Math", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle(title)).thenReturn(Optional.empty());
        String message = "";

        try {
            subjectService.updateSubjectByTitle(title, updatedSubject);
        } catch (NoSuchElementException e) {
            message = e.getMessage();
        }

        assertEquals("No value present", message);
    }

    //upload subject image and validate tests remain
}
