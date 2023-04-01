package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;

public class Evaluation {
    @NotNull
    private HashMap<Object, Float> percentage;
    private int numberOfComponents;

    //constructors
    Evaluation(@JsonProperty("percentage") HashMap<Object, Float> percentage) {
        this.percentage = new HashMap<>(percentage);
        numberOfComponents = percentage.size();
    }

    //setters
    public void setPercentage(HashMap<Object, Float> percentage) {
        this.percentage = percentage;
    }
    public void setNumberOfComponents(int numberOfComponents) {
        this.numberOfComponents = numberOfComponents;
    }

    //getters
    public HashMap<Object, Float> getPercentage() {
        return percentage;
    }
    public int getNumberOfComponents() {
        return numberOfComponents;
    }

    //additional methods
    public boolean containsComponent(Object component) {
        return percentage.containsKey(component);
    }
}