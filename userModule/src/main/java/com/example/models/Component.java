package com.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "component")
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
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Component() {
    }

    public Component(@JsonProperty("id") int id,
                     @JsonProperty("type") String type,
                     @JsonProperty("numberWeeks") int numberWeeks,
                     @JsonProperty("resources") List<Resource> resources,
                     @JsonProperty("isDeleted") boolean isDeleted) {
        this.id = id;
        this.type = type;
        this.numberWeeks = numberWeeks;
        this.resources = resources;
        this.isDeleted = isDeleted;
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

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }

    public void softDeleteResource(Resource resource) {
        int index = resources.indexOf(resource);
        if (index != -1) {
            resource.setDeleted(true);
            resources.set(index, resource);
        }
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", numberWeeks=" + numberWeeks +
                ", resources=" + resources +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
