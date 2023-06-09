package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.repository.ComponentRepo;
import com.example.coursesmodule.repository.EvaluationRepo;
import com.example.coursesmodule.repository.ResourceRepo;
import com.example.coursesmodule.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository("postgres")
public class SubjectDataAccessService implements CourseDao{
    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ComponentRepo componentRepo;

    @Autowired
    private ResourceRepo resourceRepo;

    @Autowired
    private EvaluationRepo evaluationRepo;
    
    private static final String DATA_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_FORMAT);
    private LocalDateTime formatDateTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        return LocalDateTime.parse(formatter.format(dateTime), formatter);
    }

    // SUBJECTS

    @Override
    public int insertSubject(Subject subject) {
        subject.setCreatedAt(formatDateTime());
        subject.setUpdatedAt(formatDateTime());
        subjectRepo.save(subject);
        return 1;
    }

    @Override
    public List<Subject> selectAllSubjects() {
        return subjectRepo.findAll();
    }

    @Override
    public Optional<Subject> selectSubjectByTitle(String title) {
        return subjectRepo.findSubjectByTitle(title);
    }

    @Override
    public List<Subject> getSubjectsByYearAndSemester(int year, int semester) {
        return subjectRepo.findAllByYearAndSemester(year, semester);
    }

    @Override
    public int deleteSubjectByTitle(String title) {
        Optional<Subject> optionalSubject = subjectRepo.findSubjectByTitle(title);
        if (optionalSubject.isEmpty())
            return 0;
        Subject subjectToDelete = optionalSubject.get();

        Resource oldImage = optionalSubject.get().getImage();
        if (oldImage != null) {
            String oldImageLocation = oldImage.getLocation(); //RESOURCE_PATH/Subject_image.jpg
            String oldImageLocationUpdated = oldImageLocation.substring(
                    0,
                    oldImageLocation.lastIndexOf("/") + 1
            ) + "DELETED_" + title + "_" + oldImage.getTitle();
            oldImage.setLocation(oldImageLocationUpdated);
            oldImage.setDeleted(true);
            oldImage.setUpdatedAt(formatDateTime());
            resourceRepo.save(oldImage);
            subjectToDelete.setImage(oldImage);
        }

        subjectToDelete.setDeleted(true);
        subjectToDelete.setImage(oldImage);
        subjectToDelete.setUpdatedAt(formatDateTime());
        subjectRepo.save(subjectToDelete);
        return 1;
    }

    @Override
    public int updateSubjectByTitle(String title, Subject subject) {
        Subject subjectToUpdate = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToUpdate == null)
            return 0;


        if (!subjectToUpdate.getTitle().equals(subject.getTitle())) {
            subjectToUpdate.setTitle(subject.getTitle());

            //updating resources:
            for (Component component : subjectToUpdate.getComponentList()) {
                for (Resource resource : component.getResources()) {
                    component.removeResource(resource);

                    String resLocation = resource.getLocation();
                    String newResLocation = resLocation.substring(
                            0,
                            resLocation.lastIndexOf("/") + 1
                    ) + subject.getTitle() + "_" + component.getType() + "_" + resource.getTitle();
                    resource.setLocation(newResLocation);
                    //resource location: RESOURCE_PATH/OldSubjectTitle_Component_Resource.txt ->
                    // -> RESOURCE_PATH/NewSubjectTitle_Component_Resource.txt
                    component.addResource(resource);
                    resourceRepo.save(resource);
                }
                componentRepo.save(component);
            }
        }

        Resource oldImage = subjectToUpdate.getImage();
        if (oldImage != null) {
            String locationOfOldImage = oldImage.getLocation(); //RESOURCE_PATH/OldSubjectTitle_image.jpg
            String locationOfOldImageUpdated = locationOfOldImage.substring(
                    0,
                    locationOfOldImage.lastIndexOf("/") + 1
            ) + subject.getTitle() + "_" + oldImage.getTitle();

            oldImage.setLocation(locationOfOldImageUpdated);
            resourceRepo.save(oldImage);
        }

        subjectToUpdate.setCredits(subject.getCredits());
        subjectToUpdate.setYear(subject.getYear());
        subjectToUpdate.setSemester(subject.getSemester());
        subjectToUpdate.setDescription(subject.getDescription());

        subjectToUpdate.setUpdatedAt(formatDateTime());
        subjectRepo.save(subjectToUpdate);
        return 1;
    }

    @Override
    public int saveImageToSubject(String title, Resource image) {
        Subject subjectToUpdate = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToUpdate == null)
            return 0;

        Resource oldImage = subjectToUpdate.getImage();
        if (oldImage != null) {
            String oldImageLocation = oldImage.getLocation(); //RESOURCE_PATH/Subject_image.jpg
            String oldImageLocationUpdated = oldImageLocation.substring(
                    0,
                    oldImageLocation.lastIndexOf("/") + 1
            ) + "OUTDATED_" + title + "_" + oldImage.getTitle();

            oldImage.setLocation(oldImageLocationUpdated);

            oldImage.setDeleted(true);
            oldImage.setUpdatedAt(formatDateTime());
            resourceRepo.save(oldImage);
        }
        image.setCreatedAt(formatDateTime());
        image.setUpdatedAt(formatDateTime());
        subjectToUpdate.setImage(image);
        subjectToUpdate.setUpdatedAt(formatDateTime());
        subjectRepo.save(subjectToUpdate);
        return 1;
    }

    // COMPONENTS

    @Override
    public int addComponent(String title, Component component) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        component.setCreatedAt(formatDateTime());
        component.setUpdatedAt(formatDateTime());
        subjectToModify.addComponent(component);
        subjectToModify.setUpdatedAt(formatDateTime());
        subjectRepo.save(subjectToModify);
        return 1;
    }

    @Override
    public List<Component> getComponents(String title) {
        return componentRepo.findAllBySubjectTitle(title);
    }

    @Override
    public Optional<Component> getComponentByType(String title, String type) {
        return componentRepo.findBySubjectTitleAndType(title, type);
    }

    @Override
    public int deleteComponentByType(String title, String type) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        Component componentToDelete = componentRepo.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToDelete == null)
            return 0;
        componentToDelete.setDeleted(true);
        componentToDelete.setUpdatedAt(formatDateTime());
        subjectToModify.softDeleteComponent(componentToDelete);
        componentRepo.save(componentToDelete);
        subjectRepo.save(subjectToModify);
        return 1;
    }

    @Override
    public int updateComponentByType(String title, String type, Component component) {
        Component componentToUpdate = componentRepo.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToUpdate == null)
            return 0;
        componentToUpdate.setNumberWeeks(component.getNumberWeeks());
        componentToUpdate.setType(component.getType());

        componentToUpdate.setUpdatedAt(formatDateTime());

        componentRepo.save(componentToUpdate);
        return 1;
    }

    // RESOURCES

    @Override
    public List<Resource> getResourcesForComponentType(String title, String type) {
        return resourceRepo.findAllBySubjectTitleAndComponentType(title, type);
    }

    @Override
    public Optional<Resource> getResourceByTitleForComponentType(String subjectTitle, String componentType, String resourceTitle) {
        return resourceRepo.findBySubjectTitleAndComponentTypeAndResourceTitle(subjectTitle, componentType, resourceTitle);
    }

    @Override
    public int addResourceForComponentType(String title, String type, Resource resource) {
        Component componentToModify = componentRepo.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToModify == null)
            return 0;
        resource.setCreatedAt(formatDateTime());
        resource.setUpdatedAt(formatDateTime());
        componentToModify.setUpdatedAt(formatDateTime());
        componentToModify.addResource(resource);
        componentRepo.save(componentToModify);
        return 1;
    }

    @Override
    public int updateResourceByTitleForComponentType(String subjectTitle, String componentType, String resourceTitle, Resource resource) {
        Resource resourceToUpdate = resourceRepo.findBySubjectTitleAndComponentTypeAndResourceTitle(subjectTitle, componentType, resourceTitle).orElse(null);
        if(resourceToUpdate == null)
            return 0;
        resourceToUpdate.setTitle(resource.getTitle());
        resourceToUpdate.setLocation(resource.getLocation());
        resourceToUpdate.setUpdatedAt(formatDateTime());
        resourceRepo.save(resourceToUpdate);
        return 1;
    }

    @Override
    public int deleteResourceByTitleForComponentType(String subjectTitle, String componentType, String resourceTitle) {
        Component componentToModify = componentRepo.findBySubjectTitleAndType(subjectTitle, componentType).orElse(null);
        if(componentToModify == null)
            return 0;
        Resource resourceToDelete = resourceRepo.findBySubjectTitleAndComponentTypeAndResourceTitle(subjectTitle, componentType, resourceTitle).orElse(null);
        if(resourceToDelete == null)
            return 0;

        resourceToDelete.setDeleted(true);
        resourceToDelete.setLocation(resourceToDelete.getLocation().substring(
                0,
                resourceToDelete.getLocation().lastIndexOf("/") + 1
        ) + "DELETED_" + subjectTitle + "_" + componentType + "_" + resourceTitle);
        resourceToDelete.setUpdatedAt(formatDateTime());
        resourceRepo.save(resourceToDelete);

        componentToModify.softDeleteResource(resourceToDelete);
        componentRepo.save(componentToModify);
        return 1;
    }

    // EVALUATION


    @Override
    public int addEvaluationMethod(String title, Evaluation evaluationMethod) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        evaluationMethod.setCreatedAt(formatDateTime());
        evaluationMethod.setUpdatedAt(formatDateTime());
        subjectToModify.setUpdatedAt(formatDateTime());
        subjectToModify.addEvaluation(evaluationMethod);
        subjectRepo.save(subjectToModify);
        return 1;
    }

    @Override
    public Optional<Evaluation> getEvaluationMethodByComponent(String title, String component) {
        return evaluationRepo.findBySubjectTitleAndComponent(title, component);
    }

    @Override
    public List<Evaluation> getEvaluationMethods(String title) {
        return evaluationRepo.findAllBySubjectTitle(title);
    }

    @Override
    public int deleteEvaluationMethod(String title, String component) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        Evaluation evaluationToDelete = evaluationRepo.findBySubjectTitleAndComponent(title, component).orElse(null);
        if(evaluationToDelete == null)
            return 0;
        evaluationToDelete.setDeleted(true);
        evaluationToDelete.setUpdatedAt(formatDateTime());
        subjectToModify.softDeleteEvaluation(evaluationToDelete);
        evaluationRepo.save(evaluationToDelete);
        subjectRepo.save(subjectToModify);
        return 1;
    }

    @Override
    public int updateEvaluationMethodByComponent(String title, String component, Evaluation evaluationMethod) {
        Evaluation evaluationToUpdate = evaluationRepo.findBySubjectTitleAndComponent(title, component).orElse(null);
        if(evaluationToUpdate == null)
            return 0;
        evaluationToUpdate.setComponent(evaluationMethod.getComponent());
        evaluationToUpdate.setValue(evaluationMethod.getValue());
        evaluationToUpdate.setDescription(evaluationMethod.getDescription());
        evaluationToUpdate.setUpdatedAt(formatDateTime());
        evaluationRepo.save(evaluationToUpdate);
        return 1;
    }
}
