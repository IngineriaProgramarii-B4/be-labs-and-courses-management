package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "Course")
public class Course{

    @Id
    private final int subjectId;

    @Column(name = "NumberOfWeeks")
    int numberWeeks;

    List<Resource> resources;

    public Course(@JsonProperty("numberWeeks") int numberWeeks, @JsonProperty("subjectId") int subjectId) {
        this.numberWeeks = numberWeeks;
        this.resources=new ArrayList<>();
        this.subjectId = subjectId;
    }


    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setNumberWeeks(int numberOfWeeks) {
        this.numberWeeks = numberOfWeeks;
    }

    public int getSubjectId() {
        return subjectId;
    }

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
