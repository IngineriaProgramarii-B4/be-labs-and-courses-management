package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Approfundation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprofundationService {

    private final CourseDao courseDao;

    @Autowired
    public ApprofundationService(@Qualifier("fakeDao") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyApprofundationValid(Approfundation approfundation) {
        return approfundation.getNumberWeeks() <= 0 || approfundation.getId() <= 0 ? 0 : 1;
    }

    public int addApprofundation(int id, Approfundation approfundation) {
        return verifyApprofundationValid(approfundation) == 0 ? 0 : courseDao.addApprofundation(id, approfundation);
    }

    public List<Approfundation> getApprofundations(int subjectId) {
        return courseDao.getApprofundations(subjectId);
    }

    public Optional<Approfundation> getApprofundationById(int subjectId, int approfundationId) {
        return courseDao.getApprofundationById(subjectId, approfundationId);
    }

    public int deleteApprofundationById(int id, int approfundationId) {
        return courseDao.deleteApprofundationById(id, approfundationId);
    }

    public int updateApprofundationById(int id, int approfundationId, Approfundation approfundation) {
        return verifyApprofundationValid(approfundation) == 0 ? 0 : courseDao.updateApprofundationById(id, approfundationId, approfundation);
    }
}

    ///////////////////////////////////////////////////////////////////////////////////////


