package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.*;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    /**
     * SUBJECT
     */
    int insertSubject(Subject subject);

    List<Subject> selectAllSubjects();

    Optional<Subject> selectSubjectById(int subjectId);

    List<Subject> getSubjectsByYearAndSemester(int year, int semester);

    int deleteSubjectById(int subjectId);

    int updateSubjectById(int subjectId, Subject subject);

    /**
     * COURSE
     */
    int addCourse(int subjectId, Course course);

    Optional<Course> getCourse(int subjectId);

    int deleteCourse(int subjectId);

    int updateCourse(int subjectId, Course course);

    /**
     * RESOURCE
     */
    int addCourseResource(int subjectId, Resource resource);

    List<Resource> getCourseResources(int subjectId);

    Optional<Resource> getCourseResourceById(int subjectId, int resourceId);

    int deleteCourseResourceById(int subjectId, int resourceId);

    int updateCourseResourceById(int subjectId, int resourceId, Resource resource);

    /**
     * APPROFUNDATION
     */
    int addApprofundation(int subjectId, Approfundation approfundation);

    List<Approfundation> getApprofundations(int subjectId);

    Optional<Approfundation> getApprofundationById(int subjectId, int approfundationId);

    int deleteApprofundationById(int subjectId, int approfundationId);

    int updateApprofundationById(int subjectId, int approfundationId, Approfundation approfundation);

    List<Resource> getResourcesForApprofundationId(int subjectId, int approfundationId);

    Optional<Resource> getResourceByIdForApprofundationId(int subjectId, int approfundationId, int resourceId);

    int addResourceForApprofundationId(int subjectId, int approfundationId, Resource resource);

    int updateResourceForApprofundationId(int subjectId, int approfundationId, Resource resource);

    int deleteResourceForApprofundationId(int subjectId, int approfundationId, int resourceId);

    /**
     * EVALUATION
     */
    int addEvaluationMethod(int id, Evaluation evaluationMethod);

    Evaluation getEvaluationMethod(Course course);
    List<Object> getEvaluationComponent(Course course, Object component);

    int deleteEvaluationMethod(int id);

    int updateEvaluationMethod(int id, Evaluation evaluationMethod);
}
