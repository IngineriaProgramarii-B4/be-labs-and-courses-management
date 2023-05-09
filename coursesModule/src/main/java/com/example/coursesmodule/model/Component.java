package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "component")
public class Component {
    @Id
    @GenericGenerator(
            name = "component_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "component_sequence"
    )
    private UUID id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "number_weeks", nullable = false)
    private int numberWeeks;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    private List<Resource> resources = new ArrayList<>();
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Component() {
    }

    public Component(UUID id, String type, int numberWeeks, List<Resource> resources, boolean isDeleted) {
        this.id = id;
        this.type = type;
        this.numberWeeks = numberWeeks;
        this.resources = resources;
        this.isDeleted = isDeleted;
    }

    public Component(String type, int numberWeeks, List<Resource> resources, boolean isDeleted) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.numberWeeks = numberWeeks;
        this.resources = resources;
        this.isDeleted = isDeleted;
    }

    //setters
    public void setType(String type) {
        this.type = type;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setNumberWeeks(int numberWeeks) {
        this.numberWeeks = numberWeeks;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    //getters

    public String getType() {
        return type;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public int getNumberWeeks() {
        return numberWeeks;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }

    public void softDeleteResource(Resource resource) {
        int index = resources.indexOf(resource);
        if (index != -1) {
            resource.setDeleted(true);
            resources.set(index, resource);
        }
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
        return "Component{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", numberWeeks=" + numberWeeks +
                ", resources=" + resources +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
