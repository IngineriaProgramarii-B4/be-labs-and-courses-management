package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
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



    public int addCourse(int subjectId, Course course) {
        return courseDao.addCourse(subjectId, course);
    }

    public Course getCourse(int subjectId) {
        return courseDao.getCourse(subjectId);
    }

    public int deleteCourse(int subjectId) {
        return courseDao.deleteCourse(subjectId);
    }

    public int updateCourse(int subjectId, Course course) {
        return courseDao.updateCourse(subjectId, course);
    }

    /*
      RESOURCE
     */

    /*

    */
}
