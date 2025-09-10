package com.io.github.ThoughtStorm06.repository;
import com.io.github.ThoughtStorm06.model.GraphStruct;
import com.io.github.ThoughtStorm06.model.Node;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

@Repository
public class NodeStructuringRepo extends json_crud {
    @Value("${pathweaver.data-folder}")
    private String  datafolderpath;

    //make sure the title data from frontend only contain alphabets, numbers,underscores. No spaces or special characters.
    public void insertNode(String username, String project,GraphStruct graph, Node node) throws IOException {
        String projectDirPath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project;
        File projectDir = new File(projectDirPath);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }

        // Save the node as a JSON file
        String nodeFilePath = projectDirPath + File.separator + node.getTitle() + ".json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(nodeFilePath), node);

        // Update the graph structure
        HashMap<String, java.util.ArrayList<String>> NodeConnections = graph.getNodeConnections();
        NodeConnections.putIfAbsent(node.getTitle(), new java.util.ArrayList<>());
        graph.setNodeConnections(NodeConnections);

        // Save the updated graph structure
        String graphFilePath = projectDirPath + File.separator + "_graph_credentials.json";
        objectMapper.writeValue(new File(graphFilePath), graph);
    }

    public GraphStruct readGraph(String username, String project) {
        String graphFilePath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project + File.separator + "_graph_credentials.json";
        File graphFile = new File(graphFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        if (graphFile.exists()) {
            try {
                GraphStruct graph = objectMapper.readValue(graphFile, GraphStruct.class);
                return graph;
            } catch (IOException e) {
                System.out.println("Error reading graph structure: " + e.getMessage());
            }
        } else {
            System.out.println("Graph file does not exist.");
        }
        return null;
    }

    public Node readNode(String username, String project, String nodeTitle) {
        String nodeFilePath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project + File.separator + nodeTitle + ".json";
        File nodeFile = new File(nodeFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        if (nodeFile.exists()) {
            try {
                Node node = objectMapper.readValue(nodeFile, Node.class);
                return node;
            } catch (IOException e) {
                System.out.println("Error reading node: " + e.getMessage());
            }
        } else {
            System.out.println("Node file does not exist.");
        }
        return null;
    }

    public void updateNode(String username, String project, Node node) {
        String projectDirPath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project;
        String nodeFilePath = projectDirPath + File.separator + node.getTitle() + ".json";
        File nodeFile = new File(nodeFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        if (nodeFile.exists()) {
            try {
                objectMapper.writeValue(nodeFile, node);
                System.out.println("Node updated successfully.");
            } catch (IOException e) {
                System.out.println("Error updating node: " + e.getMessage());
            }
        } else {
            System.out.println("Node file does not exist.");
        }
    }

    public void updateConnections(String username, String project, GraphStruct graph) {
        String projectDirPath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project;
        String graphFilePath = projectDirPath + File.separator + "_graph_credentials.json";
        File graphFile = new File(graphFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        if (graphFile.exists()) {
            try {
                objectMapper.writeValue(graphFile, graph);
                System.out.println("Graph structure updated successfully.");
            } catch (IOException e) {
                System.out.println("Error updating graph structure: " + e.getMessage());
            }
        } else {
            System.out.println("Graph file does not exist.");
        }
    }

    public void updateTitleName(String username, String project, String oldTitle, String newTitle) {
        String projectDirPath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project;
        File oldNodeFile = new File(projectDirPath + File.separator + oldTitle + ".json");
        File newNodeFile = new File(projectDirPath + File.separator + newTitle + ".json");
        if (oldNodeFile.exists()) {
            if (oldNodeFile.renameTo(newNodeFile)) {
                System.out.println("Node title updated successfully.");
            } else {
                System.out.println("Failed to rename node file.");
                return;
            }
        } else {
            System.out.println("Old node file does not exist.");
            return;
        }

        // Update graph structure
        GraphStruct graph = readGraph(username, project);
        if (graph != null) {
            HashMap<String, java.util.ArrayList<String>> NodeConnections = graph.getNodeConnections();
            if (NodeConnections.containsKey(oldTitle)) {
                java.util.ArrayList<String> connections = NodeConnections.remove(oldTitle);
                NodeConnections.put(newTitle, connections);
            }
            for (String key : NodeConnections.keySet()) {
                java.util.ArrayList<String> connections = NodeConnections.get(key);
                for (int i = 0; i < connections.size(); i++) {
                    if (connections.get(i).equals(oldTitle)) {
                        connections.set(i, newTitle);
                    }
                }
            }
            graph.setNodeConnections(NodeConnections);
            updateConnections(username, project, graph);
        }
    }

    public void deleteNode(String username, String project, String nodeTitle) {
        String projectDirPath = datafolderpath + File.separator + username + File.separator +"workspace"+ File.separator+ project;
        File nodeFile = new File(projectDirPath + File.separator + nodeTitle + ".json");
        if (nodeFile.exists()) {
            if (nodeFile.delete()) {
                System.out.println("Node deleted successfully.");
            } else {
                System.out.println("Failed to delete node file.");
                return;
            }
        } else {
            System.out.println("Node file does not exist.");
            return;
        }

        // Update graph structure
        GraphStruct graph = readGraph(username, project);
        if (graph != null) {
            HashMap<String, java.util.ArrayList<String>> NodeConnections = graph.getNodeConnections();
            NodeConnections.remove(nodeTitle);
            for (String key : NodeConnections.keySet()) {
                java.util.ArrayList<String> connections = NodeConnections.get(key);
                connections.removeIf(connection -> connection.equals(nodeTitle));
            }
            graph.setNodeConnections(NodeConnections);
            updateConnections(username, project, graph);
        }
    }
}
