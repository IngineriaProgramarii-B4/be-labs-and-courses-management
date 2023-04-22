package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(
        name = "component",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "component_subject_id_type_unique",
                        columnNames = {"subject_id", "type"}
                )
        }
)
public class Component {
    @Id
    @SequenceGenerator(
            name = "approfundation_sequence",
            sequenceName = "approfundation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "approfundation_sequence"
    )
    private int id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "number_weeks", nullable = false)
    private int numberWeeks;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    private List<Resource> resources = new ArrayList<>();

    public Component() {
    }


    public Component(@JsonProperty("id") int id,
                     @JsonProperty("type") String type,
                     @JsonProperty("numberWeeks") int numberWeeks,
                     @JsonProperty("resources") List<Resource> resources) {
        this.id = id;
        this.type = type;
        this.numberWeeks = numberWeeks;
        this.resources = resources;
    }

    //setters
    public void setType(String type) {
        this.type = type;
    }

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
