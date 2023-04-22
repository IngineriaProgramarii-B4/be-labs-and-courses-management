package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Resource;
import com.example.coursesmodule.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subjects/{subjectTitle}/components/{componentType}/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<Resource> getResources(@PathVariable("subjectTitle") String title,
                                       @PathVariable("componentType") String type) {
        return resourceService.getResources(title, type);
    }

    @GetMapping(path = "title={resourceTitle}")
    public Resource getResourceByTitle(@PathVariable("subjectTitle") String title,
                                       @PathVariable("componentType") String type,
                                       @PathVariable("resourceTitle") String resourceTitle) {
        return resourceService.getResourceByTitle(title, type, resourceTitle)
                .orElseThrow(() -> new IllegalStateException("Resource not found"));
    }

    @PostMapping
    public void addResource(@PathVariable("subjectTitle") String title,
                            @PathVariable("componentType") String type,
                            @RequestBody Resource resource) {
        if(resourceService.addResource(title, type, resource) == 0) {
            throw new IllegalStateException("Invalid title given");
        }
    }

    @DeleteMapping(path = "title={resourceTitle}")
    public void deleteResourceByTitle(@PathVariable("subjectTitle") String title,
                                      @PathVariable("componentType") String type,
                                      @PathVariable("resourceTitle") String resourceTitle) {
        if(resourceService.deleteResourceByTitle(title, type, resourceTitle) == 0) {
            throw new IllegalStateException("Resource not found");
        }
    }

    @PutMapping(path = "title={resourceTitle}")
    public void updateResourceByTitle(@PathVariable("subjectTitle") String title,
                                      @PathVariable("componentType") String type,
                                      @PathVariable("resourceTitle") String resourceTitle,
                                      @RequestBody Resource resource) {
        if(resourceService.updateResourceByTitle(title, type, resourceTitle, resource) == 0) {
            throw new IllegalStateException("Resource not found");
        }
    }
}
