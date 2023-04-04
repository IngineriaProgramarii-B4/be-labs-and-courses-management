package com.example.coursesmodule.api;



import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
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
        if(subjectService.addSubject(subject) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Subject already exists or is invalid");
    }

    @DeleteMapping("subjectId={id}")
    public void deleteSubjectById(@PathVariable("id") int subjectId) {
        if(subjectService.deleteSubjectById(subjectId) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
    }

    @PutMapping("subjectId={id}")
    public void updateSubjectById(@PathVariable("id") int subjectId, @RequestBody Subject subject) {
        //verify that the subject already exists
        subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        if(subjectService.updateSubjectById(subjectId, subject) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Subject is invalid");
    }

    @GetMapping(path = "subjectId={id}")
    public Subject getSubjectById(@PathVariable("id") int id) {
        return subjectService.getSubjectById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
    }

    @GetMapping(path = "year={year}&semester={semester}")
    public List<Subject> getSubjectsByYearAndSemester(@PathVariable("year") int year, @PathVariable("semester") int semester) {
        return subjectService.getSubjectsByYearAndSemester(year, semester);
    }
}
