package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.SubjectService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @PostMapping
    public void addSubject(@RequestBody Subject subject)
    {
        //check if subject already exists
        if (subjectService.getSubjectById(subject.getId()).isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, "Subject already exists");
        }
        subjectService.getSubjectById(subject.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        subjectService.addSubject(subject);
    }

    @DeleteMapping("subjectId={id}")
    public void deleteSubjectById(@PathVariable("id") int subjectId) {
        subjectService.deleteSubjectById(subjectId);
    }

    @PutMapping("subjectId={id}")
    public void updateSubjectById(@PathVariable("id") int subjectId, @RequestBody Subject subject) {
        //verify that the subject already exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        subjectService.updateSubjectById(subjectId, subject);
    }

    @GetMapping(path = "subjectId={id}")
    public Subject getSubjectById(@PathVariable("id") int id) {
        return subjectService.getSubjectById(id).orElse(null);
    }

    @GetMapping(path = "/year={year}&semester={semester}")
    public List<Subject> getSubjectsByYearAndSemester(@PathVariable("year") int year, @PathVariable("semester") int semester) {
        return subjectService.getSubjectsByYearAndSemester(year, semester);
    }
}
