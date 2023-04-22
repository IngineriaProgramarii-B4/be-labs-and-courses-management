package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {
    private final CourseDao courseDao;

    @Autowired
    public SubjectService(@Qualifier("postgres") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    /*
      SUBJECT
     */
    public int verifySubjectValid(Subject subject){
        return subject.getTitle().isEmpty() ? 0 : 1;
    }

    public int addSubject(Subject subject) {
        if(verifySubjectValid(subject) == 0)
            return 0;
        return courseDao.insertSubject(subject);
    }

    public List<Subject> getAllSubjects() {
        return courseDao.selectAllSubjects();
    }

    public Optional<Subject> getSubjectByTitle(String title) {
        return courseDao.selectSubjectByTitle(title);
    }

    public List<Subject> getSubjectsByYearAndSemester(int year, int semester) {
        return courseDao.getSubjectsByYearAndSemester(year, semester);
    }

    public int deleteSubjectByTitle(String title) {
        return courseDao.deleteSubjectByTitle(title);
    }

    public int updateSubjectByTitle(String title, Subject subject) {
        if(verifySubjectValid(subject) == 0)
            return 0;
        System.out.println("title: " + title);
        System.out.println("subject: " + subject);
        return courseDao.updateSubjectByTitle(title, subject);
    }
}
