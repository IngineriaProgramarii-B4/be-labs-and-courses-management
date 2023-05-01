package com.example.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "reminders")
@SQLDelete(sql = "UPDATE reminders SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Reminder {
    private UUID creatorId;
    private String creatorUsername;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime dueDateTime;
    private String title;
    private String description;
    private boolean deleted = Boolean.FALSE;

    public Reminder() {

    }

    public Reminder(Student student, String dueDateTime, String title, String description) {
        this.creatorId = student.getId();
        creatorUsername = student.getUsername();
        this.dueDateTime = LocalDateTime.parse(dueDateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"  ));
        this.title = title;
        this.description = description;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
