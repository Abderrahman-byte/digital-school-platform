package com.abderrahmane.elearning.schoolservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapTeacherSchool;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.SchoolTeacher;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}/teachers")
public class VerifiedTeachersController {
    @Autowired
    private MapTeacherSchool teacherProfileConverter;

    @Autowired
    private ProfileDAO profileDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTeachers(@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        List<SchoolTeacher> teachers = account.getSchoolProfile().getTeachers().stream().filter(teacher -> teacher.isVerified() && teacher.getEndedDate() == null).toList();

        response.put("data", teacherProfileConverter.convertList(teachers));
        response.put("success", "true");

        return response;
    }

    @DeleteMapping(params = "id")
    public Map<String, Object> kickTeacherFromSchool (@RequestAttribute("account") Account account, @RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = account.getSchoolProfile().getTeachers().stream().filter(teacherSchool -> teacherSchool.isVerified() && teacherSchool.getTeacherId().equals(id) && teacherSchool.getEndedDate() == null).count() > 0;

        if (!exists) {
            response.put("success", false);
            return response;
        }

        try {
            boolean ended = profileDAO.endTeacherSchool(id, account.getId());
            response.put("success", ended);
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "]");
            System.out.println(ex.getMessage());
            response.put("success", false);
        }

        return response;
    }
}