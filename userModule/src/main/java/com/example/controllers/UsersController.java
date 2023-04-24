package com.example.controllers;

import com.example.models.User;
import com.example.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = "/users")
    public List<User> getUsersByParams(@RequestParam Map<String, Object> params) {
        List<User> users = usersService.getUsersByParams(params);

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users;
    }
}