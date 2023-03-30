package com.example.coursesmodule.model;

import java.util.List;

public interface Approfundation {
    //setters
    public void setResources(List<Resource> resources);
    public void setNumberWeeks(int numberWeeks);
    public void setId(int id);

    //getters
    public List<Resource> getResources();
    public int getNumberWeeks();
    public int getId();

    //additional methods
    public void addResource(Resource resource);
}
