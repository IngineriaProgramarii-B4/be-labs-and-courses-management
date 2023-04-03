package com.example.coursesmodule.api;

import com.example.coursesmodule.model.Subject;
import com.example.coursesmodule.service.SubjectService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void addSubject(@RequestBody Subject subject) {
        subjectService.addSubject(subject);
    }

    @DeleteMapping("subjectId={id}")
    public void deleteSubjectById(@RequestParam("subjectId") int subjectId) {
        subjectService.deleteSubjectById(subjectId);
    }

    @PutMapping("subjectId={id}")
    public void updateSubjectById(@RequestParam("subjectId") int subjectId, @RequestBody Subject subject) {
        subjectService.updateSubjectById(subjectId, subject);
    }

    @GetMapping(path = "subjectId={id}")
    public Subject getSubjectById(@PathVariable("id") int id) {
        return subjectService.getSubjectById(id).orElse(null);
    }

    @GetMapping(path = "?year={year}&semester={semester}")
    public List<Subject> getSubjectsByYearAndSemester(@PathVariable("year") int year, @PathVariable("semester") int semester) {
        return subjectService.getSubjectsByYearAndSemester(year, semester);
    }
}
