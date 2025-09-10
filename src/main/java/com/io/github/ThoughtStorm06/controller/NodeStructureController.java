package com.io.github.ThoughtStorm06.controller;

import com.io.github.ThoughtStorm06.service.NodeStructuringService;
import com.io.github.ThoughtStorm06.model.Node;
import com.io.github.ThoughtStorm06.model.GraphStruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nodeStructure")
public class NodeStructureController {
    @Autowired
    private NodeStructuringService nodeStructuringService;

    @PostMapping("/insertNode")
    public void insertNode(@RequestParam String username, @RequestParam String project, @RequestBody GraphStruct graph, @RequestBody Node node) {
        try {
            nodeStructuringService.insertNode(username, project, graph, node);
        } catch (Exception e) {
            System.out.println("Error inserting node: " + e.getMessage());
        }
    }
    @GetMapping("/readGraph")
    public GraphStruct readGraph(@RequestParam String username, @RequestParam String project) {
        return nodeStructuringService.readGraph(username, project);
    }

    @GetMapping("/readNode")
    public Node readNode(@RequestParam String username, @RequestParam String project, @RequestParam String nodeTitle) {
        return nodeStructuringService.readNode(username, project, nodeTitle);
    }

    @PutMapping("/updateNode")
    public void updateNode(@RequestParam String username, @RequestParam String project, @RequestBody Node node) {
        try {
            nodeStructuringService.updateNode(username, project, node);
        } catch (Exception e) {
            System.out.println("Error updating node: " + e.getMessage());
        }
    }

    @PutMapping("/updateGraph")
    public void updateGraph(@RequestParam String username, @RequestParam String project, @RequestBody GraphStruct graph) {
        try {
            nodeStructuringService.updateConnections(username, project, graph);
        } catch (Exception e) {
            System.out.println("Error updating graph: " + e.getMessage());
        }
    }

    @PutMapping("/updateTitle")
    public void updateTitle(@RequestParam String username, @RequestParam String project, @RequestParam String oldTitle, @RequestParam String newTitle) {
        try {
            nodeStructuringService.updateTitleName(username, project, oldTitle, newTitle);
        } catch (Exception e) {
            System.out.println("Error updating title: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteNode")
    public void deleteNode(@RequestParam String username, @RequestParam String project, @RequestParam String nodeTitle) {
        try {
            nodeStructuringService.deleteNode(username, project, nodeTitle);
        } catch (Exception e) {
            System.out.println("Error deleting node: " + e.getMessage());
        }
    }
}
