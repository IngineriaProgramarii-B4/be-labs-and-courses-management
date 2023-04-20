package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subjects/{subjectTitle}/components")
public class ComponentController {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public List<Component> getComponents(@PathVariable("subjectTitle") String title) {
        return componentService.getComponents(title);
    }

    @PostMapping
    public void addComponent(@PathVariable("subjectTitle") String title, @RequestBody Component component) {
        if(componentService.addComponent(title, component) == 0) {
            throw new IllegalStateException("Invalid title given");
        }
    }

    @GetMapping(path = "type={type}")
    public Component getComponentByType(@PathVariable("subjectTitle") String title, @PathVariable("type") String type) {
        return componentService.getComponentByType(title, type)
                .orElseThrow(() -> new IllegalStateException("Component not found"));
    }

    @DeleteMapping(path = "type={type}")
    public void deleteComponentByType(@PathVariable("subjectTitle") String title, @PathVariable("type") String type) {
        if(componentService.deleteComponentByType(title, type) == 0) {
            throw new IllegalStateException("Component not found");
        }
    }

    @PutMapping(path = "type={type}")
    public void updateComponentByType(@PathVariable("subjectTitle") String title,
                                      @PathVariable("type") String type,
                                      @RequestBody Component component) {
        if(componentService.updateComponentByType(title, type, component) == 0) {
            throw new IllegalStateException("Component not found");
        }
    }
}
