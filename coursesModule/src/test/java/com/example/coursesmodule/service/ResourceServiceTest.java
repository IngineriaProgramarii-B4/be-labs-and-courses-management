package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {
    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private ResourceService resourceService;

    @Test
    public void validateExistingResourceTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    public void validateExistingResourceTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    public void validateExistingResourceTestNotFound() {
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    public void validateExistingResourcesTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(component.getResources());
        boolean result = resourceService.validateExistingResource("Maths", "Course", resource);
        assertTrue(result);
    }

    @Test
    public void validateNewResourceTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/Resource", "image/jpeg", false);

        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    public void validateNewResourceTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpeg", false);

        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertFalse(result);
    }

    @Test
    public void validateNewResourceTestDuplicateTitle() {
        Resource resource1 = new Resource("Resource1", "RESOURCE_PATH/Resource", "image/jpeg", false);
        Resource resource2 = new Resource("Resource1", "RESOURCE_PATH/Resource2", "image/jpeg", false);

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(List.of(resource1));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource2);
        assertFalse(result);
    }

    @Test
    public void validateNewResourceTestDuplicateLocation() {
        Resource resource1 = new Resource("Resource1", "RESOURCE_PATH/Resource1", "image/jpeg", false);
        Resource resource2 = new Resource("Resource2", "RESOURCE_PATH/Resource1", "image/jpeg", false);

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(List.of(resource1));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource2);
        assertFalse(result);
    }

    @Test
    public void validateNewResourceTestTypeNotFound() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        boolean result = resourceService.validateNewResource("Maths", "Laboratory", resource);
        assertFalse(result);
    }

    @Test
    public void validateNewResourceTestSuccessful() {
        Component component = new Component("Course", 14, new ArrayList<>(), false);
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpeg", false);
        component.addResource(resource);

        when(courseDao.getComponents("Maths")).thenReturn(List.of(component));
        boolean result = resourceService.validateNewResource("Maths", "Course", resource);
        assertTrue(result);
    }

    @Test
    public void getResourcesTest() {
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource("Resource1", "RESOURCE_PATH/Resource1", "image/jpg", false));
        resources.add(new Resource("Resource2", "RESOURCE_PATH/Resource2", "image/jpg", false));

        when(courseDao.getResourcesForComponentType("Maths", "Course")).thenReturn(resources);
        List<Resource> result = resourceService.getResources("Maths", "Course");
        assertEquals(result, resources);
    }

    @Test
    public void getResourcesByTitleTestSuccessful() {
        Resource resource = new Resource("Resource", "RESOURCE_PATH/Resource", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(resource));
        Optional<Resource> result = resourceService.getResourceByTitle("Maths", "Course", "Resource");
        assertTrue(result.isPresent());
        assertEquals("Resource", result.get().getTitle());
        assertEquals("RESOURCE_PATH/Resource", result.get().getLocation());
        assertEquals("image/jpg", result.get().getType());
    }

    @Test
    public void getResourcesByTitleTestNotFound() {
        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Non-existent Resource")).thenReturn(Optional.empty());
        Optional<Resource> result = resourceService.getResourceByTitle("Maths", "Course", "Non-existent Resource");
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteResourceByTitleTestNotFound() {
        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Non-existent Resource")).thenReturn(Optional.empty());
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Non-existent Resource");
        assertEquals(0, result);
    }

    @Test
    public void deleteResourceByTitleTestEmptyTitle() {
        Resource resource = new Resource("", "RESOURCE_PATH/", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "")).thenReturn(Optional.of(resource));
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "");
        assertEquals(0, result);
    }

    @Test
    public void deleteResourceByTitleTestEmptyLocation() {
        Resource resource = new Resource("Resource", "", "image/jpg", false);

        when(courseDao.getResourceByTitleForComponentType("Maths", "Course", "Resource")).thenReturn(Optional.of(resource));
        int result = resourceService.deleteResourceByTitle("Maths", "Course", "Resource");
        assertEquals(0, result);
    }

    @Test
    public void deleteResourceByTitleTestSuccessful() {
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
}
