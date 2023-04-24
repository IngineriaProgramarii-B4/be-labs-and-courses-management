package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public boolean validateSubject(Subject subject){
        if(subject.getTitle().isEmpty())
            return false;
        List<Subject> subjects = getAllSubjects();
        for(Subject subject1 : subjects)
            if(subject1.getTitle().equals(subject.getTitle()))
                return false;
        if(!validateComponents(subject)) return false;
        return true;
    }

    public boolean validateTitle(String title){
        for(Subject subject : courseDao.selectAllSubjects())
            if(subject.getTitle().equals(title))
                return true;
        return false;
    }

    public boolean validateComponents(Subject subject){
        boolean course = false, seminar = false, lab = false;
        for(Component component : subject.getComponentList()) {
            if (component.getType().equals("Course"))
                if (!course)
                    course = true;
                else return false;
            if (component.getType().equals("Seminar"))
                if(!seminar)
                    seminar = true;
                else return false;
            if(component.getType().equals("Laboratory"))
                if(!lab)
                    lab = true;
                else return false;
        }
        return true;
    }

    public boolean validateYearAndSemester(int year, int semester){
        return year >= 1 && year <= 4 && semester >= 1 && semester <= 2;
    }


    public int addSubject(Subject subject) {
        if(!validateSubject(subject))
            return 0;
        return courseDao.insertSubject(subject);
    }

    public List<Subject> getAllSubjects() {
        return courseDao.selectAllSubjects();
    }

    public Optional<Subject> getSubjectByTitle(String title) {
        if(validateTitle(title))
            return courseDao.selectSubjectByTitle(title);
        else return Optional.empty();
    }

    public List<Subject> getSubjectsByYearAndSemester(int year, int semester) {
        if(validateYearAndSemester(year, semester))
            return courseDao.getSubjectsByYearAndSemester(year, semester);
        return null;
    }

    public int deleteSubjectByTitle(String title) {
        if(validateTitle(title))
            return courseDao.deleteSubjectByTitle(title);
        else return 0;
    }

    public int updateSubjectByTitle(String title, Subject subject) {
        if(!validateSubject(subject))
            return 0;
        System.out.println("title: " + title);
        System.out.println("subject: " + subject);
        return courseDao.updateSubjectByTitle(title, subject);
    }
}
