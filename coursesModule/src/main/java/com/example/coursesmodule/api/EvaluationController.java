package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.service.ComponentService;
import com.example.coursesmodule.service.EvaluationService;
import com.example.coursesmodule.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/v1/subjects/{subjectTitle}/evaluationMethods")
@CrossOrigin(origins = "*")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final SubjectService subjectService;
    private final ComponentService componentService;
    @Autowired
    public EvaluationController(EvaluationService evaluationService, SubjectService subjectService, ComponentService componentService) {
        this.evaluationService = evaluationService;
        this.subjectService = subjectService;
        this.componentService = componentService;
    }

    @GetMapping
    public List<Evaluation> getEvaluationMethods(@PathVariable("subjectTitle") String title) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        return evaluationService.getEvaluationMethods(title);
    }

    @GetMapping(path = "component={component}")
    public Evaluation getEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                     @PathVariable("component") String component) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, component).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        return evaluationService.getEvaluationMethodByComponent(title, component)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Evaluation method not found"));
    }

    @PostMapping
    public void addEvaluationMethod(@PathVariable("subjectTitle") String title,
                                    @RequestBody Evaluation evaluation) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, evaluation.getComponent()).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(evaluationService.addEvaluationMethod(title, evaluation) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Evaluation method is invalid");
        throw new ResponseStatusException(CREATED, "Evaluation method added successfully");
    }

    @DeleteMapping(path = "component={component}")
    public void deleteEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                  @PathVariable("component") String component) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, component).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(evaluationService.deleteEvaluationMethodByComponent(title, component) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation method not found");
        throw new ResponseStatusException(NO_CONTENT, "Evaluation method deleted successfully");

    }

    @PutMapping(path = "component={component}")
    public void updateEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                  @PathVariable("component") String component,
                                                  @RequestBody Evaluation evaluation) {
        if(subjectService.getSubjectByTitle(title).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        if(componentService.getComponentByType(title, component).isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "Component not found");
        if(evaluationService.updateEvaluationMethodByComponent(title, component, evaluation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Evaluation method not found");
        }
    }
}
