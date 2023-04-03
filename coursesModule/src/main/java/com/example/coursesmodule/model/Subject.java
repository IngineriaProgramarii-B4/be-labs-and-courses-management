package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Subject {
    String title;
    int id;
    int credits;
    Evaluation evaluationMethod;
    int year;
    int semester;
    String description;
    List<Approfundation> approfundationList;

    Course course;

    //constructor

    public Subject(@JsonProperty("title") String title,
                  @JsonProperty("id") int id,
                  @JsonProperty("credits") int credits,
                  @JsonProperty("year") int year,
                  @JsonProperty("semester") int semester,
                  @JsonProperty("description") String description) {
        this.title = title;
        this.id = id;
        this.credits = credits;
        this.evaluationMethod = new Evaluation(new HashMap<>());
        this.year = year;
        this.semester = semester;
        this.description = description;
        this.approfundationList=new ArrayList<>();
        this.course = null;
    }
    //setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(int id) {
        this.id = id;
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
    public void setDescription(String description) { this.description = description; }
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
    public String getDescription() { return description;}
    public Optional<Course> getCourse() { return Optional.ofNullable(course);}
    public List<Approfundation> getApprofundationList() {
        return approfundationList;
    }

    //additional methods

    public void addApprofundation(Approfundation approfundation) {
        approfundationList.add(approfundation);
    }
    public void removeApprofundation(Approfundation approfundation) {
        approfundationList.remove(approfundation);
    }

    public void removeEvaluationMethod() {
        this.evaluationMethod = new Evaluation(new HashMap<>());
    }

    public void addCourse(Course course) {
        this.course = course;
    }

    public void removeCourse(Course course) {
        this.course = null;
    }
}
