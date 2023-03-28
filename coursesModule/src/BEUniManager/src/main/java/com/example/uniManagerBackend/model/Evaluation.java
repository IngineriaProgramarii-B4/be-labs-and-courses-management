package com.example.uniManagerBackend.model;

import java.util.HashMap;

public class Evaluation {
    private HashMap<Object, Float> percentage;
    private int numberOfComponents;

    //constructors
    Evaluation() {
        percentage = new HashMap<>();
        numberOfComponents = 0;
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
    public void addPercentage(Object object, Float number) {
        percentage.put(object, number);
        numberOfComponents++;
    }
}