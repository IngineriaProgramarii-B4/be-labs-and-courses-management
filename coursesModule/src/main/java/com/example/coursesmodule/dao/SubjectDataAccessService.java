package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.repository.ComponentRepo;
import com.example.coursesmodule.repository.EvaluationRepo;
import com.example.coursesmodule.repository.ResourceRepo;
import com.example.coursesmodule.repository.SubjectRepo;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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

    // SUBJECTS

    @Override
    public int insertSubject(Subject subject) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        subject.setCreatedAt(new Date(formatter.format(date)));
        subject.setUpdatedAt(new Date(formatter.format(date)));
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        Resource oldImage = optionalSubject.get().getImage();
        if (oldImage != null) {
            String oldImageLocation = oldImage.getLocation(); //RESOURCE_PATH/Subject_image.jpg
            String oldImageLocationUpdated = oldImageLocation.substring(
                    0,
                    oldImageLocation.lastIndexOf("/") + 1
            ) + "DELETED_" + title + "_" + oldImage.getTitle();
            oldImage.setLocation(oldImageLocationUpdated);
            oldImage.setDeleted(true);
            oldImage.setUpdatedAt(new Date(formatter.format(date)));
            resourceRepo.save(oldImage);
            subjectToDelete.setImage(oldImage);
        }

        subjectToDelete.setDeleted(true);
        subjectToDelete.setImage(oldImage);
        subjectToDelete.setUpdatedAt(new Date(formatter.format(date)));
        subjectRepo.save(subjectToDelete);
        return 1;
    }

    @Override
    public int updateSubjectByTitle(String title, Subject subject) {
        Subject subjectToUpdate = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToUpdate == null)
            return 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();

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
            //oldImage location: RESOURCE_PATH/OldSubjectTitle_image.jpg -> RESOURCE_PATH/NewSubjectTitle_image.jpg;
            resourceRepo.save(oldImage);
        }

        subjectToUpdate.setCredits(subject.getCredits());
        subjectToUpdate.setYear(subject.getYear());
        subjectToUpdate.setSemester(subject.getSemester());
        subjectToUpdate.setDescription(subject.getDescription());

        subjectToUpdate.setUpdatedAt(new Date(formatter.format(date)));
        subjectRepo.save(subjectToUpdate);
        return 1;
    }

    @Override
    public int saveImageToSubject(String title, Resource image) {
        Subject subjectToUpdate = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToUpdate == null)
            return 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        Resource oldImage = subjectToUpdate.getImage();
        if (oldImage != null) {
            String oldImageLocation = oldImage.getLocation(); //RESOURCE_PATH/Subject_image.jpg
            String oldImageLocationUpdated = oldImageLocation.substring(
                    0,
                    oldImageLocation.lastIndexOf("/") + 1
            ) + "OUTDATED_" + title + "_" + oldImage.getTitle();

            oldImage.setLocation(oldImageLocationUpdated);
            //oldImage location: RESOURCE_PATH/Subject_image.jpg -> RESOURCE_PATH/OUTDATED_Subject_image.jpg;

            oldImage.setDeleted(true);
            oldImage.setUpdatedAt(new Date(formatter.format(date)));
            resourceRepo.save(oldImage);
        }
        image.setCreatedAt(new Date(formatter.format(date)));
        image.setUpdatedAt(new Date(formatter.format(date)));
        subjectToUpdate.setImage(image);
        subjectToUpdate.setUpdatedAt(new Date(formatter.format(date)));
        subjectRepo.save(subjectToUpdate);
        return 1;
    }

    // COMPONENTS

    @Override
    public int addComponent(String title, Component component) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        component.setCreatedAt(new Date(formatter.format(date)));
        component.setUpdatedAt(new Date(formatter.format(date)));
        subjectToModify.addComponent(component);
        subjectToModify.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        componentToDelete.setUpdatedAt(new Date(formatter.format(date)));
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        componentToUpdate.setUpdatedAt(new Date(formatter.format(date)));

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        resource.setCreatedAt(new Date(formatter.format(date)));
        resource.setUpdatedAt(new Date(formatter.format(date)));
        componentToModify.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        resourceToUpdate.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        resourceToDelete.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        evaluationMethod.setCreatedAt(new Date(formatter.format(date)));
        evaluationMethod.setUpdatedAt(new Date(formatter.format(date)));
        subjectToModify.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        evaluationToDelete.setUpdatedAt(new Date(formatter.format(date)));
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        evaluationToUpdate.setUpdatedAt(new Date(formatter.format(date)));
        evaluationRepo.save(evaluationToUpdate);
        return 1;
    }
}
