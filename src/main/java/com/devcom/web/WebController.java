package com.devcom.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
        // Optionally load data for the main page
        // For example, fetch a list of communities and add to the model
        // List<Community> communities = communityService.findAll();
        // model.addAttribute("communities", communities);

        return "mainPage";  // returns the mainPage.html Thymeleaf template
    }

    @GetMapping("/userProfile")
    public String userProfile(){

        return "userProfile";
    }

    @GetMapping("/createCommunity")
    public String createCommunity() {
        return "createCommunity";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister() {
        // Process registration logic here
        return "redirect:/login";
    }


}

