package com.io.github.ThoughtStorm06.model;


import java.util.ArrayList;
import java.util.HashMap;

public class GraphStruct {
    private HashMap<String, ArrayList<String>> NodeConnections; // Map of node titles to list of connected node titles

    // Constructors
    public GraphStruct() {
        this.NodeConnections = new HashMap<>();
    }

    // Getters and setters
    public HashMap<String, ArrayList<String>> getNodeConnections() {
        return NodeConnections;
    }

    public void setNodeConnections(HashMap<String, ArrayList<String>> NodeConnections) {
        this.NodeConnections = NodeConnections;
    }
}