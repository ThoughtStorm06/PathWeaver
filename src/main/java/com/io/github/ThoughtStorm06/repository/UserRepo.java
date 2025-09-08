package com.io.github.ThoughtStorm06.repository;
import com.io.github.ThoughtStorm06.model.User;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepo extends json_crud {

    @Value("${pathweaver.data-folder}")
    private String  datafolderpath;



    // creating user and providing a folder to them
    public String passwordProtection(String Password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(Password);
    }


    public void create(User user) {
        ObjectMapper mapper = new ObjectMapper();
        String userDirPath = datafolderpath+"/"+ user.getUsername();
        File userDir = new File(userDirPath);
        if (!userDir.exists()) {
            userDir.mkdirs();
            // Save user details to a JSON file
            try {
                String userFilePath = userDirPath + "/userCredentials.json";
                mapper.writeValue(new File(userFilePath), user);
                System.out.println("User created successfully.");
            } catch (IOException e) {
                System.out.println("Error saving user details: " + e.getMessage());
            }
        } else {
            System.out.println("Username already exists.");
        }

        String WorkSpacePath = datafolderpath + "/" + user.getUsername()+"/Workspace";
        File WorkSpaceDir = new File(WorkSpacePath);
        if (!WorkSpaceDir.exists()) {
            WorkSpaceDir.mkdirs();
        }



    }

    public User read(String username,String password) {
        ObjectMapper mapper = new ObjectMapper();
        String userFilePath = datafolderpath +"/"+ username + "/userCredentials.json";
        File userFile = new File(userFilePath);
        if (userFile.exists()) {
            try {
                User user = mapper.readValue(userFile, User.class);
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    System.out.println("User authenticated successfully.");
                    return user;
                } else {
                    System.out.println("Invalid password.");
                    return null;
                }
            } catch (IOException e) {
                System.out.println("Error reading user details: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("User not found.");
            return null;
        }
    }

    public void update(User user) {
        ObjectMapper mapper = new ObjectMapper();
        String userFilePath = datafolderpath +"/"+ user.getUsername() + "/userCredentials.json";
        File userFile = new File(userFilePath);
        if (userFile.exists()) {
            try {
                mapper.writeValue(userFile, user);
                System.out.println("User updated successfully.");
            } catch (IOException e) {
                System.out.println("Error updating user details: " + e.getMessage());
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public void updatePassword(String oldPassword,String newPassword,String username) {
        ObjectMapper mapper = new ObjectMapper();
        String userFilePath = datafolderpath +"/"+ username + "/userCredentials.json";
        File userFile = new File(userFilePath);
        if (userFile.exists()) {
            try {
                User user = mapper.readValue(userFile, User.class);
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                    user.setPassword(passwordProtection(newPassword));
                    mapper.writeValue(userFile, user);
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("Invalid old password.");
                }
            } catch (IOException e) {
                System.out.println("Error updating password: " + e.getMessage());
            }
        } else {
            System.out.println("User not found.");
        }

    }

    public void delete(String username,String password) {
        ObjectMapper mapper = new ObjectMapper();
        String userFilePath = datafolderpath +"/"+ username + "/userCredentials.json";
        File userFile = new File(userFilePath);
        if (userFile.exists()) {
            try {
                User user = mapper.readValue(userFile, User.class);
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    File userDir = new File(datafolderpath + "/"+username);
                    deleteDirectory(userDir);
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("Invalid password.");
                }
            } catch (IOException e) {
                System.out.println("Error deleting user: " + e.getMessage());
            }
        } else {
            System.out.println("User not found.");
        }
    }
    //erasing user directory by deleting files and sub directories
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
