package com.example.controllers;

import com.example.models.Reminder;
import com.example.models.Student;
import com.example.services.RemindersService;
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
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class RemindersController {
    private final RemindersService remindersService;

    @Autowired
    public RemindersController(RemindersService remindersService) {
        this.remindersService = remindersService;
    }

    @Operation(summary = "Get a specific reminder of a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reminder.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found the reminder.",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = {"/reminders/{username}/{id}"})
    public List<Reminder> getRemindersByParams(@PathVariable String username, @PathVariable UUID id) {
        List<Reminder> reminders = remindersService.getRemindersByParams(Map.of("creatorUsername", username, "id", id));

        if(reminders.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reminders;
    }

    @Operation(summary = "Get a list of reminders of a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found reminders",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found reminders",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = {"/reminders/{username}"})
    public List<Reminder> getRemindersOfLoggedUser(@PathVariable String username) {
        Map<String, Object> params = Map.of("creatorUsername", username);

        List<Reminder> reminders = remindersService.getRemindersByParams(params);
        System.out.println(reminders);
        if(reminders.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reminders;
    }

    @Operation(summary = "Receive necessary data in order to add a new reminder in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource added successfully.",
                    content = @Content)
    })
    @PostMapping(value = "/reminders")
    public void updateReminder(@RequestBody Reminder reminder) {
        remindersService.saveReminder(reminder);
    }
}
