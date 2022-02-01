package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.socialservice.converters.JsonSchoolProfileConverter;
import com.abderrahmane.elearning.socialservice.converters.StringDateConverter;
import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/teacher")
public class teacherController {
    @Autowired
    StringDateConverter stringDateConverter;

    @Autowired
    JsonSchoolProfileConverter schoolProfileConverter;

    @GetMapping(path = "/schools")
    public Map<String, Object> getTeacherSchoolList (@RequestAttribute(name = "account") Account account) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("ok", true);

        if (!checkTeacherAccount(response, account)) return response;
        
        List<Map<String, Object>> schools = account.getTeacherProfil().getSchooles().stream().map(school -> {
           Map<String, Object> schoolTeacherMap = new HashMap<>();

           schoolTeacherMap.put("verified", school.getVerified());
           schoolTeacherMap.put("title", school.getTitle());
           schoolTeacherMap.put("addedDate", stringDateConverter.convert(school.getCreatedDate()));
           schoolTeacherMap.put("endedDate", school.getEndedDate() != null ? stringDateConverter.convert(school.getEndedDate()) : null);
           schoolTeacherMap.put("school", schoolProfileConverter.convert(school.getSchool()));

           return schoolTeacherMap;
        }).toList();

        response.put("data", schools);

        return response;
    }

    private boolean checkTeacherAccount (Map<String, Object> response, Account account) {
        if (!account.getAccountType().equals(AccountType.TEACHER)) {
            response.put("ok", false);
            response.put("errors", List.of("unauthorized"));
            return false;
        }

        if (account.getTeacherProfil() == null) {
            response.put("ok", false);
            response.put("errors", List.of("uncompleted_profile"));
            return false;
        }

        return true;
    }
}
