package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Evaluation;
import com.example.coursesmodule.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/subjects/{subjectTitle}/evaluationMethods")
@CrossOrigin(origins = "*", allowedHeaders = "",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public List<Evaluation> getEvaluationMethods(@PathVariable("subjectTitle") String title) {
        return evaluationService.getEvaluationMethods(title);
    }

    @GetMapping(path = "component={component}")
    public Evaluation getEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                     @PathVariable("component") String component) {
        return evaluationService.getEvaluationMethodByComponent(title, component)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Evaluation method not found"));
    }

    @PostMapping
    public void addEvaluationMethod(@PathVariable("subjectTitle") String title,
                                    @RequestBody Evaluation evaluation) {
        if(evaluationService.addEvaluationMethod(title, evaluation) == 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Invalid title given");
        }
    }

    @DeleteMapping(path = "component={component}")
    public void deleteEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                  @PathVariable("component") String component) {
        if(evaluationService.deleteEvaluationMethodByComponent(title, component) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Evaluation method not found");
        }
    }

    @PutMapping(path = "component={component}")
    public void updateEvaluationMethodByComponent(@PathVariable("subjectTitle") String title,
                                                  @PathVariable("component") String component,
                                                  @RequestBody Evaluation evaluation) {
        if(evaluationService.updateEvaluationMethodByComponent(title, component, evaluation) == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Evaluation method not found");
        }
    }
}
