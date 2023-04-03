package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Course{
    List<Resource> resources;

    int numberOfWeeks;

    //constructor

    public Course(@JsonProperty("weeks") int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
        this.resources=new ArrayList<>();
    }

    //setters
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    //getters
    public List<Resource> getCourseResources() {
        return resources;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    //additional methods
    public void addCourseResource(Resource resource) {
        resources.add(resource);
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

    public Optional<Resource> getCourseResourceById(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }
}
