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
    /*
      SUBJECT
     */
    @Override
    public int insertSubject(Subject subject) {
        DB.add(new Subject(subject.getTitle(), subject.getId(), subject.getYear(), subject.getSemester(), subject.getCredits(), subject.getDescription()));
        return 1;
    }

    @Override
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


    @Override
    public int verifySubjectId(int subjectId) {
        return selectSubjectById(subjectId).isPresent() ? 1 : 0;
    }


    /*
      COURSE
     */

    @Override
    public int addCourse(int subjectId, Course course) {
        //check if subject already has a course
        if(selectSubjectById(subjectId).get().getCourse() != null){
            return 0;
        }
        selectSubjectById(subjectId).get().setCourse(course);
        return 1;
    }

    @Override
    public Course getCourse(int subjectId) {
        return selectSubjectById(subjectId).get().getCourse();
    }

    @Override
    public int deleteCourse(int id) {
        Course courseMaybe = selectSubjectById(id).get().getCourse();
        if (courseMaybe == null) {
            return 0;
        }
        selectSubjectById(id).get().removeCourse();
        return 1;
    }

    @Override
    public int updateCourse(int id, Course course) {
        return selectSubjectById(id)
                .map(s -> {
                    int indexOfCourseToUpdate = DB.indexOf(s);
                    if (indexOfCourseToUpdate >= 0) {
                        selectSubjectById(id).get().removeCourse();
                        selectSubjectById(id).get().setCourse(course);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    /*
      RESOURCE - COURSE
     */


    @Override
    public int addCourseResource(int subjectId, Resource resource) {
        //check if the subject has a course
        Optional<Subject> subjectMaybe = selectSubjectById(subjectId);
        if (subjectMaybe.isEmpty()) {
            return 0;
        }
        if (subjectMaybe.get().getCourse() == null) {
            return 0;
        }
        subjectMaybe.get().getCourse().addResource(resource);
        return 1;
    }

    @Override
    public List<Resource> getCourseResources(int subjectId) {
        //check if the subject has a course
        Optional<Subject> subjectMaybe = selectSubjectById(subjectId);
        if (subjectMaybe.isEmpty()) {
            return null;
        }
        if (subjectMaybe.get().getCourse() == null) {
            return null;
        }
        return subjectMaybe.get().getCourse().getResources();
    }

    @Override
    public Optional<Resource> getCourseResourceById(int subjectId, int resourceId) {
        //check if the subject has a course
        Optional<Subject> subjectMaybe = selectSubjectById(subjectId);
        if (subjectMaybe.isEmpty()) {
            return null;
        }
        if (subjectMaybe.get().getCourse() == null) {
            return null;
        }
        return subjectMaybe.get().getCourse().getCourseResourceById(resourceId);
    }
    @Override
    public int deleteCourseResourceById(int subjectId, int resourceId) {
        //check if the subject has a course
        Optional<Subject> subjectMaybe = selectSubjectById(subjectId);
        if (subjectMaybe.isEmpty()) {
            return 0;
        }
        if (subjectMaybe.get().getCourse() == null) {
            return 0;
        }
        subjectMaybe.get().getCourse().removeCourseResource(resourceId);
        return 1;
    }

    @Override
    public int updateCourseResourceById(int subjectId, int resourceId, Resource resource) {
        //check if the subject has a course
        Optional<Subject> subjectMaybe = selectSubjectById(subjectId);
        if (subjectMaybe.isEmpty()) {
            return 0;
        }
        if (subjectMaybe.get().getCourse() == null) {
            return 0;
        }
        subjectMaybe.get().getCourse().updateCourseResource(resourceId, resource);
        return 1;
    }

    /*
      APPROFUNDATION
     */

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

    /*
      RESOURCE FOR APPROFUNDATION
     */


   /* @Override
    public List<Resource> getResourcesForApprofundationId(int id, int approfundationId) {
        return selectSubjectById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .getResources();
    }

    @Override
    public Optional<Resource> getResourceByIdForApprofundationId(int id, int approfundationId, int resourceId) {
        return selectSubjectById(id).get().getApprofundationList().stream()
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
        if (selectSubjectById(id).get().getApprofundationList().stream()
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
                    int indexOfResourceToUpdate = selectSubjectById(id).get().getApprofundationList().stream()
                            .filter(approfundation -> approfundation.getId() == approfundationId)
                            .findFirst()
                            .get()
                            .getResources().indexOf(r);
                    if (indexOfResourceToUpdate >= 0) {
                        selectSubjectById(id).get().getApprofundationList().stream()
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
        selectSubjectById(id).get().getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst()
                .get()
                .removeResource(resourceMaybe.get());
        return 1;
    }*/


    /*
      EVALUATION
     */

    @Override
    public int addEvaluationMethod(int subjectId, Evaluation evaluationMethod) {
        //check if course doesn't already have a non-empty evaluation method
        if (selectSubjectById(subjectId).get().getEvaluationMethod() != null) {
            return 0;
        }
        selectSubjectById(subjectId).get().setEvaluationMethod(evaluationMethod);
        return 1;
    }

    @Override
    public int addEvaluationComponent(int subjectId, String component, float value) {
        if (selectSubjectById(subjectId).get().getEvaluationMethod() == null)
            return 0;
        if (selectSubjectById(subjectId).get().getEvaluationMethod().containsComponent(component))
            return 0;
        selectSubjectById(subjectId).get().getEvaluationMethod().addComponent(component, value);
        return 1;
    }

    @Override
    public Evaluation getEvaluationMethod(Subject subject) {
        return subject.getEvaluationMethod();
    }

    @Override
    public List<Object> getEvaluationComponent(Subject subject, String component) {
        //returns list of two objects in the form: (component, value) if component is part of the evaluation method
        if (subject.getEvaluationMethod() == null)
            return null;
        if (!subject.getEvaluationMethod().containsComponent(component))
            return null;
        return Arrays.asList(component, subject.getEvaluationMethod().getPercentage().get(component));
    }

    @Override
    public int deleteEvaluationMethod(int subjectId) {
        if (selectSubjectById(subjectId).get().getEvaluationMethod() == null)
            return 0;
        selectSubjectById(subjectId).get().removeEvaluationMethod();
        return 1;
    }

    @Override
    public int deleteEvaluationComponent(int subjectId, String component) {
        if (selectSubjectById(subjectId).get().getEvaluationMethod() == null)
            return 0;
        if (!selectSubjectById(subjectId).get().getEvaluationMethod().containsComponent(component))
            return 0;
        selectSubjectById(subjectId).get().getEvaluationMethod().removeComponent(component);
        return 1;
    }

    @Override
    public int updateEvaluationMethod(int subjectId, Evaluation evaluationMethod) {
        selectSubjectById(subjectId).get().setEvaluationMethod(evaluationMethod);
        return 1;
    }

    @Override
    public int updateEvaluationComponent(int subjectId, String component, float value) {
        if (selectSubjectById(subjectId).get().getEvaluationMethod() == null)
            return 0;
        if (!selectSubjectById(subjectId).get().getEvaluationMethod().containsComponent(component))
            return 0;
        selectSubjectById(subjectId).get().getEvaluationMethod().updateComponent(component, value);
        return 1;
    }
}
