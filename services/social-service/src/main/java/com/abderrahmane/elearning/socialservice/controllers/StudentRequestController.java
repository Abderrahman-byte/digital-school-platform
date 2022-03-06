package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.version}/student")
public class StudentRequestController {

    @GetMapping(path = "/requests")
    public Map<String, Object> getRequestsForConnections () {
        Map<String, Object> response = new HashMap<>();

        response.put("ok", false);

        return response;
    }    
}
