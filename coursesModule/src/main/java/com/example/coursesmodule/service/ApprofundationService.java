package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprofundationService {

    private final CourseDao courseDao;

    @Autowired
    public ApprofundationService(@Qualifier("fakeDao") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyApprofundationValid(Approfundation approfundation){
        return approfundation.getNumberWeeks() <= 0 || approfundation.getId() <= 0 ? 0 : 1;
    }
    public Optional<Course> getCourseById(int id) {
        return courseDao.selectCourseById(id);
    }

    public int addApprofundation(int id, Approfundation approfundation) {
        return verifyApprofundationValid(approfundation) == 0 ? 0 : courseDao.addApprofundation(id, approfundation);
    }

    public List<Approfundation> getApprofundations(Course course) {
        return courseDao.getApprofundations(course);
    }

    public Optional<Approfundation> getApprofundationById(Course course, int approfundationId) {
        return courseDao.getApprofundationById(course, approfundationId);
    }

    public int deleteApprofundationById(int id, int approfundationId) {
        return courseDao.deleteApprofundationById(id, approfundationId);
    }

    public int updateApprofundationById(int id, int approfundationId, Approfundation approfundation) {
        return verifyApprofundationValid(approfundation) == 0 ? 0 : courseDao.updateApprofundationById(id, approfundationId, approfundation);
    }

    public List<Resource> getResourcesForAprofundationId(int id, int approfundationId) {
        return courseDao.getResourcesForApprofundationId(id, approfundationId);
    }

    public Optional<Resource> getResourceByIdForApprofundationId(int id, int approfundationId , int resourceId) {
        return courseDao.getResourceByIdForApprofundationId(id, approfundationId, resourceId);
    }

    public int addResourceForApprofundationId(int id, int approfundationId, Resource resource) {
        return courseDao.addResourceForApprofundationId(id, approfundationId, resource);
    }

    public int updateResourceForApprofundationId(int id, int approfundationId, Resource resource) {
        return courseDao.updateResourceForApprofundationId(id, approfundationId, resource);
    }

    public int deleteResourceForApprofundationId(int id, int approfundationId, int resourceId) {
        return courseDao.deleteResourceForApprofundationId(id, approfundationId, resourceId);
    }

}
