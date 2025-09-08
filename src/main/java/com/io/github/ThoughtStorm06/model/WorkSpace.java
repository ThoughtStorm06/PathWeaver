package com.io.github.ThoughtStorm06.model;

import java.util.HashMap;

public class WorkSpace {
    private String owner;
    private HashMap<String, String> projects; 
    // Project name → file path

    public WorkSpace() {
        // Default constructor
    }

    // Getters and setters
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, String> getProjects() {
        return projects;
    }
    public void setProjects(HashMap<String, String> projects) {
        this.projects = projects;
    }
}
