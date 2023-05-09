package com.example.models;

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
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    //constructor


    public Resource() {
        timeStamp = LocalDateTime.now();
    }

    public Resource(int id,
                    String title,
                    String location,
                    String type,
                    boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
        this.isDeleted = isDeleted;
    }

    public Resource(String title, String location, String type, boolean isDeleted) {
        this.title = title;
        this.location = location;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
        this.isDeleted = isDeleted;
    }

    //setters

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

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
