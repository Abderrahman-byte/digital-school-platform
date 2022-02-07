package com.abderrahmane.elearning.schoolservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapTeacherProfileConverter;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.TeacherProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teachers")
public class GetVerifiedTeachers {
    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTeachers(@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        List<TeacherProfile> teachers = account.getSchoolProfile().getTeachers().stream().filter(teacher -> teacher.isVerified() && teacher.getEndedDate() == null).map(teacherSchool -> teacherSchool.getTeacher()).toList();

        response.put("data", teacherProfileConverter.convertList(teachers));
        response.put("ok", "true");

        return response;
    }
}