package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Approfundation;
import com.example.coursesmodule.model.Course;
import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.ApprofundationResourceService;
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
@RequestMapping(path = "api/v1/subjects/{id}/approfundations/{approfundationId}/resources")
public class ApprofundationResourceController {

    private final SubjectService subjectService;
    private final ApprofundationService approfundationService;
    private final ApprofundationResourceService approfundationResourceService;

    @Autowired
    public ApprofundationResourceController(SubjectService subjectService, ApprofundationService approfundationService,
                                            ApprofundationResourceService approfundationResourceService) {
        this.approfundationService = approfundationService;
        this.approfundationResourceService = approfundationResourceService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Resource> getApprofundationResources(@PathVariable("id") int id,
                                                     @PathVariable("approfundationId") int approfundationId) {
        if(subjectService.getSubjectById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Approfundation approfundation = approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
        return approfundationResourceService.getResourcesForAprofundationId(id, approfundationId);
    }


    @GetMapping(path = "resourceId={resourceId}")
    public Resource getApprofundationResourceById(@PathVariable("id") int id,
                                                  @PathVariable("approfundationId") int approfundationId,
                                                  @PathVariable("resourceId") int resourceId) {
        if(subjectService.getSubjectById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Approfundation approfundation = approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
        return approfundationResourceService.getResourceByIdForApprofundationId(id, approfundationId, resourceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @PostMapping
    public void addApprofundationResource(@PathVariable("id") int id,
                                          @PathVariable("approfundationId") int approfundationId,
                                          @Valid @NonNull @RequestBody Resource resource) {
        if(subjectService.getSubjectById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Approfundation approfundation = approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
        if(approfundationResourceService.addResourceForApprofundationId(id, approfundationId, resource) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Resource not found");
        }
    }

    @PutMapping(path = "resourceId={resourceId}")
    public void updateApprofundationResourceById(@PathVariable("id") int id,
                                                 @PathVariable("approfundationId") int approfundationId,
                                                 @PathVariable("resourceId") int resourceId,
                                                 @Valid @NonNull @RequestBody Resource resource) {
        if(subjectService.getSubjectById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Approfundation approfundation = approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
        if(approfundationResourceService
                .updateResourceForApprofundationId(id, approfundationId, resourceId, resource) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Resource not found");
        }
    }

    @DeleteMapping(path = "resourceId={resourceId}")
    public void deleteApprofundationResourceById(@PathVariable("id") int id,
                                                 @PathVariable("approfundationId") int approfundationId,
                                                 @PathVariable("resourceId") int resourceId) {
        if(subjectService.getSubjectById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        }
        Approfundation approfundation = approfundationService.getApprofundationById(id, approfundationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Approfundation not found"));
        if(approfundationResourceService.deleteResourceForApprofundationId(id, approfundationId, resourceId) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Resource not found");
        }
    }
}
