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



      /*EVALUATION*/

    /*public int verifyEvaluationMethodValid(Evaluation evaluationMethod) {
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
    }*/
}
