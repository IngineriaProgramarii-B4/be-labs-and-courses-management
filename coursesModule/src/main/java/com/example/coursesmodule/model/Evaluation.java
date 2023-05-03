package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @SequenceGenerator(
            name = "evaluation_sequence",
            sequenceName = "evaluation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "evaluation_sequence"
    )
    private Long id;
    @Column(name = "component", nullable = false)
    private String component;
    @Column(name = "value", nullable = false)
    private float value;
    @Column(name = "description")
    private String description;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Evaluation() {
    }

    public Evaluation(@JsonProperty("id") Long id,
                      @JsonProperty("component") String component,
                      @JsonProperty("value") float value,
                      @JsonProperty("description") String description,
                      @JsonProperty("isDeleted") boolean isDeleted) {
        this.id = id;
        this.component = component;
        this.value = value;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
