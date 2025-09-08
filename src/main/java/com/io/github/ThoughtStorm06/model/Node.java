package com.io.github.ThoughtStorm06.model;

public class Node {
    private String title;
    private String imagePath;   // Instead of File
    private String content;
    // Node title → list of JSON paths

    // Constructors
    public Node(String title, String imagePath, String content) {
        this.title = title;
        this.imagePath = imagePath;
        this.content = content;
    }

    public Node() {
        // Default constructor
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    
}
