package com.abderrahmane.elearning.authservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @GetMapping
    public String getPage () {
        return "There is no GET for login in REST";
    }
}
