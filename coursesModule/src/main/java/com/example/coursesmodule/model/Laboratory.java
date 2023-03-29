package com.example.coursesmodule.model;

import java.util.List;

public class Laboratory implements Approfundation {

    private List<Resource> resources;
    private int numberWeeks;

    //setters
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    public void setNumberWeeks(int numberWeeks) {
        this.numberWeeks = numberWeeks;
    }

    //getters
    public List<Resource> getResources() {
        return resources;
    }
    public int getNumberWeeks() {
        return numberWeeks;
    }

    //additional methods
    public void addResource(Resource resource) {
        this.resources.add(resource);
    }
    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }
}
