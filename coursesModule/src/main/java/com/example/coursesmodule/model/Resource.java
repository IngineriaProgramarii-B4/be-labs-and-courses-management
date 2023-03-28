package com.example.coursesmodule.model;

import java.time.LocalDateTime;

public class Resource {
    private String id;
    private String title;
    private String location;
    private LocalDateTime timeStamp;

    //constructor
    public Resource(String id, String title, String location, LocalDateTime timeStamp) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.timeStamp = timeStamp;
    }

    //setters
    public void setId(String id) {
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
    public String getId() {
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
