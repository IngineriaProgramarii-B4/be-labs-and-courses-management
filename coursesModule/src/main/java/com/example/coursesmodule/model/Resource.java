package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @SequenceGenerator(
            name = "resource_sequence",
            sequenceName = "resource_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resource_sequence"
    )
    private int id;
    @Column(
            name = "title",
            nullable = false
    )
    private String title;
    @Column(
            name = "location",
            nullable = false,
            unique = true
    )
    private String location;
    @Column(
            name = "time_stamp",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime timeStamp;
    @Column(
            name = "type",
            nullable = false
    )
    private String type;
    //constructor


    public Resource() {
        timeStamp = LocalDateTime.now();
    }

    public Resource(int id,
                    String title,
                    String location,
                    String type) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
    }

    public Resource(String title, String location, String type) {
        this.title = title;
        this.location = location;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
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

    public void setType(String type) {
        this.type = type;
    }

    //getters
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

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", timeStamp=" + timeStamp +
                ", type='" + type + '\'' +
                '}';
    }
}
