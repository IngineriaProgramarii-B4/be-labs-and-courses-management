package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/v1/subjects")
@CrossOrigin(origins = "*", allowedHeaders = "",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
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
        throw new ResponseStatusException(CREATED, "Subject added successfully");
    }

    @DeleteMapping("subjectTitle={title}")
    public void deleteSubjectByTitle(@PathVariable("title") String title) {
        if(subjectService.deleteSubjectByTitle(title) == 0)
            throw new ResponseStatusException(NOT_FOUND, "Subject not found");
        throw new ResponseStatusException(NO_CONTENT, "Subject deleted successfully");
    }

    @PutMapping("subjectTitle={title}")
    public void updateSubjectByTitle(@PathVariable("title") String title, @RequestBody Subject subject) {
        subjectService.getSubjectByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
        if(subjectService.updateSubjectByTitle(title, subject) == 0)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Subject is invalid");
    }

    @GetMapping(path = "subjectTitle={title}")
    public Subject getSubjectByTitle(@PathVariable("title") String title) {
        return subjectService.getSubjectByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subject not found"));
    }

    @GetMapping(path = "year={year}&semester={semester}")
    public List<Subject> getSubjectsByYearAndSemester(@PathVariable("year") int year, @PathVariable("semester") int semester) {
        return subjectService.getSubjectsByYearAndSemester(year, semester);
    }
}
