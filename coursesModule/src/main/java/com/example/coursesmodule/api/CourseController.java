package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "courseid={id}")
    public Course getCourseById(@PathVariable("id") int id) {
        return courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
    }
    @PostMapping(path = "courseid={id}")
    public void addResource(@PathVariable("id") int id, @RequestBody Resource resource) {
        courseService.addResource(id, resource);
    }
    @GetMapping(path = "courseid={id}/resourceid={resourceid}")
    public Resource getResource(@PathVariable("id") int id, @PathVariable("resourceid") int resourceid) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getResourceById(course, resourceid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));

    }
}
