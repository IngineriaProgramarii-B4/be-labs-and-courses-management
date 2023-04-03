package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("fakeDao")
public class FakeCourseDataAccessService implements CourseDao {
    private static List<Subject> DB = new ArrayList<>();

    public FakeCourseDataAccessService(){

        DB.add(new Subject("Math", 1, 5, 1, 1,""));
        DB.add(new Subject("Data Structures", 2, 6, 1, 1,""));
        DB.add(new Subject("Computer Architecture and Operating Systems", 3, 5, 1, 1,""));
        DB.add(new Subject("Logics for Computer Science", 4, 6, 1, 1,""));
        DB.add(new Subject("Introduction to Programming", 5, 4, 1, 1,""));
        DB.add(new Subject("English I", 6, 4, 1, 1,""));
        DB.add(new Subject("Object Oriented Programming", 7, 6, 1, 2,""));
        DB.add(new Subject("Operating Systems", 8, 6, 1, 2,""));
        DB.add(new Subject("Algebraic Foundations of Computer Science", 9, 5, 1, 2,""));
        DB.add(new Subject("Algorithms Design", 10, 5, 1, 2,""));
        DB.add(new Subject("English II", 11, 4, 1, 2,""));
        DB.add(new Subject("Probabilities and Statistics", 12, 4, 1, 2,""));

    }
    /**
     * SUBJECT
     */
    @Override
    public int insertSubject(Subject subject) {
        if (selectSubjectById(subject.getId()).isPresent()) {
            return 0;
        }
        DB.add(subject);
        return 1;
    }
    public List<Subject> selectAllSubjects() {
        return DB;
    }
    @Override
    public Optional<Subject> selectSubjectById(int id) {
        return DB.stream()
                .filter(subject -> subject.getId() == id)
                .findFirst();
    }

    @Override
    public List<Subject> getSubjectsByYearAndSemester(int year, int semester) {
        return DB.stream()
                .filter(subject -> subject.getYear() == year && subject.getSemester() == semester)
                .toList();
    }

