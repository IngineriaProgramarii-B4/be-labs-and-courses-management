package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final CourseDao courseDao;

    @Autowired
    public SubjectService(@Qualifier("fakeDao") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    /**
     * SUBJECT
     */
    public int verifySubjectValid(Subject subject){
        return subject.getId() <= 0 || subject.getTitle().isEmpty() ? 0 : 1;
    }
    public int addSubject(Subject subject) {
        return courseDao.insertSubject(subject);
    }

    public List<Subject> getAllSubjects() {
        return courseDao.selectAllSubjects();
    }

    public Optional<Subject> getSubjectById(int subjectId) {
        return courseDao.selectSubjectById(subjectId);
    }

    public List<Subject> getSubjectsByYearAndSemester(int year, int semester) {
        return courseDao.getSubjectsByYearAndSemester(year, semester);
    }

    public int deleteSubjectById(int subjectId) {
        return courseDao.deleteSubjectById(subjectId);
    }

    public int updateSubjectById(int subjectId, Subject subject) {
        return courseDao.updateSubjectById(subjectId, subject);
    }
}
