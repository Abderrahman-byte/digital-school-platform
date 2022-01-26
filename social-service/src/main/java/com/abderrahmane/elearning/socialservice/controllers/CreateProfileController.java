package com.abderrahmane.elearning.socialservice.controllers;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class CreateProfileController {
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String handlePost(@RequestBody Map<String, Object> body) {
        return "CREATING new profile";
    }    
}
