package com.example.demo.controller;

import com.example.controllers.ResourceController;
import com.example.models.Component;
import com.example.models.Evaluation;
import com.example.models.Resource;
import com.example.models.Subject;
import com.example.services.ComponentService;
import com.example.services.ResourceService;
import com.example.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceControllerTest {

    @Mock
    private SubjectService subjectService;

    @Mock
    private ResourceService resourceService;

    @Mock
    private ComponentService componentService;

    private MockMvc mockMvc;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(resourceController).build();
    }

    @Test
    void getResources() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        Resource resource = subject.getComponentList().get(1).getResources().get(0);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.getResources("Algebraic Foundations of Science", "Seminar")).thenReturn(subject.getComponentList().get(1).getResources());
        List<Resource> result = resourceController.getResources("Algebraic Foundations of Science", "Seminar");
        assertEquals(1, result.size());
        assertEquals(resource.getTitle(), result.get(0).getTitle());
        assertEquals(resource.getLocation(), result.get(0).getLocation());
        assertEquals(resource.getType(), result.get(0).getType());
        assertEquals(resource.isDeleted(), result.get(0).isDeleted());
    }

    @Test
    void getResourcesSubjectNotFound(){
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResources("Algebraic Foundations of Science", "Seminar"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void getResourcesComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Course")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResources("Algebraic Foundations of Science", "Course"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void getResourceByTitle() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        Resource resource = subject.getComponentList().get(1).getResources().get(0);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.getResourceByTitle(subject.getTitle(), "Seminar", "Book")).thenReturn(Optional.of(resource));
        Resource result = resourceController.getResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book");
        assertEquals(resource.getTitle(), result.getTitle());
        assertEquals(resource.getLocation(), result.getLocation());
        assertEquals(resource.getType(), result.getType());
        assertEquals(resource.isDeleted(), result.isDeleted());

    }

    @Test
    void getResourceByTitleSubjectNotFound(){
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void getResourceByTitleComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Course")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceByTitle("Algebraic Foundations of Science", "Course", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void getResourceByTitleResourceNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.getResourceByTitle(subject.getTitle(), "Seminar", "Book")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resource not found", exception.getReason());
    }

    @Test
    void addResourceFile() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        MultipartFile file = new MockMultipartFile("Physics_romania.png", "Physics_romania.png", "image/png", "Physics_romania.png".getBytes());

        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.addResource(file, subject.getTitle(), "Seminar")).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.addResourceFile("Algebraic Foundations of Science", "Seminar", file));
        assertEquals(HttpStatus.CREATED, exception.getStatusCode());
        assertEquals("Resource added successfully", exception.getReason());
    }

    @Test
    void addResourceFileSubjectNotFound(){
        MultipartFile file = new MockMultipartFile("Physics_romania.png", "Physics_romania.png", "image/png", "Physics_romania.png".getBytes());
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.addResourceFile("Algebraic Foundations of Science", "Seminar", file));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void addResourceFileComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        MultipartFile file = new MockMultipartFile("Physics_romania.png", "Physics_romania.png", "image/png", "Physics_romania.png".getBytes());
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.addResourceFile("Algebraic Foundations of Science", "Seminar", file));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void addResourceFileResourceNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        MultipartFile file = new MockMultipartFile("Physics_romania.png", "Physics_romania.png", "image/png", "Physics_romania.png".getBytes());
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.addResource(file, subject.getTitle(), "Seminar")).thenReturn(0);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.addResourceFile("Algebraic Foundations of Science", "Seminar", file));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resource not found", exception.getReason());
    }

    @Test
    void getResourceFile() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Laboratory")).thenReturn(Optional.of(new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)));
        Resource resource = new Resource("Book", "savedResources/Physics_romania.png", "image/png", false);
        when(resourceService.getResourceByTitle(subject.getTitle(), "Laboratory", "Book")).thenReturn(Optional.of(resource));

        ResponseEntity<byte[]> result = resourceController.getResourceFile("Algebraic Foundations of Science", "Laboratory", "Book");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("image/png", Objects.requireNonNull(result.getHeaders().getContentType()).toString());
    }

    /*@Test
    void getResourceFileFileReadError() throws Exception {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Laboratory")).thenReturn(Optional.of(new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)));
        Resource resource = new Resource("Book", "savedResources/Physics_romania.png", "image/png", false);
        when(resourceService.getResourceByTitle(subject.getTitle(), "Laboratory", "Book")).thenReturn(Optional.of(resource));
        when(Files.readAllBytes(any(Path.class))).thenThrow(new IOException("Failed to read file"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceFile("Algebraic Foundations of Science", "Laboratory", "Book"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }*/

    @Test
    void getResourceFileSubjectNotFound(){
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceFile("Algebraic Foundations of Science", "Laboratory", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void getResourceFileComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Laboratory")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceFile("Algebraic Foundations of Science", "Laboratory", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void getResourceFileEmptyResource(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)));
        when(resourceService.getResourceByTitle(subject.getTitle(), "Seminar", "Book")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.getResourceFile("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resource not found", exception.getReason());
    }

    @Test
    void deleteResourceByTitle() {
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.deleteResourceByTitle(subject.getTitle(), "Seminar", "Book")).thenReturn(1);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.deleteResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        assertEquals("Resource deleted successfully", exception.getReason());
    }

    @Test
    void deleteResourceByTitleSubjectNotFound(){
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.deleteResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Subject not found", exception.getReason());
    }

    @Test
    void deleteResourceByTitleComponentNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Course")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.deleteResourceByTitle("Algebraic Foundations of Science", "Course", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Component not found", exception.getReason());
    }

    @Test
    void deleteResourceByTitleResourceNotFound(){
        Subject subject = new Subject(3, "Algebraic Foundations of Science", 6, 1, 2, "not gonna pass",
                List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                        new Component(4, "Laboratory", 14, List.of(new Resource("Book", "savedResources/Physics_romania.png", "image/png", false)), false)),
                List.of(new Evaluation(3L, "Seminar", 0.5F, "Test", false),
                        new Evaluation(4L, "Laboratory", 0.5F, "Test", false))
                , false);
        when(subjectService.getSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(componentService.getComponentByType(subject.getTitle(), "Seminar")).thenReturn(Optional.of(new Component(3, "Seminar", 14, new ArrayList<>(), false)));
        when(resourceService.deleteResourceByTitle(subject.getTitle(), "Seminar", "Book")).thenReturn(0);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> resourceController.deleteResourceByTitle("Algebraic Foundations of Science", "Seminar", "Book"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resource not found", exception.getReason());
    }
}