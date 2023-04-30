package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class SubjectService {
    private final CourseDao courseDao;
    private final String resourcePath = "savedResources/";

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
        return validateComponents(subject);
    }

    public boolean validateTitle(String title){
        List<Subject> subjects = getAllSubjects();
        for(Subject subject : subjects)
            if(subject.getTitle().equals(title))
                return true;
        return false;
    }

    private boolean validateUpdate(String title, String title1) {
        if(title.equals(title1))
            return true;
        for(Subject subject : courseDao.selectAllSubjects())
            if(subject.getTitle().equals(title1))
                return false;
        return true;
    }

    public boolean validateComponents(Subject subject) {
        Set<String> componentTypes = new HashSet<>();
        for (Component component : subject.getComponentList()) {
            if (!componentTypes.add(component.getType())) {
                return false; // found a duplicate component type
            }
        }
        return true; // all component types are unique
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
        return courseDao.getSubjectsByYearAndSemester(year, semester);
    }

    public int deleteSubjectByTitle(String title) {
        if(validateTitle(title))
            return courseDao.deleteSubjectByTitle(title);
        else return 0;
    }

    public int updateSubjectByTitle(String title, Subject subject) {
        if(!validateUpdate(title, subject.getTitle()))
            return 0;
        return courseDao.updateSubjectByTitle(title, subject);
    }

    public int uploadSubjectImage(String title, MultipartFile image){
        Optional<Subject> subjectMaybe = getSubjectByTitle(title);
        if(subjectMaybe.isEmpty())
            return 0;
        String fileName = title + "_" + image.getOriginalFilename();
        String filePath = resourcePath + fileName;
        String fileType = image.getContentType();
        Resource resource = new Resource(fileName, filePath, fileType);
        if(courseDao.saveImageToSubject(title, resource) == 0)
            return 0;
        try {
            String absolutePath = new File("").getAbsolutePath();
            // get the absolute path of the savedResources folder
            String folderPath = absolutePath + "/" + resourcePath;
            // create the savedResources folder if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }
            // transfer the file to the savedResources folder
            image.transferTo(new File(folderPath + fileName));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
