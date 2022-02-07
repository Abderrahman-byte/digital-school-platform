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

// TODO : Must give more information about teacher like : created_date, ended_date

@RestController
@RequestMapping("/api/v1/archived")
public class GetArchived {
    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getArchived (@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        List<TeacherProfile> teachers = account.getSchoolProfile().getTeachers().stream().filter(teacher -> teacher.getEndedDate() != null).map(teacher -> teacher.getTeacher()).toList();

        response.put("data", teacherProfileConverter.convertList(teachers));
        response.put("ok", true);

        return response;
    }
}