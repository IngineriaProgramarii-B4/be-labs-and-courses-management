package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeCourseDataAccessService implements CourseDao {
    private static List<Course> DB = new ArrayList<>();

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
}
