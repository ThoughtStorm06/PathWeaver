package com.io.github.ThoughtStorm06.controller;

import com.io.github.ThoughtStorm06.service.WorkspaceService;
import com.io.github.ThoughtStorm06.model.WorkSpace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping("/create")
    public void createWorkspace(@RequestBody WorkSpace workSpace) {
        workspaceService.create(workSpace);
    }

    @GetMapping("/read")
    public WorkSpace readWorkspace(@RequestParam String username) {
        return workspaceService.read(username);
    }

    @PutMapping("/update")
    public void updateWorkspace(@RequestBody WorkSpace workspace, @RequestParam String oldProjectName, @RequestParam String newProjectName) {
        workspaceService.update(workspace, oldProjectName, newProjectName);
    }

    @DeleteMapping("/delete")
    public void deleteWorkspace(@RequestParam String username, @RequestParam String projectName) {
        workspaceService.delete(username, projectName);
    }
}
