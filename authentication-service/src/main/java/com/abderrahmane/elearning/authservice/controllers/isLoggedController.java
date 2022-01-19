package com.abderrahmane.elearning.authservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isLoggedIn")
public class isLoggedController {
    @RequestMapping("")
    public String handleGetRequest () {
        return "Authentication is not supported yet";
    }
}
