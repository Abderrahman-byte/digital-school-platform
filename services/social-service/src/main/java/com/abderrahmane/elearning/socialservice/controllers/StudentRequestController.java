package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.abderrahmane.elearning.common.converters.MapTeacherProfileConverter;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.StudentTeacherConnection;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.version}/student")
public class StudentRequestController {
    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    @Autowired
    private ProfileDAO profileDAO;

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

    // TODO : Should send notification to teacher
    @PostMapping(path = "/requests")
    public Map<String, Object> sendRequestForConnection (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        boolean created = false;

        if (!body.containsKey("id") || !body.get("id").getClass().equals(String.class) || ((String)body.get("id")).length() <= 0) {
            response.put("ok", false);
            response.put("errors", List.of("Id field is required"));
            return response;
        }

        Stream<StudentTeacherConnection> connections = account.getStudentProfile().getConnections().stream().filter(conn -> conn.getTeacherProfile().getId().equals(body.get("id")));

        if (connections.count() > 0) {
            response.put("ok", false);
            response.put("errors", List.of("connection_already_exists"));
            return response;
        }

        try {
            created = profileDAO.createRequestForConnection(account.getId(), (String)body.get("id"));
        } catch (DataIntegrityViolationException ex) {
            response.put("errors", List.of(this.translateSQLException(ex)));
        }
        
        response.put("ok", created);
        return response;
    }

    private String translateSQLException (DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        if (message.contains("request_for_connection_teacher_id_student_id_key")) {
            return "request_already_exists";
        } else if (message.contains("request_for_connection_teacher_id_fkey")) {
            return "teacher_doesnot_exist";
        }

        System.out.println("[UNKNOWN-ERROR] " + message);

        return "unknown_error";
    }
}
