package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    private final CourseDao courseDao;

    @Autowired
    public EvaluationService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }


    public int verifyEvaluationMethodValid(Evaluation evaluationMethod) {
        float totalValue = 0;
        for (Object component : evaluationMethod.getPercentage().keySet())
            totalValue += evaluationMethod.getPercentage().get(component);

        return totalValue == 100.0f ? 0 : 1;
    }

    public int verifyIfComponentCanBeAdded(Evaluation evaluationMethod, String newComponent, float value) {
        float totalValue = 0;
        for (Object component : evaluationMethod.getPercentage().keySet())
            totalValue += evaluationMethod.getPercentage().get(component);

        return totalValue + value == 100.0f ? 0 : 1;
    }

    public int addEvaluationMethod(int subjectId, Evaluation evaluationMethod) {
        return verifyEvaluationMethodValid(evaluationMethod) == 1 ? 0 : courseDao.addEvaluationMethod(subjectId, evaluationMethod);
    }

    public int addEvaluationComponent(int subjectId, String component, float value) {
        return verifyIfComponentCanBeAdded(courseDao.getEvaluationMethod(
                courseDao.selectSubjectById(subjectId).get()), component, value)
                == 1 ? 0 : courseDao.addEvaluationComponent(subjectId, component, value);
    }

    public Evaluation getEvaluationMethod(Subject subject) {
        return courseDao.getEvaluationMethod(subject);
    }

    public List<Object> getEvaluationComponent(Subject subject, String component) {
        return courseDao.getEvaluationComponent(subject, component);
    }

    public int deleteEvaluationMethod(int subjectId) {
        return courseDao.deleteEvaluationMethod(subjectId);
    }

    public int deleteEvaluationComponent(int subjectId, String component) {
        return courseDao.deleteEvaluationComponent(subjectId, component);
    }

    public int updateEvaluationMethod(int subjectId, Evaluation evaluationMethod) {
        return verifyEvaluationMethodValid(evaluationMethod) == 0 ? 0 : courseDao.updateEvaluationMethod(subjectId, evaluationMethod);
    }

    public int updateEvaluationComponent(int subjectId, String component, float value) {
        return courseDao.updateEvaluationComponent(subjectId, component, value);
    }
}
