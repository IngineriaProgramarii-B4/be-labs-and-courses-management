package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.util.*;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @SequenceGenerator(
            name = "subject_sequence",
            sequenceName = "subject_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subject_sequence"
    )
    int id;
    @Column(name = "title", nullable = false, unique = true)
    String title;
    @Column(name = "credits", nullable = false)
    int credits;
    @Column(name = "year", nullable = false)
    int year;
    @Column(name = "semester", nullable = false)
    int semester;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    String description;
    // describe a One-to-Many relationship between Subject and Approfundation
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    List<Component> componentList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    List<Evaluation> evaluationList = new ArrayList<>();

    //constructors
    public Subject() {
    }

    public Subject(@JsonProperty("id") int id,
                   @JsonProperty("title") String title,
                   @JsonProperty("credits") int credits,
                   @JsonProperty("year") int year,
                   @JsonProperty("semester") int semester,
                   @JsonProperty("description") String description,
                   @JsonProperty("components") List<Component> componentList,
                   @JsonProperty("evaluations") List<Evaluation> evaluationList
    ) {
        this.id = id;
        this.title = title;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.description = description;
        this.componentList = componentList;
        this.evaluationList = evaluationList;
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

    public void setEvaluationList(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setDescription(String description) { this.description = description; }

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
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

    public List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public String getDescription() { return description;}

    public List<Component> getComponentList() {
        return componentList;
    }

    public void addComponent(Component component) {
        componentList.add(component);
    }

    public void removeComponent(Component component) {
        componentList.remove(component);
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluationList.add(evaluation);
    }

    public void removeEvaluation(Evaluation evaluation) {
        evaluationList.remove(evaluation);
    }


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", year=" + year +
                ", semester=" + semester +
                ", description='" + description + '\'' +
                ", componentList=" + componentList +
                ", evaluationList=" + evaluationList +
                '}';
    }
}
