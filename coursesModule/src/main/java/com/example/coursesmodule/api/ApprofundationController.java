package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.service.ApprofundationService;
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
@RequestMapping(path = "api/v1/subjects/{subjectId}/approfundations")
public class ApprofundationController {

    private final ApprofundationService approfundationService;

    private final SubjectService subjectService;

    @Autowired
    public ApprofundationController(ApprofundationService approfundationService, SubjectService subjectService) {
        this.approfundationService = approfundationService;
        this.subjectService = subjectService;
    }

    @PostMapping
    public void addApprofundation(@PathVariable("subjectId") int id, @Valid @NonNull @RequestBody Approfundation approfundation) {
        subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(approfundationService.addApprofundation(id, approfundation) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Approfundation ID already exists");
        }
    }

    @GetMapping
    public List<Approfundation> getApprofundations(@PathVariable("subjectId") int id) {
        subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return approfundationService.getApprofundations(id);
    }

    @GetMapping(path = "approfundationId={approfundationId}")
    public Approfundation getApprofundationById(@PathVariable("subjectId") int id,
                                                @PathVariable("approfundationId") int approfundationId) {
        subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        return approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
    }

    @DeleteMapping(path = "approfundationId={approfundationId}")
    public void deleteApprofundationById(@PathVariable("subjectId") int id,
                                         @PathVariable("approfundationId") int approfundationId) {
        subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(approfundationService.deleteApprofundationById(id, approfundationId) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }

    @PutMapping(path = "approfundationId={approfundationId}")
    public void updateApprofundationById(@PathVariable("subjectId") int id,
                                         @PathVariable("approfundationId") int approfundationId,
                                         @Valid @NonNull @RequestBody Approfundation approfundation) {
        subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        if(approfundationService.updateApprofundationById(id, approfundationId, approfundation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Approfundation not found");
        }
    }
}



