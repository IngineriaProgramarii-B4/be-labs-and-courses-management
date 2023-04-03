package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Course{
    int numberWeeks;
    List<Resource> resources;

    //constructor

    public Course(@JsonProperty("numberWeeks") int numberWeeks) {
        this.numberWeeks = numberWeeks;
        this.resources=new ArrayList<>();
    }

    //setters
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setNumberWeeks(int numberOfWeeks) {
        this.numberWeeks = numberOfWeeks;
    }

    //getters
    public List<Resource> getResources() {
        return resources;
    }

    public int getNumberWeeks() {
        return numberWeeks;
    }


    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public Optional<Resource> getCourseResourceById(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }

    public void removeCourseResource(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                resources.remove(resource);
                break;
            }
        }
    }

    public void updateCourseResource(int resourceId, Resource resource) {
        for (Resource res : resources) {
            if (res.getId() == resourceId) {
                res = resource;
                break;
            }
        }
    }
}
