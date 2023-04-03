package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseResourceService {

    private final CourseDao courseDao;

    @Autowired
    public CourseResourceService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyResourceValid(Resource resource){
        return resource.getId() <= 0 || resource.getTitle().isEmpty() ? 0 : 1;
    }

    public int addCourseResource(int subjectId, Resource resource) {
        if(verifyResourceValid(resource) == 0)
            return 0;
        courseDao.addCourseResource(subjectId, resource);
        return 1;
    }

    public List<Resource> getCourseResources(int subjectId) {
        return courseDao.getCourseResources(subjectId);
    }

    public Optional<Resource> getCourseResourceById(int subjectId, int resourceId) {
        return courseDao.getCourseResourceById(subjectId, resourceId);
    }

    public int deleteCourseResourceById(int subjectId, int resourceId) {
        return courseDao.deleteCourseResourceById(subjectId, resourceId);
    }

    public int updateCourseResourceById(int subjectId, int resourceId, Resource resource) {
        return verifyResourceValid(resource) == 0 ? 0 : courseDao.updateCourseResourceById(subjectId, resourceId, resource);
    }
}
