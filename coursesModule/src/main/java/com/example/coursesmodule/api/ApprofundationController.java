package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.service.ApprofundationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/subjects")
public class ApprofundationController {

    private final ApprofundationService approfundationService;

    @Autowired
    public ApprofundationController(ApprofundationService approfundationService) {
        this.approfundationService = approfundationService;
    }

    @PostMapping(path = "subjectId={id}/approfundations")
    public void addApprofundation(@PathVariable("id") int id, @Valid @NonNull @RequestBody Approfundation approfundation) {
 /*       approfundationService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));*/
        if(approfundationService.addApprofundation(id, approfundation) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @GetMapping(path = "subjectId={id}/approfundations")
    public List<Approfundation> getApprofundations(@PathVariable("id") int id) {
        /*Course course = approfundationService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));*/
        /*return approfundationService.getApprofundations(course);*/
        return null;
    }

    @GetMapping(path = "subjectId={id}/approfundationId={approfundationId}")
    public Approfundation getApprofundationById(@PathVariable("id") int id,
                                                @PathVariable("approfundationId") int approfundationId) {
       /* Course course = approfundationService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return approfundationService.getApprofundationById(course, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));*/
        return null;
    }

    @DeleteMapping(path = "subjectId={id}/approfundationId={approfundationId}")
    public void deleteApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationId") int approfundationId) {
        /*approfundationService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(approfundationService.deleteApprofundationById(id, approfundationId) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }*/
    }

    @PutMapping(path = "subjectId={id}/approfundationId={approfundationId}")
    public void updateApprofundationById(@PathVariable("id") int id,
                                         @PathVariable("approfundationId") int approfundationId,
                                         @Valid @NonNull @RequestBody Approfundation approfundation) {
        /*approfundationService.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(approfundationService.updateApprofundationById(id, approfundationId, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }*/
    }
}
