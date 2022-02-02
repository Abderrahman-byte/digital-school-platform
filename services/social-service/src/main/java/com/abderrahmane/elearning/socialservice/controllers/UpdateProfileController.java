package com.abderrahmane.elearning.socialservice.controllers;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class UpdateProfileController {
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String handlePut (@RequestBody Map<String, Object> body) {
        return "UPDATE profile handler";
    }
}
