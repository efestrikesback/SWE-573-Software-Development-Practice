package com.devcom.webapp.controllers;


import com.devcom.webapp.models.User;
import com.devcom.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Community Builder!";
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User newUser) {
        return userService.registerUser(newUser);
    }
}
