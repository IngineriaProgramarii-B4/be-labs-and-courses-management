package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.service.ComponentService;
import com.example.coursesmodule.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/subjects/{subjectTitle}/components")
@CrossOrigin(origins = "*")
public class ComponentController {

    private final ComponentService componentService;
    private final SubjectService subjectService;
    @Autowired
    public ComponentController(ComponentService componentService, SubjectService subjectService){
        this.componentService = componentService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Component> getComponents(@PathVariable("subjectTitle") String title) {
        return componentService.getComponents(title);
    }

    @PostMapping
    public void addComponent(@PathVariable("subjectTitle") String title, @RequestBody Component component) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");

        if(componentService.addComponent(title, component) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Component is invalid");
        throw new ResponseStatusException(CREATED, "Component added successfully");
    }

    @GetMapping(path = "type={type}")
    public Component getComponentByType(@PathVariable("subjectTitle") String title, @PathVariable("type") String type) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");

        return componentService.getComponentByType(title, type)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Component not found"));
    }

    @DeleteMapping(path = "type={type}")
    public void deleteComponentByType(@PathVariable("subjectTitle") String title, @PathVariable("type") String type) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");

        if(componentService.deleteComponentByType(title, type) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        throw new ResponseStatusException(NO_CONTENT, "Component deleted successfully");
    }

    @PutMapping(path = "type={type}")
    public void updateComponentByType(@PathVariable("subjectTitle") String title,
                                      @PathVariable("type") String type,
                                      @RequestBody Component component) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.updateComponentByType(title, type, component) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        }
    }
}
