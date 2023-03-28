package com.example.controllers;

import com.example.models.AppUser;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/students/{username}")
    public AppUser getUser(@PathVariable String username) {
       AppUser user = userService.getUser(username);
       if(user == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
       return user;
    }
}
