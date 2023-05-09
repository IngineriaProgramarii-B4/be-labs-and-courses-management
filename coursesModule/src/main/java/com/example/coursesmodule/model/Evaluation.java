package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GenericGenerator(
            name = "evaluation_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "evaluation_sequence"
    )
    private UUID id;
    @Column(name = "component", nullable = false)
    private String component;
    @Column(name = "value", nullable = false)
    private float value;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Evaluation() {
    }

    public Evaluation(UUID id, String component, float value, String description, boolean isDeleted) {
        this.id = id;
        this.component = component;
        this.value = value;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public Evaluation(String component, float value, String description, boolean isDeleted) {
        this.id = UUID.randomUUID();
        this.component = component;
        this.value = value;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", component='" + component + '\'' +
                ", value=" + value +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
