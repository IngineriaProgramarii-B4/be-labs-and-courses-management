package com.example.coursesmodule.api;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.service.CourseService;
import com.example.coursesmodule.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
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

    /**
     * COURSE
     */
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
        //verify that the course exists
        return courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
    }

    @DeleteMapping
    public void deleteCourse(@PathVariable("subjectId") int subjectId) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        courseService.deleteCourse(subjectId);
    }

    @PutMapping
    public void updateCourse(@PathVariable("subjectId") int subjectId,
                             @Valid @NonNull @RequestBody Course course) {
        //verify that the course exists
        Course course1 = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        courseService.updateCourse(subjectId, course);
    }

    /**
     *  COURSE'S RESOURCES
     */

    @PostMapping(path = "resources")
    public void addResource(@PathVariable("subjectId") int subjectId,
                            @Valid @NonNull @RequestBody Resource resource) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource is valid
        if(courseService.verifyResourceValid(resource) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Resource ID already exists");
        }
        courseService.addCourseResource(subjectId, resource);
    }

    @GetMapping(path = "resources/{resourceId}")
    public Resource getCourseResourceById(@PathVariable("subjectId") int subjectId,
                                          @PathVariable("resourceId") int resourceId) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        return courseService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @GetMapping(path = "resources")
    public List<Resource> getCourseResources(@PathVariable("subjectId") int subjectId) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getCourseResources(subjectId);
    }

    @DeleteMapping(path = "resources/{resourceId}")
    public void deleteCourseResourceById(@PathVariable("subjectId") int subjectId,
                                         @PathVariable("resourceId") int resourceId) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        Resource resource = courseService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        courseService.deleteCourseResourceById(subjectId, resourceId);
    }

    @PutMapping(path = "resources/{resourceId}")
    public void updateCourseResourceById(@PathVariable("subjectId") int subjectId,
                                         @PathVariable("resourceId") int resourceId,
                                         @Valid @NonNull @RequestBody Resource resource) {
        //verify that the course exists
        Course course = courseService.getCourse(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        Resource resource1 = courseService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        courseService.updateCourseResourceById(subjectId, resourceId, resource);
    }


    /*************************************
      EVALUATION*/
     /*
    @PostMapping(path = "courseId={id}/evaluation")
    public void addEvaluationMethod(@PathVariable("id") int id,
                                    @Valid @NonNull @RequestBody Evaluation evaluationMethod) {
        //verify that the course exists
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify if course doesn't have evaluation already
        if(courseService.addEvaluationMethod(id, evaluationMethod) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Course already has Evaluation");
        }
    }

    @GetMapping(path = "courseId={id}/evaluation")
    public Evaluation getEvaluationMethod(@PathVariable("id") int id) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return course.getEvaluationMethod();
    }

    @GetMapping(path = "courseId={id}/evaluation/component={component}")
    public List<Object> getEvaluationComponent(@PathVariable("id") int id,
                                               @PathVariable("component") Object component) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the course evaluation component
        if (courseService.getEvaluationComponent(course, component) == null)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation component not found");
        return courseService.getEvaluationComponent(course, component);
    }

    @DeleteMapping(path = "courseId={id}/evaluation")
    public void deleteEvaluationMethod(@PathVariable("id") int id) {
        //verify that the course exists
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the course evaluation exists
        if (courseService.deleteEvaluationMethod(id) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation not found");
    }

    @PutMapping(path = "courseId={id}/evaluation")
    public void updateEvaluationMethod(@PathVariable("id") int id,
                                       @Valid @NonNull @RequestBody Evaluation evaluationMethod) {
        //verify that the course exists
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        courseService.updateEvaluationMethod(id, evaluationMethod);
    }*/
}
