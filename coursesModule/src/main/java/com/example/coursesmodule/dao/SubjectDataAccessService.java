package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.repository.ComponentRepository;
import com.example.coursesmodule.repository.EvaluationRepo;
import com.example.coursesmodule.repository.ResourceRepo;
import com.example.coursesmodule.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("postgres")
public class SubjectDataAccessService implements CourseDao{
    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ResourceRepo resourceRepo;

    @Autowired
    private EvaluationRepo evaluationRepo;

    // SUBJECTS

    @Override
    public int insertSubject(Subject subject) {
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
        subjectRepo.deleteSubjectByTitle(title);
        return 1;
    }

    @Override
    public int updateSubjectByTitle(String title, Subject subject) {
        Subject subjectToUpdate = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToUpdate == null)
            return 0;
        subjectToUpdate.setTitle(subject.getTitle());
        subjectToUpdate.setCredits(subject.getCredits());
        subjectToUpdate.setYear(subject.getYear());
        subjectToUpdate.setSemester(subject.getSemester());
        subjectToUpdate.setDescription(subject.getDescription());
        subjectRepo.save(subjectToUpdate);
        return 1;
    }

    // COMPONENTS

    @Override
    public int addComponent(String title, Component component) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        subjectToModify.addComponent(component);
        subjectRepo.save(subjectToModify);
        return 1;
    }

    @Override
    public List<Component> getComponents(String title) {
        return componentRepository.findAllBySubjectTitle(title);
    }

    @Override
    public Optional<Component> getComponentByType(String title, String type) {
        return componentRepository.findBySubjectTitleAndType(title, type);
    }

    @Override
    public int deleteComponentByType(String title, String type) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
        Component componentToDelete = componentRepository.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToDelete == null)
            return 0;
        subjectToModify.removeComponent(componentToDelete);
        subjectRepo.save(subjectToModify);
        componentRepository.delete(componentToDelete);
        return 1;
    }

    @Override
    public int updateComponentByType(String title, String type, Component component) {
        Component componentToUpdate = componentRepository.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToUpdate == null)
            return 0;
        componentToUpdate.setNumberWeeks(component.getNumberWeeks());
        componentToUpdate.setType(component.getType());
        componentRepository.save(componentToUpdate);
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
        Component componentToModify = componentRepository.findBySubjectTitleAndType(title, type).orElse(null);
        if(componentToModify == null)
            return 0;
        componentToModify.addResource(resource);
        componentRepository.save(componentToModify);
        return 1;
    }

    @Override
    public int updateResourceByTitleForComponentType(String subjectTitle, String componentType, String resourceTitle, Resource resource) {
        Resource resourceToUpdate = resourceRepo.findBySubjectTitleAndComponentTypeAndResourceTitle(subjectTitle, componentType, resourceTitle).orElse(null);
        if(resourceToUpdate == null)
            return 0;
        resourceToUpdate.setTitle(resource.getTitle());
        resourceToUpdate.setLocation(resource.getLocation());
        resourceToUpdate.setTimeStamp(LocalDateTime.now());
        resourceRepo.save(resourceToUpdate);
        return 1;
    }

    @Override
    public int deleteResourceByTitleForComponentType(String subjectTitle, String componentType, String resourceTitle) {
        Component componentToModify = componentRepository.findBySubjectTitleAndType(subjectTitle, componentType).orElse(null);
        if(componentToModify == null)
            return 0;
        Resource resourceToDelete = resourceRepo.findBySubjectTitleAndComponentTypeAndResourceTitle(subjectTitle, componentType, resourceTitle).orElse(null);
        if(resourceToDelete == null)
            return 0;
        componentToModify.removeResource(resourceToDelete);
        componentRepository.save(componentToModify);
        resourceRepo.delete(resourceToDelete);
        return 1;
    }

    // EVALUATION


    @Override
    public int addEvaluationMethod(String title, Evaluation evaluationMethod) {
        Subject subjectToModify = subjectRepo.findSubjectByTitle(title).orElse(null);
        if(subjectToModify == null)
            return 0;
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
        subjectToModify.removeEvaluation(evaluationToDelete);
        subjectRepo.save(subjectToModify);
        evaluationRepo.delete(evaluationToDelete);
        return 1;
    }

    @Override
    public int updateEvaluationMethodByComponent(String title, String component, Evaluation evaluationMethod) {
        Evaluation evaluationToUpdate = evaluationRepo.findBySubjectTitleAndComponent(title, component).orElse(null);
        if(evaluationToUpdate == null)
            return 0;
        evaluationToUpdate.setComponent(evaluationMethod.getComponent());
        evaluationToUpdate.setValue(evaluationMethod.getValue());
        evaluationRepo.save(evaluationToUpdate);
        return 1;
    }
}
