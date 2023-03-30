package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Laboratory implements Approfundation {

    private int id;
    private List<Resource> resources;
    private int numberWeeks;

    public Laboratory(@JsonProperty("id") int id,@JsonProperty("numberWeeks") int numberWeeks) {
        this.resources = new ArrayList<>();
        this.numberWeeks = numberWeeks;
        this.id = id;
    }

    //setters
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    public void setNumberWeeks(int numberWeeks) {
        this.numberWeeks = numberWeeks;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    //getters
    public List<Resource> getResources() {
        return resources;
    }
    public int getNumberWeeks() {
        return numberWeeks;
    }

    @Override
    public int getId() {
        return id;
    }

    //additional methods
    public void addResource(Resource resource) {
        this.resources.add(resource);
    }
    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }
}
