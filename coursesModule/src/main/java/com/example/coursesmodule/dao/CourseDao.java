package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    int insertCourse(Course course);

    List<Course> selectAllCourses();

    Optional<Course> selectCourseById(int id);

    int deleteCourseById(int id);

    int updateCourseById(int id, Course course);

    int addResource(int id, Resource resource);

    List<Resource> getResources(Course course);

    Optional<Resource> getResourceById(Course course, int resourceId);

    int deleteResourceById(int id, int resourceId);

    int updateResourceById(int id, int resourceId, Resource resource);

    int addApprofundation(int id, Approfundation approfundation);

    List<Approfundation> getApprofundations(Course course);

    Optional<Approfundation> getApprofundationById(Course course, int approfundationId);

    int deleteApprofundationById(int id, int approfundationId);

    int updateApprofundationById(int id, int approfundationId, Approfundation approfundation);
}
