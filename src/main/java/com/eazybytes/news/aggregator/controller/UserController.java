package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.model.User;
import com.eazybytes.news.aggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔐 Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // 🔓 Login
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
    @GetMapping("/get")
    public Optional<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
    
    @GetMapping("/users-by-role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getAllUsersByRole(role);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
