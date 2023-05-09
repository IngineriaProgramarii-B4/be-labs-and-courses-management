package com.example.models;

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
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "credits", nullable = false)
    private int credits;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "semester", nullable = false)
    private int semester;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    // describe a One-to-Many relationship between Subject and Approfundation
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private List<Component> componentList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private List<Evaluation> evaluationList = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Resource image;
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

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
                   @JsonProperty("evaluations") List<Evaluation> evaluationList,
                   @JsonProperty("isDeleted") boolean isDeleted
    ) {
        this.id = id;
        this.title = title;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.description = description;
        this.componentList = componentList;
        this.evaluationList = evaluationList;
        this.image = null;
        this.isDeleted = isDeleted;
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

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Resource getImage() {
        return image;
    }

    public void setImage(Resource image) {
        this.image = image;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    //additional methods

    public void addComponent(Component component) {
        componentList.add(component);
    }

    public void removeComponent(Component component) {
        componentList.remove(component);
    }

    public void softDeleteComponent(Component component) {
        int index = componentList.indexOf(component);
        if (index != -1) {
            component.setDeleted(true);
            componentList.set(index, component);
        }
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluationList.add(evaluation);
    }

    public void removeEvaluation(Evaluation evaluation) {
        evaluationList.add(evaluation);
    }

    public void softDeleteEvaluation(Evaluation evaluation) {
        int index = evaluationList.indexOf(evaluation);
        if (index != -1) {
            evaluation.setDeleted(true);
            evaluationList.set(index, evaluation);
        }
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
                ", image=" + image +
                ", isDeleted=" + isDeleted +
                '}';
    }
}