package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final CourseDao courseDao;

    @Autowired
    public EvaluationService(@Qualifier("postgres")  CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public boolean validateEval(String title, Evaluation evaluation){
        double sum = 0.;
        for(Evaluation eval : courseDao.getEvaluationMethods(title))
            sum+=eval.getValue();
        if(sum+evaluation.getValue() > 1.0)
            return false;
        for(Component comp : courseDao.getComponents(title))
            if(comp.getType().equals(evaluation.getComponent()))
                return true;
        return false;
    }

    public List<Evaluation> getEvaluationMethods(String title){
        return courseDao.getEvaluationMethods(title);
    }

    public Optional<Evaluation> getEvaluationMethodByComponent(String title, String component){
        return courseDao.getEvaluationMethodByComponent(title, component);
    }

    public int addEvaluationMethod(String title, Evaluation evaluation){
        if(validateEval(title, evaluation))
            return courseDao.addEvaluationMethod(title, evaluation);
        return 0;
    }

    public int deleteEvaluationMethodByComponent(String title, String component){
        return courseDao.deleteEvaluationMethod(title, component);
    }

    public int updateEvaluationMethodByComponent(String title, String component, Evaluation evaluation){
        if(validateEval(title, evaluation))
            return courseDao.updateEvaluationMethodByComponent(title, component, evaluation);
        return 0;
    }
}
