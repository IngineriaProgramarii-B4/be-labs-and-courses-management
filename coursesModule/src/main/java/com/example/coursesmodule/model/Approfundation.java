package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Approfundation {

    private final String type;
    @NotNull
    private int id;
    private List<Resource> resources;
    @NotNull
    private int numberWeeks;

    public Approfundation(@JsonProperty("id") int id,
                          @JsonProperty("numberWeeks") int numberWeeks,
                          @JsonProperty("type") String type) {
        // this.resources = new ArrayList<>();
        this.numberWeeks = numberWeeks;
        this.id = id;
        this.type = type;
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
}
