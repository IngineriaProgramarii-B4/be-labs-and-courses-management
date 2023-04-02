package com.example.coursesmodule.api;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * COURSE
     */
    @PostMapping
    public void addCourse(@RequestBody Course course) {
        //verify that the course is valid
        if(courseService.verifyCourseValid(course) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Course ID already exists");
        }
        courseService.addCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "courseId={id}")
    public Course getCourseById(@PathVariable("id") int id) {
        return courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
    }
    @GetMapping(path="?year={year}&semester={semester}")
    public List<Course> getCoursesByYearAndSemester(@PathVariable("year") int year, @PathVariable("semester") int semester) {
        return courseService.getCoursesByYearAndSemester(year, semester);
    }

    @DeleteMapping(path = "courseId={id}")
    public void deleteCourseById(@PathVariable("id") int id) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        courseService.deleteCourseById(id);
    }

    @PutMapping(path = "courseId={id}")
    public void updateCourseById(@PathVariable("id") int id, @Valid @NonNull @RequestBody Course course) {
        if(courseService.updateCourseById(id, course) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Course ID already exists");
        }
    }

    /**
     * RESOURCE
     */
    @PostMapping(path = "courseId={id}")
    public void addResource(@PathVariable("id") int id, @Valid @NonNull @RequestBody Resource resource) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource is valid
        if(courseService.verifyResourceValid(resource) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Resource ID already exists");
        }
        courseService.addResource(id, resource);
    }
    @GetMapping(path = "courseId={id}/resourceId={resourceId}")
    public Resource getResource(@PathVariable("id") int id, @PathVariable("resourceId") int resourceId) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        return courseService.getResourceById(course, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }
    @GetMapping(path = "courseId={id}/resources")
    public List<Resource> getResources(@PathVariable("id") int id) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getResources(course);
    }

    @DeleteMapping(path = "courseId={id}/resourceId={resourceId}")
    public void deleteResource(@PathVariable("id") int id, @PathVariable("resourceId") int resourceId) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        Resource resource = courseService.getResourceById(course, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        courseService.deleteResourceById(id, resourceId);
    }

    @PutMapping(path = "courseId={id}/resourceId={resourceId}")
    public void updateResource(@PathVariable("id") int id, @PathVariable("resourceId") int resourceId,
                               @Valid @NonNull @RequestBody Resource resource) {
        //verify that the course exists
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the resource exists
        Resource resource1 = courseService.getResourceById(course, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        //verify that the resource is valid
        if(courseService.verifyResourceValid(resource) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Resource ID already exists");
        }
        courseService.updateResourceById(id, resourceId, resource);
    }

    /**
     * APPROFUNDATION
     */


    /**
     * EVALUATION
     */
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
    }
}
