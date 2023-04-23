package com.example.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "reminders")
public class Reminder {
    private UUID creatorId;
    private String creatorUsername;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reminderId;
    private LocalDateTime dueDateTime;
    private String title;
    private String description;

    public Reminder() {

    }

    public Reminder(Student student, String dueDateTime, String title, String description) {
        this.creatorId = student.getId();
        creatorUsername = student.getUsername();
        this.dueDateTime = LocalDateTime.parse(dueDateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"  ));
        this.title = title;
        this.description = description;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public UUID getReminderId() {
        return reminderId;
    }

    public void setReminderId(UUID reminderId) {
        this.reminderId = reminderId;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
