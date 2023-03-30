package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeCourseDataAccessService implements CourseDao {
    private static List<Course> DB = new ArrayList<>();

    public FakeCourseDataAccessService(){
        DB.add(new Course("Math", 1, 5, 1, 1));
        DB.add(new Course("Data Structures", 2, 6, 1, 1));
        DB.add(new Course("Computer Architecture and Operating Systems", 3, 5, 1, 1));
        DB.add(new Course("Logics for Computer Science", 4, 6, 1, 1));
        DB.add(new Course("Introduction to Programming", 5, 4, 1, 1));
        DB.add(new Course("English I", 6, 4, 1, 1));
        DB.add(new Course("Object Oriented Programming", 7, 6, 1, 2));
        DB.add(new Course("Operating Systems", 8, 6, 1, 2));
        DB.add(new Course("Algebraic Foundations of Computer Science", 9, 5, 1, 2));
        DB.add(new Course("Algorithms Design", 10, 5, 1, 2));
        DB.add(new Course("English II", 11, 4, 1, 2));
        DB.add(new Course("Probabilities and Statistics", 12, 4, 1, 2));
    }
    @Override
    public int insertCourse(Course course) {
        DB.add(new Course(course.getTitle(), course.getId(),
                course.getCredits(), course.getYear(), course.getSemester()));
        return 1;
    }

    @Override
    public List<Course> selectAllCourses() {
        return DB;
    }

    @Override
    public Optional<Course> selectCourseById(int id) {
        return DB.stream()
                .filter(course -> course.getId() == id)
                .findFirst();
    }

    @Override
    public void addResource(int id, Resource resource) {
        selectCourseById(id).get().addResource(resource);
    }

    @Override
    public Optional<Resource> getResourceById(Course course, int resourceId) {
        return course.getResources().stream()
                .filter(resource -> resource.getId() == resourceId)
                .findFirst();
    }

    @Override
    public int addApprofundation(int id, Approfundation approfundation) {
        // check if the approfundation already exists
        if (selectCourseById(id).get().getApprofundationList().stream()
                .anyMatch(approfundation1 -> approfundation1.getId() == approfundation.getId())) {
            return 0;
        }
        selectCourseById(id).get().addApprofundation(approfundation);
        return 1;
    }

    @Override
    public List<Approfundation> getApprofundations(Course course) {
        return course.getApprofundationList();
    }

    @Override
    public Optional<Approfundation> getApprofundationById(Course course, int approfundationId) {
        return course.getApprofundationList().stream()
                .filter(approfundation -> approfundation.getId() == approfundationId)
                .findFirst();
    }

    @Override
    public int deleteApprofundationById(int id, int approfundationId) {
        Optional<Approfundation> approfundationMaybe = getApprofundationById(selectCourseById(id).get(), approfundationId);
        if (approfundationMaybe.isEmpty()) {
            return 0;
        }
        selectCourseById(id).get().getApprofundationList().remove(approfundationMaybe.get());
        return 1;
    }

    @Override
    public int updateApprofundationById(int id, int approfundationId, Approfundation approfundation) {
        return getApprofundationById(selectCourseById(id).get(), approfundationId)
                .map(a -> {
                    int indexOfApprofundationToUpdate = selectCourseById(id).get().getApprofundationList().indexOf(a);
                    if (indexOfApprofundationToUpdate >= 0) {
                        selectCourseById(id).get().getApprofundationList().set(indexOfApprofundationToUpdate, approfundation);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
