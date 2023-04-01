package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Resource;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    /**
     * COURSE
     */
    int insertCourse(Course course);

    List<Course> selectAllCourses();

    Optional<Course> selectCourseById(int id);

    List<Course> getCoursesByYearAndSemester(int year, int semester);

    int deleteCourseById(int id);

    int updateCourseById(int id, Course course);

    /**
     * RESOURCE
     */
    int addResource(int id, Resource resource);

    List<Resource> getResources(Course course);

    Optional<Resource> getResourceById(Course course, int resourceId);

    int deleteResourceById(int id, int resourceId);

    int updateResourceById(int id, int resourceId, Resource resource);

    /**
     * APPROFUNDATION
     */
    int addApprofundation(int id, Approfundation approfundation);

    List<Approfundation> getApprofundations(Course course);

    Optional<Approfundation> getApprofundationById(Course course, int approfundationId);

    int deleteApprofundationById(int id, int approfundationId);

    int updateApprofundationById(int id, int approfundationId, Approfundation approfundation);

    /**
     * EVALUATION
     */
    int addEvaluationMethod(int id, Evaluation evaluationMethod);

    Evaluation getEvaluationMethod(Course course);
    List<Object> getEvaluationComponent(Course course, Object component);

    int deleteEvaluationMethod(int id);

    int updateEvaluationMethod(int id, Evaluation evaluationMethod);
}
