package com.example.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class Demo {
    @GetMapping("/")
    public String get()
    {
        return "login";
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")

    public ResponseEntity<String> sayStudent() {
        return ResponseEntity.ok("This is a STUDENT page");
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<String> sayTeacher() {
        return ResponseEntity.ok("This is a TEACHER page");
    }

}