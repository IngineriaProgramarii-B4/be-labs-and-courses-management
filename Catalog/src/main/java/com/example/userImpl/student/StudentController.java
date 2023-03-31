package com.example.userImpl.student;

import com.example.grades.Grade;
import com.example.subject.Subject;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/catalog/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudentDataBase();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            throw new NullPointerException();
        }
    }

    @GetMapping("/{id}/{subject}")
    public List<Grade> getStudentByIdSubjectGrades(@PathVariable("id") int id, @PathVariable String subject) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            List<Grade> gradesList = student.get().getGradesBySubject(subject);
            if (gradesList.isEmpty()) {
                return null;
            }
            return new ResponseEntity<>(gradesList, HttpStatus.OK).getBody();
        }
        else return null;
    }
    @GetMapping("/{id}/grades")
    public List<Grade> getStudentByIdGrades(@PathVariable("id") int id) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get().getGrades(), HttpStatus.OK).getBody();
        } else {
            throw new NullPointerException();
        }
    }
    @GetMapping("/{id}/grades/{gradeId}")
    private ResponseEntity<Grade> getGradeById(@PathVariable("id") int id, @PathVariable("gradeId") int gradeId) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            Optional<Grade> grade = Optional.ofNullable(studentService.getGradeById(id, gradeId));
            if (grade.isPresent()){
                return new ResponseEntity<>(grade.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // salveaza un student in baza de date fictiva
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> create(@RequestBody Student newStudent) throws ServerException {

        Student studentToSave = studentService.save(newStudent);
        if (studentToSave == null) {
            throw new ServerException("Cannot save.");
        } else {
            return new ResponseEntity<>(studentToSave, HttpStatus.CREATED);
        }
    }
    @PostMapping(path = "/{id}/grades",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> addGrade(@PathVariable int id, @RequestBody Grade grade) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            studentService.addGrade(id, grade);
            return new ResponseEntity<>(grade, HttpStatus.OK);
        } else {
            return null;
        }
    }
    @DeleteMapping(value = "/{id}/grades/{gradeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> deleteGrade(@PathVariable int id, @PathVariable int gradeId) {
        Grade isRemoved = studentService.deleteGrade(id,gradeId);
        if (isRemoved == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(isRemoved, HttpStatus.OK);
    }
    // sterge in mod fizic un student din baza de date
    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> delete(@PathVariable("id") int id){
        Student isRemoved = studentService.delete(getStudentById(id).getBody());
        if (isRemoved == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(isRemoved, HttpStatus.OK);
    }

    //Modifica valoarea unei note si/sau data de notare
    @PutMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> updateGradeValue(@PathVariable("id") int id, @PathVariable("gradeId") int gradeId,@RequestParam(required = false) String evaluationDate,@RequestParam(required = false) Integer value){
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            Optional<Grade> grade = Optional.ofNullable(studentService.getGradeById(id, gradeId));
            if (grade.isPresent()){
               Grade updatedGrade= studentService.updateGrade(id,value,evaluationDate,gradeId);
                return new ResponseEntity<>(grade.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}


    /* TODO:
    *   poate exista o modalitate mai simpla de a detecta care informatii au fost actually updated, pentru a nu face operatii inutile
    *   poate ar trebui un putmapping si pentru note, cumva trebuie sa poti updata doar o nota -> gradeController?
    */
//    @PutMapping("/{id}")
//    public ResponseEntity<Student> update(@PathVariable("id") int id, @RequestBody Student studentDetails){
//        Student updateStudent = studentService.getStudentById(id);
//        if (updateStudent == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        else {
//            updateStudent.setEmail(studentDetails.getEmail());
//            updateStudent.setName(studentDetails.getName());
//            updateStudent.setGrades(studentDetails.getGrades());
//        }
//    }

