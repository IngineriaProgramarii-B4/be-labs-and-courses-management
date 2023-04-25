package com.example.controllers;

import com.example.models.Teacher;
import com.example.models.User;
import com.example.services.TeachersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class TeachersController {
    private final TeachersService teachersService;
    @Autowired
    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @Operation(summary = "Get a list of teachers based on 0 or more filters passed as queries. The format is property_from_teacher_schema=value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found teachers that match the requirements",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Teacher.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found teachers that match the requirements",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = "/teachers")
    public List<Teacher> getTeachersByParams(@RequestParam Map<String, Object> params) {
        List<Teacher> teachers = teachersService.getTeachersByParams(params);

        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return teachers;
    }

    @Operation(summary = "Receive necessary data in order to update information about a teacher in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource updated successfully",
                    content = @Content)
    })
    @PutMapping(value = "/teacher")
    public void updateTeacher(@RequestBody Teacher teacher) {
        teachersService.saveTeacher(teacher);
    }
}
