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
    private static final String RESOURCE_PATH = "savedResources/";

    @Autowired
    public SubjectService(@Qualifier("postgres") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public boolean validateSubject(Subject subject){
        if(subject.getTitle().isEmpty() || subject.isDeleted())
            return false;
        List<Subject> subjects = getAllSubjects();
        for(Subject subject1 : subjects)
            if(subject1.getTitle().equals(subject.getTitle())) //only non-deleted subjects
                return false;
        return validateComponents(subject);
    }

    public boolean validateTitle(String title){
        List<Subject> subjects = getAllSubjects();
        for(Subject subject : subjects)
            if(subject.getTitle().equals(title)) //only non-deleted subjects
                return true;
        return false;
    }

    private boolean validateUpdate(String title, String title1) {
        if(title.equals(title1))
            return true;
        for(Subject subject : courseDao.selectAllSubjects()) //only non-deleted subjects
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
        ComponentService componentService = new ComponentService(courseDao);

        if (!validateTitle(title))
            return 0;

        for (Component component : courseDao.getComponents(title)) {
            String type = component.getType();
            componentService.deleteComponentByType(title, type);
        }

        return courseDao.deleteSubjectByTitle(title);
    }

    public int updateSubjectByTitle(String title, Subject subject) {
        if(!validateUpdate(title, subject.getTitle()))
            return 0;
        return courseDao.updateSubjectByTitle(title, subject);
    }

    public int uploadSubjectImage(String title, MultipartFile image) {
        Optional<Subject> subjectMaybe = getSubjectByTitle(title);
        if(subjectMaybe.isEmpty())
            return 0;

        Resource oldImage = subjectMaybe.get().getImage();
        String oldImageLocation = oldImage.getLocation();
        String potentialLocation = oldImageLocation.substring(
                0,
                oldImageLocation.lastIndexOf("/") + 1
        ) + "OUTDATED_" + oldImage.getTitle();
        File oldImageFile = new File(oldImageLocation);

        String fileName = title + "_" + image.getOriginalFilename();
        String filePath = RESOURCE_PATH + fileName;
        String fileType = image.getContentType();
        Resource resource = new Resource(fileName, filePath, fileType, false);
        if(courseDao.saveImageToSubject(title, resource) == 0)
            return 0;
        try {
            String absolutePath = new File("").getAbsolutePath();
            String folderPath = absolutePath + "/" + RESOURCE_PATH;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            boolean renameSuccessful = oldImageFile.renameTo(new File(potentialLocation));
            image.transferTo(new File(folderPath + fileName));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}