package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Resource")
public class Resource {
    @Id
    private int id;

    @Column(name = "subjectId", nullable = false)
    private final int subjectId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Location")
    private String location;

    @Column(name = "TimeStamp")
    private LocalDateTime timeStamp;

    //constructor
    public Resource(@JsonProperty("id") int id,
                    @JsonProperty("title") String title,
                    @JsonProperty("location") String location,
                    @JsonProperty("subjectId") int subjectId) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.timeStamp = LocalDateTime.now();
        this.subjectId = subjectId;
    }

    //setters
    public void setId(int id) {
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

    public int getSubjectId() {
        return subjectId;
    }

    public int getId() {
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
