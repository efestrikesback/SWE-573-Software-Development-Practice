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
        return "mainPage";
    }

    @GetMapping("/userProfile")
    public String userProfile(){

        return "userProfile";
    }

    @GetMapping("/createCommunity")
    public String createCommunity() {
        return "createCommunity";
    }

    @GetMapping("/communityPage")
    public String communityPage() {
        return "communityPage";
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
        return "redirect:/login";
    }


}

