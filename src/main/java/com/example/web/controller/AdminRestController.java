package com.example.web.controller;

import com.example.web.model.User;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> userList() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping()
    public List<User> addUser(@RequestBody User user){
        userService.addUser(user);
        return userService.getAllUser();
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public List<User> deliteById(@PathVariable long id){
        userService.deleteById(id);
        return userService.getAllUser();
    }
}
