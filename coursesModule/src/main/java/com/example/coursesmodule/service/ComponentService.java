package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComponentService {
    private final CourseDao courseDao;

    @Autowired
    public ComponentService(@Qualifier("postgres") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyComponent(Component component) {
        return component.getNumberWeeks() <= 0 ? 0 : 1;
    }

    public int addComponent(String title, Component component) {
        return verifyComponent(component) == 0 ? 0 : courseDao.addComponent(title, component);
    }

    public List<Component> getComponents(String title) {
        return courseDao.getComponents(title);
    }

    public Optional<Component> getComponentByType(String title, String type) {
        return courseDao.getComponentByType(title, type);
    }

    public int deleteComponentByType(String title, String type) {
        return courseDao.deleteComponentByType(title, type);
    }

    public int updateComponentByType(String title, String type, Component component) {
        return verifyComponent(component) == 0 ? 0 : courseDao.updateComponentByType(title, type, component);
    }
}
