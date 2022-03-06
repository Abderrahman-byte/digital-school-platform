package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapTeacherProfileConverter;
import com.abderrahmane.elearning.common.models.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.version}/student")
public class StudentRequestController {
    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    // TODO : Do pagination
    @GetMapping(path = "/requests")
    public Map<String, Object> getRequestsForConnections (@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();

        response.put("ok", true);
        response.put("data", account.getStudentProfile().getRequests().stream().map(rfc -> {
            Map<String, Object> rfcData = teacherProfileConverter.convert(rfc.getTeacherProfile());
            rfcData.put("id", rfc.getId());

            return rfcData;
        }).toList());

        return response;
    }    
}
