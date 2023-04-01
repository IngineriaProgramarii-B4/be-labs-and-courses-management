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
    @PostMapping(path = "courseId={id}/seminars")
    public void addApprofundation(@PathVariable("id") int id,@Valid @NonNull @RequestBody Seminar seminar) {
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.addApprofundation(id, seminar) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @PostMapping(path = "courseId={id}/laboratories")
    public void addApprofundation(@PathVariable("id") int id, @Valid @NonNull @RequestBody Laboratory laboratory) {
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.addApprofundation(id, laboratory) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @GetMapping(path = "courseId={id}/approfundations")
    public List<Approfundation> getApprofundations(@PathVariable("id") int id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getApprofundations(course);
    }

    @GetMapping(path = "courseId={id}/approfundationId={approfundationId}")
    public Approfundation getApprofundationById(@PathVariable("id") int id,
                                            @PathVariable("approfundationId") int approfundationId) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getApprofundationById(course, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
    }

    @DeleteMapping(path = "courseId={id}/approfundationId={approfundationId}")
    public void deleteApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationId") int approfundationId) {
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.deleteApprofundationById(id, approfundationId) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }

    @PutMapping(path = "courseId={id}/approfundationId={approfundationId}/seminars")
    public void updateApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationId") int approfundationId,
                                         @Valid @NonNull @RequestBody Seminar approfundation) {
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.updateApprofundationById(id, approfundationId, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }

    @PutMapping(path = "courseId={id}/approfundationId={approfundationId}/laboratories")
    public void updateApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationId") int approfundationId,
                                         @Valid @NonNull @RequestBody Laboratory approfundation) {
        courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.updateApprofundationById(id, approfundationId, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }
}
