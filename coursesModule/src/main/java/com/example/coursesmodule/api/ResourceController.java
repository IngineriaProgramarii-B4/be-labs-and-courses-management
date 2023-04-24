package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.service.ComponentService;
import com.example.coursesmodule.service.ResourceService;
import com.example.coursesmodule.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/subjects/{subjectTitle}/components/{componentType}/resources")
@CrossOrigin(origins = "*", allowedHeaders = "",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class ResourceController {
    private final ResourceService resourceService;
    private final SubjectService subjectService;
    private final ComponentService componentService;
    @Autowired
    public ResourceController(ResourceService resourceService, SubjectService subjectService, ComponentService componentService) {
        this.resourceService = resourceService;
        this.subjectService = subjectService;
        this.componentService = componentService;
    }

    @GetMapping
    public List<Resource> getResources(@PathVariable("subjectTitle") String title,
                                       @PathVariable("componentType") String type) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, type).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        return resourceService.getResources(title, type);
    }

    @GetMapping(path = "title={resourceTitle}")
    public Resource getResourceByTitle(@PathVariable("subjectTitle") String title,
                                       @PathVariable("componentType") String type,
                                       @PathVariable("resourceTitle") String resourceTitle) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, type).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        return resourceService.getResourceByTitle(title, type, resourceTitle)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @PostMapping
    public void addResource(@PathVariable("subjectTitle") String title,
                            @PathVariable("componentType") String type,
                            @RequestBody Resource resource) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, type).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(resourceService.addResource(title, type, resource) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        throw new ResponseStatusException(CREATED, "Resource created successfully");
    }

    @DeleteMapping(path = "title={resourceTitle}")
    public void deleteResourceByTitle(@PathVariable("subjectTitle") String title,
                                      @PathVariable("componentType") String type,
                                      @PathVariable("resourceTitle") String resourceTitle) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, type).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(resourceService.deleteResourceByTitle(title, type, resourceTitle) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Resource not found");
        throw new ResponseStatusException(NO_CONTENT, "Resource deleted successfully");
    }

    @PutMapping(path = "title={resourceTitle}")
    public void updateResourceByTitle(@PathVariable("subjectTitle") String title,
                                      @PathVariable("componentType") String type,
                                      @PathVariable("resourceTitle") String resourceTitle,
                                      @RequestBody Resource resource) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, type).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(resourceService.updateResourceByTitle(title, type, resourceTitle, resource) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Resource not found");
        }
    }
}
