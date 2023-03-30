package com.example.coursesmodule.api;

import com.example.coursesmodule.model.*;
import com.example.coursesmodule.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "courseid={id}/seminar")
    public void addApprofundation(@PathVariable("id") int id, @RequestBody Seminar seminar) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.addApprofundation(id, seminar) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @PostMapping(path = "courseid={id}/laboratory")
    public void addApprofundation(@PathVariable("id") int id, @RequestBody Laboratory laboratory) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.addApprofundation(id, laboratory) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @GetMapping(path = "courseid={id}/approfundations")
    public List<Approfundation> getApprofundations(@PathVariable("id") int id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getApprofundations(course);
    }

    @GetMapping(path = "courseid={id}/approfundationid={approfundationid}")
    public Approfundation getApprofundationById(@PathVariable("id") int id,
                                            @PathVariable("approfundationid") int approfundationid) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return courseService.getApprofundationById(course, approfundationid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
    }

    @DeleteMapping(path = "courseid={id}/approfundationid={approfundationid}")
    public void deleteApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationid") int approfundationid) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.deleteApprofundationById(id, approfundationid) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }

    @PutMapping(path = "courseid={id}/approfundationid={approfundationid}/seminar")
    public void updateApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationid") int approfundationid,
                                         @RequestBody Seminar approfundation) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.updateApprofundationById(id, approfundationid, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }

    @PutMapping(path = "courseid={id}/approfundationid={approfundationid}/laboratory")
    public void updateApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationid") int approfundationid,
                                         @RequestBody Laboratory approfundation) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(courseService.updateApprofundationById(id, approfundationid, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }
}
