package com.abderrahmane.elearning.schoolservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapTeacherSchool;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.SchoolTeacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}/archived")
public class GetArchivedController {
    @Autowired
    private MapTeacherSchool teacherProfileConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getArchived (@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        List<SchoolTeacher> teachers = account.getSchoolProfile().getTeachers().stream().filter(teacher -> teacher.getEndedDate() != null).toList();

        response.put("data", teacherProfileConverter.convertList(teachers));
        response.put("ok", true);

        return response;
    }
}