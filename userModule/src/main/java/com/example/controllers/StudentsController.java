package com.example.controllers;

import com.example.models.Student;
import com.example.services.StudentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class StudentsController {
    private final StudentsService studentsService;

    @Autowired
    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @Operation(summary = "Get a list of students based on 0 or more filters passed as queries. The format is property_from_student_schema=value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found students that match the requirements",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found students that match the requirements",
                    content = @Content
            )
    })
    @GetMapping(value = {"/students"})
    public ResponseEntity<List<Student>> getStudentsByParams(@RequestParam Map<String, Object> params) {
        List<Student> students = studentsService.getStudentsByParams(params);

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "Receive necessary data in order to update information about a student in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resource updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Haven't found students that match the requirements",
                    content = @Content
            )
    })
    @PatchMapping(value = "/student/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        if (!studentsService.getStudentsByParams(Map.of("id", id)).isEmpty()) {
            studentsService.updateStudent(id, student);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Receive necessary data in order to add a new student in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource added successfully",
                    content = @Content)
    })
    @PostMapping(value = "/students")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        studentsService.saveStudent(student);
        return new ResponseEntity<>("Resource added successfully", HttpStatus.CREATED);
    }
}
