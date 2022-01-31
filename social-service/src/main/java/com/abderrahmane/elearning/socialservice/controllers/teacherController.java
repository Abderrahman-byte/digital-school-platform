package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.TeacherProfile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO : Verify if profile is complete

@RestController
@RequestMapping(path = "/api/teacher")
public class teacherController {
    @GetMapping(path = "/schools")
    public Map<String, Object> getTeacherSchoolList (@RequestAttribute(name = "account") Account account) {
        Optional<TeacherProfile> teacherProfile = Optional.ofNullable(account.getTeacherProfil());

        teacherProfile.ifPresent(profil -> {
            System.out.println("Schools -> " + profil.getSchooles().size());
        });
        

        return new HashMap<>();
    }
}
