package com.io.github.ThoughtStorm06.model;


import java.util.ArrayList;
import java.util.HashMap;

public class GraphStruct {

    // Node title → JSON file path
    private HashMap<String, String> nodes;   

    //Node title → list of next connected node titles
    private HashMap<String, ArrayList<String>> next_connections;


    public HashMap<String, String> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<String, String> nodes) {
        this.nodes = nodes;
    }

    public HashMap<String, ArrayList<String>> getNext_Connections() {
        return next_connections;
    }
    public void setNext_Connections(HashMap<String, ArrayList<String>> next_connections) {
        this.next_connections = next_connections;
    }
}