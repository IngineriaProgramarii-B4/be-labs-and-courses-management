package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.service.CourseResourceService;
import com.example.coursesmodule.service.CourseService;
import com.example.coursesmodule.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/subjects/{subjectId}/course/resources")
public class CourseResourceController {

    private final CourseService courseService;
    private final SubjectService subjectService;
    private final CourseResourceService courseResourceService;

    @Autowired
    public CourseResourceController(CourseService courseService, SubjectService subjectService, CourseResourceService courseResourceService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
        this.courseResourceService = courseResourceService;
    }

    @PostMapping
    public void addResource(@PathVariable("subjectId") int subjectId,
                            @Valid @NonNull @RequestBody Resource resource) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        if(courseResourceService.addCourseResource(subjectId, resource) == 0){
            throw new ResponseStatusException(NOT_FOUND, "Resource not added");
        }
    }

    @GetMapping(path = "{resourceId}")
    public Resource getCourseResourceById(@PathVariable("subjectId") int subjectId,
                                          @PathVariable("resourceId") int resourceId) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        return courseResourceService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @GetMapping
    public List<Resource> getCourseResources(@PathVariable("subjectId") int subjectId) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        return courseResourceService.getCourseResources(subjectId);
    }

    @DeleteMapping(path = "{resourceId}")
    public void deleteCourseResourceById(@PathVariable("subjectId") int subjectId,
                                         @PathVariable("resourceId") int resourceId) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        //verify that the resource exists
        Resource resource = courseResourceService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        if(courseResourceService.deleteCourseResourceById(subjectId, resourceId) == 0){
            throw new ResponseStatusException(NOT_FOUND, "Resource not deleted");
        }
    }

    @PutMapping(path = "{resourceId}")
    public void updateCourseResourceById(@PathVariable("subjectId") int subjectId,
                                         @PathVariable("resourceId") int resourceId,
                                         @Valid @NonNull @RequestBody Resource resource) {
        if(subjectService.getSubjectById(subjectId).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Course course = courseService.getCourse(subjectId);
        if(course == null) {
            throw new ResponseStatusException(NOT_FOUND, "Course not found");
        }
        //verify that the resource exists
        Resource resource1 = courseResourceService.getCourseResourceById(subjectId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        if(courseResourceService.updateCourseResourceById(subjectId, resourceId, resource) == 0){
            throw new ResponseStatusException(NOT_FOUND, "Resource not updated");
        }
    }
}
