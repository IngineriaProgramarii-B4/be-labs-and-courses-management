package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.service.EvaluationService;
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
@RequestMapping(path = "api/v1/subjects/{subjectId}/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final SubjectService subjectService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, SubjectService subjectService) {
        this.evaluationService = evaluationService;
        this.subjectService = subjectService;
    }

    @PostMapping
    public void addEvaluationMethod(@PathVariable("subjectId") int subjectId,
                                    @Valid @NonNull @RequestBody Evaluation evaluationMethod) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        //verify if subject doesn't have evaluation already
        if(evaluationService.addEvaluationMethod(subjectId, evaluationMethod) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Subject already has evaluation or evaluation method is not valid");
        }
    }

    @PostMapping("{component}")
    public void addEvaluationComponent(@PathVariable("subjectId") int subjectId,
                                    @Valid @NonNull @RequestBody @PathVariable("component") String component,
                                    float value) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        //verify if subject's evaluation method doesn't have component already
        if(evaluationService.addEvaluationComponent(subjectId, component, value) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Subject already has evaluation or evaluation method is not valid");
        }
    }

    @GetMapping
    public Evaluation getEvaluationMethod(@PathVariable("subjectId") int subjectId) {
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        return evaluationService.getEvaluationMethod(subjectService.getSubjectById(subjectId).get());
    }

    @GetMapping("{component}")
    public List<Object> getEvaluationComponent(@PathVariable("subjectId") int subjectId,
                                               @Valid @NonNull @RequestBody @PathVariable("component") String component) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));

        List<Object> evaluationComponent = evaluationService.getEvaluationComponent(
                subjectService.getSubjectById(subjectId).get(), component);
        //verify if evaluation component exists
        if (evaluationComponent == null)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation component not found");
        return evaluationComponent;
    }

    @DeleteMapping
    public void deleteEvaluationMethod(@PathVariable("subjectId") int subjectId) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        //verify that the course evaluation exists
        if (evaluationService.deleteEvaluationMethod(subjectId) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation not found");
    }

    @DeleteMapping("{component}")
    public void deleteEvaluationComponent(@PathVariable("subjectId") int subjectId,
                                          @Valid @NonNull @RequestBody @PathVariable("component") String component) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        //verify if subject's evaluation method has component
        if (evaluationService.deleteEvaluationComponent(subjectId, component) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation component not found");
    }

    @PutMapping
    public void updateEvaluationMethod(@PathVariable("subjectId") int subjectId,
                                       @Valid @NonNull @RequestBody Evaluation evaluationMethod) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the evaluation method is valid
        if (evaluationService.updateEvaluationMethod(subjectId, evaluationMethod) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Evaluation method is not valid");
    }

    @PutMapping("{component}")
    public void updateEvaluationComponent(@PathVariable("subjectId") int subjectId,
                                          @Valid @NonNull @RequestBody @PathVariable("component") String component,
                                          float value) {
        //verify that the subject exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found"));
        //verify that the subject's evaluation method has component
        if (evaluationService.updateEvaluationComponent(subjectId, component, value) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Evaluation component not found");
    }
}
