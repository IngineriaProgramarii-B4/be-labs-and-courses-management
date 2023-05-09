package com.example.demo.service;

import com.example.dao.CourseDao;
import com.example.models.Component;
import com.example.models.Subject;
import com.example.services.ComponentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComponentServiceTest {

    @Mock
    private CourseDao courseDao;

    private ComponentService componentService;

    @BeforeEach
    void setUp() {
        componentService = new ComponentService(courseDao);
    }

    @Test
    void validateComponentReturnsTrueForValidComponent() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(new Subject()));
        assertTrue(componentService.validateComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void validateComponentReturnsFalseForDeletedComponent(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), true);
        assertFalse(componentService.validateComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void validateComponentReturnsFalseForInvalidType(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        component.setType("Invalid");
        assertFalse(componentService.validateComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void validateComponentReturnsFalseForInvalidNumberWeeks(){
        Component component = new Component(3, "Seminar", 0, new ArrayList<>(), false);
        assertFalse(componentService.validateComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void validateComponentReturnsFalseForSubjectNotFound(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        assertFalse(componentService.validateComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void validateComponentReturnsFalseForDuplicateType(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        assertFalse(componentService.validateComponent("Algebraic Foundations of Science", component));
    }
    @Test
    void validateComponentToUpdateReturnsTrueForValidComponent() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(new Subject()));
        assertTrue(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateComponentToUpdateReturnsFalseForDeletedComponent(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), true);
        assertFalse(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateComponentToUpdateReturnsFalseForInvalidNumberWeeks(){
        Component component = new Component(3, "Seminar", 0, new ArrayList<>(), false);
        assertFalse(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateComponentToUpdateReturnsFalseForInvalidType(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        component.setType("Invalid");
        assertFalse(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateComponentToUpdateReturnsFalseForSubjectNotFound(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        assertFalse(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateComponentToUpdateReturnsFalseForDuplicateType(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        assertFalse(componentService.validateComponentToUpdate("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void validateTypeReturnsTrueForValidType() {
        assertTrue(componentService.validateType("Seminar"));
        assertTrue(componentService.validateType("Laboratory"));
        assertTrue(componentService.validateType("Course"));
    }

    @Test
    void validateTypeReturnsFalseForInvalidType() {
        assertFalse(componentService.validateType("type"));
    }

    @Test
    void addComponentReturnsFalseForInvalidComponent() {
        Component component = new Component(3, "Seminar", 0, new ArrayList<>(), false);
        assertEquals(0, componentService.addComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void addComponentSuccessful(){
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(courseDao.addComponent("Algebraic Foundations of Science", component)).thenReturn(1);
        assertEquals(1, componentService.addComponent("Algebraic Foundations of Science", component));
    }

    @Test
    void getComponents() {
        List<Component> components = List.of(new Component(3, "Seminar", 14, new ArrayList<>(), false),
                new Component(4, "Laboratory", 10, new ArrayList<>(), false));
        Subject subject = new Subject();
        subject.setComponentList(components);
        when(courseDao.getComponents("Algebraic Foundations of Science")).thenReturn(components);
        List<Component> result = componentService.getComponents("Algebraic Foundations of Science");
        assertEquals(components, result);
    }

    @Test
    void getComponentByTypeSuccessful() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        when(courseDao.getComponentByType("Algebraic Foundations of Science", "Seminar")).thenReturn(Optional.of(component));
        Optional<Component> result = componentService.getComponentByType("Algebraic Foundations of Science", "Seminar");
        assertEquals(Optional.of(component), result);
    }

    @Test
    void getComponentByTypeReturnsEmptyOptionalForInvalidType() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        Optional<Component> result = componentService.getComponentByType("Algebraic Foundations of Science", "Invalid");
        assertEquals(Optional.empty(), result);
    }

    @Test
    void deleteComponentByTypeSuccessful() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(courseDao.deleteComponentByType("Algebraic Foundations of Science", "Seminar")).thenReturn(1);
        assertEquals(1, componentService.deleteComponentByType("Algebraic Foundations of Science", "Seminar"));
    }

    @Test
    void deleteComponentByTypeReturnsZeroForInvalidComponentType() {
        Component component = new Component(3, "Invalid", 0, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        assertEquals(0, componentService.deleteComponentByType("Algebraic Foundations of Science", "Seminar"));
    }

    @Test
    void deleteComponentByTypeReturnsZeroForInvalidSubject() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.empty());
        assertEquals(0, componentService.deleteComponentByType("Algebraic Foundations of Science", "Seminar"));
    }

    @Test
    void updateComponentByTypeSuccessful() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        when(courseDao.selectSubjectByTitle("Algebraic Foundations of Science")).thenReturn(Optional.of(subject));
        when(courseDao.updateComponentByType("Algebraic Foundations of Science", "Seminar", component)).thenReturn(1);
        assertEquals(1, componentService.updateComponentByType("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void updateComponentByTypeReturnsZeroForInvalidSubject() {
        Component component = new Component(3, "Seminar", 14, new ArrayList<>(), false);
        assertEquals(0, componentService.updateComponentByType("Algebraic Foundations of Science", "Seminar", component));
    }

    @Test
    void updateComponentByTypeReturnsZeroForInvalidComponent() {
        Component component = new Component(3, "Invalid", 0, new ArrayList<>(), false);
        Subject subject = new Subject();
        subject.setComponentList(List.of(component));
        assertEquals(0, componentService.updateComponentByType("Algebraic Foundations of Science", "Seminar", component));
    }
}
