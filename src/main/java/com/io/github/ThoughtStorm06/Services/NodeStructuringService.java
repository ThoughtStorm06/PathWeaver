package com.io.github.ThoughtStorm06.Services;

import com.io.github.ThoughtStorm06.model.Node;
import com.io.github.ThoughtStorm06.model.GraphStruct;
import com.io.github.ThoughtStorm06.repository.NodeStructuringRepo;
import org.springframework.stereotype.Service;


@Service
public class NodeStructuringService {
    public final NodeStructuringRepo nodeStructuringRepo;

    public NodeStructuringService(NodeStructuringRepo nodeStructuringRepo) {
        this.nodeStructuringRepo = nodeStructuringRepo;
    }

    public void insertNode(String username, String project, GraphStruct graph, Node node) throws Exception {
        nodeStructuringRepo.insertNode(username, project, graph, node);
    }

    public GraphStruct readGraph(String username, String project) {
        return nodeStructuringRepo.readGraph(username, project);
    }

    public Node readNode(String username, String project, String nodeTitle) {
        return nodeStructuringRepo.readNode(username, project, nodeTitle);
    }

    public void updateNode(String username, String project, Node node) {
        nodeStructuringRepo.updateNode(username, project, node);
    }

    public void updateConnections(String username, String project, GraphStruct graph) {
        nodeStructuringRepo.updateConnections(username, project, graph);
    }

    public void updateTitleName(String username, String project, String oldTitle, String newTitle) {
        nodeStructuringRepo.updateTitleName(username, project, oldTitle, newTitle);
    }

    public void deleteNode(String username, String project, String nodeTitle) {
        nodeStructuringRepo.deleteNode(username, project, nodeTitle);
    }

}
