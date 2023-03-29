package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseDao {
    int insertCourse(Course course);

    List<Course> selectAllCourses();

    Optional<Course> selectCourseById(int id);
}
