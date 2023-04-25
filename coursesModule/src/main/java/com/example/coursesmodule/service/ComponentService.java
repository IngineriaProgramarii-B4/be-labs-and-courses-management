package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ComponentService {
    private final CourseDao courseDao;

    @Autowired
    public ComponentService(@Qualifier("postgres") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public boolean validateComponent(String title, Component component) {
        if(component.getNumberWeeks() <= 0) return false;
        if(!Objects.equals(component.getType(), "Course") || !Objects.equals(component.getType(), "Seminar") || !Objects.equals(component.getType(), "Laboratory"))
            return false;
        Optional<Subject> subject = courseDao.selectSubjectByTitle(title);
        if(subject.isEmpty()) return false;
        for(Component comp : subject.get().getComponentList())
            if(comp.getType().equals(component.getType()))
                return false;
        return true;
    }

    public boolean validateUpdate(String title, String type, Component component) {
        if(component.getNumberWeeks() <= 0) return false;
        if(!Objects.equals(component.getType(), type))
            return false;
        Optional<Subject> subject = courseDao.selectSubjectByTitle(title);
        if(subject.isEmpty()) return false;
        for(Component comp : subject.get().getComponentList())
            if(comp.getType().equals(type))
                return true;
        return false;
    }
    public boolean validateType(String type){
        return Objects.equals(type, "Course") || Objects.equals(type, "Seminar") || Objects.equals(type, "Laboratory");
    }

    public int addComponent(String title, Component component) {
        if(validateComponent(title, component))
            courseDao.addComponent(title, component);
        return 0;
    }

    public List<Component> getComponents(String title) {
        return courseDao.getComponents(title);
    }

    public Optional<Component> getComponentByType(String title, String type) {
        if(validateType(type))
            return courseDao.getComponentByType(title, type);
        return Optional.empty();
    }

    public int deleteComponentByType(String title, String type) {
        if(validateType(type))
            return courseDao.deleteComponentByType(title, type);
        return 0;
    }

    public int updateComponentByType(String title, String type, Component component) {
        return !validateUpdate(title, type, component) ? 0 : courseDao.updateComponentByType(title, type, component);
    }
}
