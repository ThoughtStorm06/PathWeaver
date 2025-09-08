package com.io.github.ThoughtStorm06.repository;
import com.io.github.ThoughtStorm06.model.WorkSpace;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


@Repository
public class WorkspaceRepo extends json_crud {
    // Workspace related CRUD operations would go here
    @Value("${pathweaver.data-folder}")
    private String  datafolderpath;
    
    //workspace object with username and hashmap of project and its location will come from service classes
    public void insertProject(WorkSpace workspace) {
        ObjectMapper mapper = new ObjectMapper();
        
        for(String folderPath: workspace.getProjects().values()) {
            File projectDir = new File(folderPath);
            if (!projectDir.exists()) {
                projectDir.mkdirs();
            }
        }

        try {
            mapper.writeValue(new File(datafolderpath+"/"+workspace.getOwner()+"/workspace/workspace_credentials.json"), workspace);
            System.out.println("Workspace updated successfully.");
        } catch (IOException e) {
            System.out.println("Error saving workspace details: " + e.getMessage());
        }
    }

    public WorkSpace read(String username) {
        ObjectMapper mapper = new ObjectMapper();
        String workspaceFilePath = datafolderpath +"/"+ username + "/workspace/workspace_credentials.json";
        File workspaceFile = new File(workspaceFilePath);
        if (workspaceFile.exists()) {
            try {
                WorkSpace workspace = mapper.readValue(workspaceFile, WorkSpace.class);
                return workspace;
            } catch (IOException e) {
                System.out.println("Error reading workspace details: " + e.getMessage());
            }
        } else {
            System.out.println("Workspace file does not exist.");
        }
        return null;
    }
    
    public void update(WorkSpace workspace,String oldProjectName,String newProjectName) {
        // renames project directory and the make the chanes in workspace_credentials.json file
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> projects = workspace.getProjects();
        if (projects.containsKey(oldProjectName)) {
            String oldPath = projects.get(oldProjectName);
            String newPath = datafolderpath + "/" + workspace.getOwner() + "/workspace/" + newProjectName;
            File oldDir = new File(oldPath);
            File newDir = new File(newPath);
            if (oldDir.exists()) {
                if (oldDir.renameTo(newDir)) {
                    projects.remove(oldProjectName);
                    projects.put(newProjectName, newPath);
                    workspace.setProjects(projects);
                    try {
                        mapper.writeValue(new File(datafolderpath+"/"+workspace.getOwner()+"/workspace/workspace_credentials.json"), workspace);
                        System.out.println("Workspace updated successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving workspace details: " + e.getMessage());
                    }
                } else {
                    System.out.println("Failed to rename project directory.");
                }
            } else {
                System.out.println("Old project directory does not exist.");
            }
        }
    }

    public void delete(String username, String projectName) {
        ObjectMapper mapper = new ObjectMapper();
        WorkSpace workspace = read(username);
        if (workspace != null) {
            HashMap<String, String> projects = workspace.getProjects();
            if (projects.containsKey(projectName)) {
                String projectPath = projects.get(projectName);
                File projectDir = new File(projectPath);
                if (projectDir.exists()) {
                    deleteDirectory(projectDir);
                    projects.remove(projectName);
                    workspace.setProjects(projects);
                    try {
                        mapper.writeValue(new File(datafolderpath+"/"+workspace.getOwner()+"/workspace/workspace_credentials.json"), workspace);
                        System.out.println("Workspace updated successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving workspace details: " + e.getMessage());
                    }
                } else {
                    System.out.println("Project directory does not exist.");
                }
            } else {
                System.out.println("Project not found in workspace.");
            }
        }
    }
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }
}
