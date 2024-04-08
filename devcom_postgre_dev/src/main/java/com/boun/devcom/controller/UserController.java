package com.boun.devcom.controller;


import com.boun.devcom.model.User;
import com.boun.devcom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        User registeredUser = userService.registerNewUser(user);
        // Avoided returning the full user object here to not expose sensitive info
        return ResponseEntity.ok("User registered successfully with ID: " + registeredUser.getUserId());
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }


}

