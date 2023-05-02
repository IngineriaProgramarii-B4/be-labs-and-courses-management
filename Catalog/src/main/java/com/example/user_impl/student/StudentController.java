package com.example.user_impl.student;

import com.example.grades.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/catalog/students")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Student> getStudents(){
        return studentService.getStudentDataBase();
    }
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            throw new NullPointerException();
        }
    }

    @GetMapping("/{id}/{subject}")
    @CrossOrigin(origins = "*")
    public List<Grade> getStudentByIdSubjectGrades(@PathVariable("id") int id, @PathVariable String subject) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            List<Grade> gradesList = student.get().getGradesBySubject(subject);
            if (gradesList.isEmpty()) {
                return List.of();
            }
            return new ResponseEntity<>(gradesList, HttpStatus.OK).getBody();
        }
        else return List.of();
    }
    @GetMapping("/{id}/grades")
    @CrossOrigin(origins = "*")
    public List<Grade> getStudentByIdGrades(@PathVariable("id") int id) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get().getGrades(), HttpStatus.OK).getBody();
        } else {
            throw new NullPointerException();
        }
    }

    @GetMapping("/{id}/grades/{gradeId}")
    @CrossOrigin(origins = "*")
    @Nullable
    public ResponseEntity<Grade> getGradeById(@PathVariable("id") int id, @PathVariable("gradeId") int gradeId) {
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
    @CrossOrigin(origins = "*")
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
    @Nullable
    public ResponseEntity<Grade> addGrade(@PathVariable int id, @RequestBody Grade grade) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            studentService.addGrade(id, grade);
            return new ResponseEntity<>(grade, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @Nullable
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
    @CrossOrigin(origins = "*")
    @Nullable
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
    @CrossOrigin(origins = "*")
    @Nullable
    @PutMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> updateGradeValue(@PathVariable("id") int id, @PathVariable("gradeId") int gradeId,@RequestParam(required = false) String evaluationDate,@RequestParam(required = false) Integer value){
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            Optional<Grade> grade = Optional.ofNullable(studentService.getGradeById(id, gradeId));
            if (grade.isPresent()){
                studentService.updateGrade(id,value,evaluationDate,gradeId);
                return new ResponseEntity<>(grade.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}