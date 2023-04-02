package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Evaluation;
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

    /**
     * COURSE
     */
    public int verifyCourseValid(Course course){
        return course.getId() <= 0 || course.getTitle().isEmpty() ? 0 : 1;
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
    public List<Course> getCoursesByYearAndSemester(int year, int semester) {
        return courseDao.getCoursesByYearAndSemester(year, semester);
    }
    public int deleteCourseById(int id) {
        return courseDao.deleteCourseById(id);
    }
    public int updateCourseById(int id, Course course) {
        return verifyCourseValid(course) == 0 ? 0 : courseDao.updateCourseById(id, course);
    }

    /**
     * RESOURCE
     */
    public int verifyResourceValid(Resource resource){
        return resource.getId() <= 0 || resource.getTitle().isEmpty() ? 0 : 1;
    }
    public void addResource(int id, Resource resource) {
        courseDao.addResource(id, resource);
    }

    public List<Resource> getResources(Course course) {
        return courseDao.getResources(course);
    }
    public Optional<Resource> getResourceById(Course course, int resourceId) {
        return courseDao.getResourceById(course, resourceId);
    }
    public int deleteResourceById(int id, int resourceId) {
        return courseDao.deleteResourceById(id, resourceId);
    }
    public int updateResourceById(int id, int resourceId, Resource resource) {
        return verifyResourceValid(resource) == 0 ? 0 : courseDao.updateResourceById(id, resourceId, resource);
    }

    /**
     * APPROFUNDATION
     */


    /**
     * EVALUATION
     */
    public int verifyEvaluationMethodValid(Evaluation evaluationMethod) {
        float totalValue = 0;
        for (Object component : evaluationMethod.getPercentage().keySet())
            totalValue += evaluationMethod.getPercentage().get(component);

        return totalValue == 100.0f ? 0 : 1;
    }

    public int addEvaluationMethod(int id, Evaluation evaluationMethod) {
        return verifyEvaluationMethodValid(evaluationMethod) == 1 ? 0 : courseDao.addEvaluationMethod(id, evaluationMethod);
    }

    public Evaluation getEvaluationMethod(Course course) {
        return courseDao.getEvaluationMethod(course);
    }
    public List<Object> getEvaluationComponent(Course course, Object component) {
        return courseDao.getEvaluationComponent(course, component);
    }

    public int deleteEvaluationMethod(int id) {
        return courseDao.deleteEvaluationMethod(id);
    }

    public int updateEvaluationMethod(int id, Evaluation evaluationMethod) {
        return verifyEvaluationMethodValid(evaluationMethod) == 0 ? 0 : courseDao.updateEvaluationMethod(id, evaluationMethod);
    }
}
