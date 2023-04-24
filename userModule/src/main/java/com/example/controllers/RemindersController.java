package com.example.controllers;

import com.example.models.Reminder;
import com.example.services.RemindersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class RemindersController {
    private final RemindersService remindersService;

    @Autowired
    public RemindersController(RemindersService remindersService) {
        this.remindersService = remindersService;
    }

    @GetMapping(value = {"/reminders"})
    public List<Reminder> getRemindersByParams(@RequestParam Map<String, Object> params) {
        List<Reminder> reminders = remindersService.getRemindersByParams(params);

        if(reminders.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reminders;
    }

    @GetMapping(value = {"/logged/reminders"})
    public List<Reminder> getRemindersOfLoggedUser() {
        Map<String, Object> params = Map.of("creatorUsername", (Object) "loggeduser");

        List<Reminder> reminders = remindersService.getRemindersByParams(params);
        if(reminders.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reminders;
    }

    @PutMapping(value = "/reminder")
    public void updateReminder(@RequestBody Reminder reminder) {
        remindersService.saveReminder(reminder);
    }
}
