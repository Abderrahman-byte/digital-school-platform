package com.abderrahmane.elearning.authservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {
    @GetMapping()
    public String homePage () {
        return "Welcome to the home page";
    }

    @GetMapping("/test/{id}")
    public String testPage (@PathVariable String id) {
        return "You're id : " + id;
    }
}
