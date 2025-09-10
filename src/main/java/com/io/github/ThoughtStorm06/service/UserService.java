package com.io.github.ThoughtStorm06.service;

import com.io.github.ThoughtStorm06.model.User;
import com.io.github.ThoughtStorm06.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void create(User user) {
        userRepo.create(user);
    }

    public User read(String username,String password) {
        return userRepo.read(username,password);
    }

    public void update(User user) {
        userRepo.update(user);
    }

    public void updatePassword(String oldPassword, String newPassword, String username) {
        userRepo.updatePassword(oldPassword, newPassword, username);
    }

    public void delete(String username,String password) {
        userRepo.delete(username,password);
    }


}
