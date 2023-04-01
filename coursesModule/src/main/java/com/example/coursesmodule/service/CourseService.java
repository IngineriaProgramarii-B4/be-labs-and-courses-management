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
public class CourseService {
    private final CourseDao courseDao;

    @Autowired
    public CourseService(@Qualifier("fakeDao") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int addCourse(Course course) {
        return courseDao.insertCourse(course);
    }
    public List<Course> getAllCourses() {
       return courseDao.selectAllCourses();
    }

    public Optional<Course> getCourseById(int id) {
        return courseDao.selectCourseById(id);
    }

    public void addResource(int id, Resource resource) {
        courseDao.addResource(id, resource);
    }
    public Optional<Resource> getResourceById(Course course, int resourceId) {
        return courseDao.getResourceById(course, resourceId);
    }

    public int verifyApprofundationValid(Approfundation approfundation){
        return approfundation.getNumberWeeks() <= 0 || approfundation.getId() <= 0 ? 0 : 1;
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
}
