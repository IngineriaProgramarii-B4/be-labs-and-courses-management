
package com.example.coursesmodule.api;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.service.CourseService;
import com.example.coursesmodule.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/subjects/{subjectId}/course")
public class CourseController {

    private final CourseService courseService;

    private final SubjectService subjectService;

    @Autowired
    public CourseController(CourseService courseService, SubjectService subjectService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
    }

    @PostMapping
    public void addCourse(@PathVariable("subjectId") int subjectId,
                          @Valid @NonNull @RequestBody Course course) {
        //verify that the subject exists
        Subject subject = subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        courseService.addCourse(subjectId, course);
    }

    @GetMapping
    public Course getCourse(@PathVariable("subjectId") int subjectId) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        return course;
    }

    @DeleteMapping
    public void deleteCourse(@PathVariable("subjectId") int subjectId) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        //verify that the course exists
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        courseService.deleteCourse(subjectId);
    }

    @PutMapping
    public void updateCourse(@PathVariable("subjectId") int subjectId,
                             @Valid @NonNull @RequestBody Course course) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        //verify that the course exists
        Course course1 = courseService.getCourse(subjectId);
        if(course1 == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        courseService.updateCourse(subjectId, course);
    }
}
