package com.io.github.ThoughtStorm06.service;

import com.io.github.ThoughtStorm06.model.WorkSpace;
import com.io.github.ThoughtStorm06.repository.WorkspaceRepo;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService {
    public final WorkspaceRepo workspaceRepo;

    public WorkspaceService(WorkspaceRepo workspaceRepo) {
        this.workspaceRepo = workspaceRepo;
    }

    public void create(WorkSpace workSpace) {
        workspaceRepo.insertProject(workSpace);
    }

    public WorkSpace read(String username) {
        return workspaceRepo.read(username);
    }

    public void update(WorkSpace workspace, String oldProjectName, String newProjectName) {
        workspaceRepo.update(workspace, oldProjectName, newProjectName);
    }

    public void delete(String username, String projectName) {
        workspaceRepo.delete(username, projectName);
    }
}