    @Override
    public int deleteSubjectById(int id) {
        Optional<Subject> subjectMaybe = selectSubjectById(id);
        if (subjectMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(subjectMaybe.get());
        return 1;
    }

    @Override
    public int updateSubjectById(int id, Subject subject) {
        return selectSubjectById(id)
                .map(s -> {
                    int indexOfSubjectToUpdate = DB.indexOf(s);
                    if (indexOfSubjectToUpdate >= 0) {
                        DB.set(indexOfSubjectToUpdate, new Subject(subject.getTitle(), subject.getId(), subject.getCredits(), subject.getYear(), subject.getSemester(), subject.getDescription()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }


    /**
     * COURSE
     */

    @Override
    public int addCourse(int subjectId, Course course) {
        //check if subject already has a course
        if (selectSubjectById(subjectId).get().getCourse().isPresent()) {
            return 0;
        }
        selectSubjectById(subjectId).get().addCourse(course);
        return 1;
    }

    @Override
    public Optional<Course> getCourse(int subjectId) {
        return selectSubjectById(subjectId).get().getCourse();
    }

    @Override
    public int deleteCourse(int id) {
        Optional<Course> courseMaybe = selectSubjectById(id).get().getCourse();
        if (courseMaybe.isEmpty()) {
            return 0;
        }
        selectSubjectById(id).get().removeCourse(courseMaybe.get());
        return 1;
    }

    @Override
    public int updateCourse(int id, Course course) {
        return selectSubjectById(id)
                .map(s -> {
                    int indexOfCourseToUpdate = DB.indexOf(s);
                    if (indexOfCourseToUpdate >= 0) {
                        selectSubjectById(id).get().removeCourse(selectSubjectById(id).get().getCourse().get());
                        selectSubjectById(id).get().addCourse(course);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    /**
     * RESOURCE
     */
    @Override
    public int addCourseResource(int subjectId, Resource resource) {
        //check if the subject has a course
        if (selectSubjectById(subjectId).get().getCourse().isEmpty()) {
            return 0;
        }
        selectSubjectById(subjectId).get().getCourse().get().addCourseResource(resource);
        return 1;
    }

    @Override
    public List<Resource> getCourseResources(int subjectId) {
        //check if the subject has a course
        if (selectSubjectById(subjectId).get().getCourse().isEmpty()) {
            return null;
        }
        return selectSubjectById(subjectId).get().getCourse().get().getCourseResources();
    }

    @Override
    public Optional<Resource> getCourseResourceById(int subjectId, int resourceId) {
        //check if the subject has a course
        if (selectSubjectById(subjectId).get().getCourse().isEmpty()) {
            return null;
        }
        return selectSubjectById(subjectId).get().getCourse().get().getCourseResourceById(resourceId);
    }
    @Override
    public int deleteCourseResourceById(int subjectId, int resourceId) {
        //check if the subject has a course
        if (selectSubjectById(subjectId).get().getCourse().isEmpty()) {
            return 0;
        }
        selectSubjectById(subjectId).get().getCourse().get().removeCourseResource(resourceId);
        return 1;
    }

    @Override
    public int updateCourseResourceById(int subjectId, int resourceId, Resource resource) {
        //check if the subject has a course
        if (selectSubjectById(subjectId).get().getCourse().isEmpty()) {
            return 0;
        }
        selectSubjectById(subjectId).get().getCourse().get().updateCourseResource(resourceId, resource);
        return 1;
    }

    /**
     * APPROFUNDATION
     */
    ///////////////////////////////////////////////////////


    @Override
    public int addApprofundation(int subjectId, Approfundation approfundation) {
        // check if the approfundation already exists in subject
        if (selectSubjectById(subjectId).get().getApprofundationList().contains(approfundation)) {
            return 0;
        }
        selectSubjectById(subjectId).get().addApprofundation(approfundation);
        return 1;
    }

    @Override
    public List<Approfundation> getApprofundations(int subjectId) {
        return selectSubjectById(subjectId).get().getApprofundationList();
    }

    @Override
    public Optional<Approfundation> getApprofundationById(int subjectId, int approfundationId) {
        return selectSubjectById(subjectId).get().
                getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst();
    }

    @Override
    public int deleteApprofundationById(int id, int approfundationId) {
        Optional<Approfundation> approfundationMaybe = getApprofundationById(id, approfundationId);
        if (approfundationMaybe.isEmpty()) {
            return 0;
        }
        selectSubjectById(id).get().removeApprofundation(approfundationMaybe.get());
        return 1;
    }

    @Override
    public int updateApprofundationById(int id, int approfundationId, Approfundation approfundation) {
        return getApprofundationById(id, approfundationId)
                .map(a -> {
                    int indexOfApprofundationToUpdate = selectSubjectById(id).get().getApprofundationList().indexOf(a);
                    if (indexOfApprofundationToUpdate >= 0) {
                        selectSubjectById(id).get().getApprofundationList().set(indexOfApprofundationToUpdate, approfundation);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    ///////////////////////////////////////////////////////

    @Override
    public List<Resource> getResourcesForApprofundationId(int subjectId, int approfundationId) {
        return null;
    }

    @Override
    public Optional<Resource> getResourceByIdForApprofundationId(int subjectId, int approfundationId, int resourceId) {
        return Optional.empty();
    }

    @Override
    public int addResourceForApprofundationId(int subjectId, int approfundationId, Resource resource) {
        return 0;
    }

    @Override
    public int updateResourceForApprofundationId(int subjectId, int approfundationId, Resource resource) {
        return 0;
    }

    @Override
    public int deleteResourceForApprofundationId(int subjectId, int approfundationId, int resourceId) {
        return 0;
    }

    /**
     * EVALUATION
     */

    @Override
    public int addEvaluationMethod(int id, Evaluation evaluationMethod) {
        return 0;
    }

    @Override
    public Evaluation getEvaluationMethod(Course course) {
        return null;
    }

    @Override
    public List<Object> getEvaluationComponent(Course course, Object component) {
        return null;
    }

    @Override
    public int deleteEvaluationMethod(int id) {
        return 0;
    }

    @Override
    public int updateEvaluationMethod(int id, Evaluation evaluationMethod) {
        return 0;
    }
    /*
    *//**
     * APPROFUNDATION
     *//*


    @Override
    public List<Resource> getResourcesForApprofundationId(int id, int approfundationId) {
        return selectCourseById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .getResources();
    }

    @Override
    public Optional<Resource> getResourceByIdForApprofundationId(int id, int approfundationId, int resourceId) {
        return selectCourseById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .getResources().stream()
                .filter(resource -> resource.getId() == resourceId)
                .findFirst();
    }

    @Override
    public int addResourceForApprofundationId(int id, int approfundationId, Resource resource) {
        //check if the resource already exists
        if (selectCourseById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .getResources().stream()
                .anyMatch(resource1 -> resource1.getId() == resource.getId())) {
            return 0;
        }

        return 0;
    }

    @Override
    public int updateResourceForApprofundationId(int id, int approfundationId, Resource resource) {
        return getResourceByIdForApprofundationId(id, approfundationId, resource.getId())
                .map(r -> {
                    int indexOfResourceToUpdate = selectCourseById(id).get().getApprofundationList().stream()
                            .filter(approfundation -> approfundation.getId() == approfundationId)
                            .findFirst()
                            .get()
                            .getResources().indexOf(r);
                    if (indexOfResourceToUpdate >= 0) {
                        selectCourseById(id).get().getApprofundationList().stream()
                                .filter(approfundation -> approfundation.getId() == approfundationId)
                                .findFirst()
                                .get()
                                .getResources().set(indexOfResourceToUpdate, new Resource(resource.getId(),
                                resource.getTitle(), resource.getLocation()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    @Override
    public int deleteResourceForApprofundationId(int id, int approfundationId, int resourceId) {
        Optional<Resource> resourceMaybe = getResourceByIdForApprofundationId(id, approfundationId, resourceId);
        if (resourceMaybe.isEmpty()) {
            return 0;
        }
        selectCourseById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .removeResource(resourceMaybe.get());
        return 1;
    }


    *//**
     * EVALUATION
     *//*
    @Override
    public int addEvaluationMethod(int id, Evaluation evaluationMethod) {
        //check if course doesn't already have a non-empty evaluation method
        if (selectCourseById(id).get().getEvaluationMethod().getNumberOfComponents() == 0) {
            return 0;
        }
        selectCourseById(id).get().setEvaluationMethod(evaluationMethod);
        return 1;
    }

    @Override
    public Evaluation getEvaluationMethod(Course course) {
        return course.getEvaluationMethod();
    }

    @Override
    public List<Object> getEvaluationComponent(Course course, Object component) {
        //returns list of two objects in the form: (component, value) if component is part of the evaluation method
        if (!course.getEvaluationMethod().containsComponent(component))
            return null;
        return Arrays.asList(component, course.getEvaluationMethod().getPercentage().get(component));
    }

    @Override
    public int deleteEvaluationMethod(int id) {
        if (selectCourseById(id).get().getEvaluationMethod().getNumberOfComponents() == 0)
            return 0;
        selectCourseById(id).get().removeEvaluationMethod();
        return 1;
    }

    @Override
    public int updateEvaluationMethod(int id, Evaluation evaluationMethod) {
        selectCourseById(id).get().setEvaluationMethod(evaluationMethod);
        return 1;
    }*/
    /*
      END EVALUATION
     */
}
