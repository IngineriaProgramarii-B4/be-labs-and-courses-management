package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {
    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private ResourceService resourceService;

    @Test
    void validateExistingResourceTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    void validateExistingResourceTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    void validateExistingResourceTestNotFound() {
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    void validateExistingResourcesTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(component.getResources());
        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertTrue(result);
    }

    @Test
    void validateNewResourceTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    void validateNewResourceTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpeg", false);

        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    void validateNewResourceTestDuplicateTitle() {
        Resource resource1 = new Resource("Resource1", "RESOURCE_PATH/Resource", "image/jpeg", false);
        Resource resource2 = new Resource("Resource1", "RESOURCE_PATH/Resource2", "image/jpeg", false);

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(List.of(resource1));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource2);
        assertFalse(result);
    }

    @Test
    void validateNewResourceTestDuplicateLocation() {
        Resource resource1 = new Resource("Resource1", "RESOURCE_PATH/Resource1", "image/jpeg", false);
        Resource resource2 = new Resource("Resource2", "RESOURCE_PATH/Resource1", "image/jpeg", false);

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(List.of(resource1));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource2);
        assertFalse(result);
    }

    @Test
    void validateNewResourceTestTypeNotFound() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        boolean result = resourceService.validateNewResource("Maths", "Laboratory", resource);
        assertFalse(result);
    }

    @Test
    void validateNewResourceTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertTrue(result);
    }

    @Test
    void addResourceTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Subject subject = new Subject("Maths", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        String absolutePath = new File("").getAbsolutePath();
        String folderPath = absolutePath + "/" + "savedResources/";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File resourceFile = new File(folderPath + "Maths_Course_image.jpg");

        Resource resource = new Resource(
                        "image.jpg",
                        resourceFile.getParentFile().getAbsolutePath() + "/Maths_Course_image.jpg",
                        "image/jpeg",
                        false
        );
        MultipartFile image = new MockMultipartFile(
                "image.jpg",
                "image.jpg",
                "image/jpeg",
                new byte[0]
        );

        component.addResource(resource);;
        subject.addComponent(component);
        courseDao.insertSubject(subject);
        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        when(courseDao.addResourceForComponentType(any(String.class), any(String.class), any(Resource.class))).thenReturn(1);
        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(new ArrayList<>());
        int result = resourceService.addResource(image, "Maths", "Course");
        assertTrue(resourceFile.exists());
        assertEquals(1, result);

        if (resourceFile.exists())
            resourceFile.delete();
        File[] filesInSavedResources = folder.listFiles();
        if (filesInSavedResources == null || filesInSavedResources.length == 0)
            folder.delete();
    }

    @Test
    void getResourcesTest() {
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource("Resource1", "RESOURCE_PATH/Resource1", "image/jpg", false));
        resources.add(new Resource("Resource2", "RESOURCE_PATH/Resource2", "image/jpg", false));

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(resources);
        List<Resource> result = resourceService.getResources("Maths", "Course");
        assertEquals(result, resources);
    }

    @Test
    void getResourcesByTitleTestSuccessful() {
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(resource));
        Optional<Resource> result = resourceService.getResourceByTitle("Maths", "Course", "Resource");
        assertTrue(result.isPresent());
        assertEquals("Resource", result.get().getTitle());
        assertEquals("RESOURCE_PATH/Resource", result.get().getLocation());
        assertEquals("image/jpg", result.get().getType());
    }

    @Test
    void getResourcesByTitleTestNotFound() {
        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Non-existent Resource")).thenReturn(Optional.empty());
        Optional<Resource> result = resourceService.getResourceByTitle("Maths", "Course", "Non-existent Resource");
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteResourceByTitleTestNotFound() {
        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Non-existent Resource")).thenReturn(Optional.empty());
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Non-existent Resource");
        assertEquals(0, result);
    }

    @Test
    void deleteResourceByTitleTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "")).thenReturn(Optional.of(resource));
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "");
        assertEquals(0, result);
    }

    @Test
    void deleteResourceByTitleTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(resource));
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Resource");
        assertEquals(0, result);
    }

    @Test
     void deleteResourceByTitleTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpg", false);
        component.addResource(resource);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(resource));
        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        when(courseDao.deleteResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(1);
        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(component.getResources());
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Resource");
        assertEquals(1, result);
    }

    @Test
    void deleteResourceByTitleResourceRenamed() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Subject subject = new Subject("Maths", 5, 1, 2, "description", new ArrayList<>(), new ArrayList<>(), false);
        String absolutePath = new File("").getAbsolutePath();
        String folderPath = absolutePath + "/" + "savedResources/";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File resourceFile = new File(folderPath + "Maths_Course_image.jpg");
        File updatedResourceFile = new File(folderPath + "DELETED_Maths_Course_image.jpg");
        try {
            resourceFile.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        component.addResource(new Resource(
                "image.jpg",
                resourceFile.getParentFile().getAbsolutePath() + "/Maths_Course_image.jpg",
                "image/jpeg",
                false
        ));
        subject.addComponent(component);
        courseDao.insertSubject(subject);
        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(component.getResources().get(0)));
        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        when(courseDao.deleteResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(1);
        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(component.getResources());
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Resource");
        assertFalse(resourceFile.exists());
        assertTrue(updatedResourceFile.exists());
        assertEquals(1, result);

        if (resourceFile.exists())
            resourceFile.delete();
        updatedResourceFile.delete();
        File[] filesInSavedResources = folder.listFiles();
        if (filesInSavedResources == null || filesInSavedResources.length == 0)
            folder.delete();
    }
}
