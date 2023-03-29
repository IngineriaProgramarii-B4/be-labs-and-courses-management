package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class Resource {
    private UUID id;
    private String title;
    private String location;
    private LocalDateTime timeStamp;

    //constructor
    public Resource(UUID id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.timeStamp = LocalDateTime.now();
    }

    //setters
    public void setId(UUID id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    //getters
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getLocation() {
        return location;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
