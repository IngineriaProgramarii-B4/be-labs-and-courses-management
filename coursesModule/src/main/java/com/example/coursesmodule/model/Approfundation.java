package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Approfundation")
public class Approfundation {

    @Column(name = "subjectId", nullable = false)
    private final int subjectId;

    @Column(name = "Type", nullable = false)
    private final String type;

    @Id
    @NotNull
    private int id;

    private List<Resource> resources;


    @NotNull
    @Column(name = "NumberOfWeeks")
    private int numberWeeks;

    public Approfundation(@JsonProperty("id") int id,
                          @JsonProperty("numberWeeks") int numberWeeks,
                          @JsonProperty("type") String type,
                          @JsonProperty("subjectId") int subjectId) {
        this.resources = new ArrayList<>();
        this.numberWeeks = numberWeeks;
        this.id = id;
        this.type = type;
        this.subjectId = subjectId;
    }

    //setters
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setNumberWeeks(int numberWeeks) {
        this.numberWeeks = numberWeeks;
    }


    public void setId(int id) {
        this.id = id;
    }

    //getters
    public int getSubjectId() {
        return subjectId;
    }

    public String getType() {
        return type;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public int getNumberWeeks() {
        return numberWeeks;
    }

    public int getId() {
        return id;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }


    public Optional<Resource> getResourceById(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }

    public void removeResource(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                resources.remove(resource);
                break;
            }
        }
    }

    public void updateResource(int resourceId, Resource resource) {
        for (Resource res : resources) {
            if (res.getId() == resourceId) {
                res = resource;
                break;
            }
        }
    }
}
