package com.io.github.ThoughtStorm06.controller;

import com.io.github.ThoughtStorm06.service.UserService;
import com.io.github.ThoughtStorm06.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.create(user);
    }

    @GetMapping("/read")
    public User readUser(@RequestParam String username, @RequestParam String password) {
        return userService.read(username, password);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String username) {
        userService.updatePassword(oldPassword, newPassword, username);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String username, @RequestParam String password) {
        userService.delete(username, password);
    }
}
