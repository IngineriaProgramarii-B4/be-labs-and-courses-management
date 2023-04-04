package com.example.coursesmodule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;

public class Evaluation {
    @NotNull
    private HashMap<String, Float> percentage;
    private int numberOfComponents;

    //constructors
    Evaluation(@JsonProperty("percentage") HashMap<String, Float> percentage) {
        this.percentage = new HashMap<>(percentage);
        numberOfComponents = percentage.size();
    }

    //setters
    public void setPercentage(HashMap<String, Float> percentage) {
        this.percentage = percentage;
    }
    public void setNumberOfComponents(int numberOfComponents) {
        this.numberOfComponents = numberOfComponents;
    }

    //getters
    public HashMap<String, Float> getPercentage() {
        return percentage;
    }
    public int getNumberOfComponents() {
        return numberOfComponents;
    }

    //additional methods
    public boolean containsComponent(Object component) {
        return percentage.containsKey(component);
    }

    public void addComponent(String component, float value) {
        percentage.put(component, value);
        numberOfComponents++;
    }

    public void removeComponent(String component) {
        percentage.remove(component);
        numberOfComponents--;
    }

    public void updateComponent(String component, float value) {
        percentage.remove(component);
        percentage.put(component, value);
    }
}