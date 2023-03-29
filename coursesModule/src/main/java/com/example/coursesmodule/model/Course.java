package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    String title;
    int id;
    List<Resource> resources;
    int credits;
    Evaluation evaluationMethod;
    int year;
    int semester;
    List<Approfundation> approfundationList;

    //constructor

    public Course(@JsonProperty("title") String title,
                  @JsonProperty("id") int id,
                  @JsonProperty("credits") int credits,
                  @JsonProperty("year") int year,
                  @JsonProperty("semester") int semester) {
        this.title = title;
        this.id = id;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.resources=new ArrayList<>();
    }

    public Course(String title, int id, List<Resource> resources, int credits, Evaluation evaluationMethod, int year, int semester, List<Approfundation> approfundationList) {
        this.title = title;
        this.id = id;
        this.resources = resources;
        this.credits = credits;
        this.evaluationMethod = evaluationMethod;
        this.year = year;
        this.semester = semester;
        this.approfundationList = approfundationList;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public void setEvaluationMethod(Evaluation evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public void setApprofundationList(List<Approfundation> approfundationList) {
        this.approfundationList = approfundationList;
    }

    //getters
    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }
    public List<Resource> getResources() {
        return resources;
    }
    public int getCredits() {
        return credits;
    }
    public Evaluation getEvaluationMethod() {
        return evaluationMethod;
    }
    public int getYear() {
        return year;
    }
    public int getSemester() {
        return semester;
    }
    public List<Approfundation> getApprofundationList() {
        return approfundationList;
    }

    //additional methods
    public void addResource(Resource resource) {
        resources.add(resource);
    }
    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public void addApprofundation(Approfundation approfundation) {
        approfundationList.add(approfundation);
    }
    public void removeApprofundation(Approfundation approfundation) {
        approfundationList.remove(approfundation);
    }

    public Resource getResourceById(String id) {
        for (Resource resource : resources)
            if (Objects.equals(resource.getId(), id))
                return resource;
        return null;
    }
}
