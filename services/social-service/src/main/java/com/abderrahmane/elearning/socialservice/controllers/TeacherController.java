package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.abderrahmane.elearning.common.converters.MapSchoolProfileConverter;
import com.abderrahmane.elearning.common.converters.MapStudentProfileConverter;
import com.abderrahmane.elearning.common.converters.StringDateConverter;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;
import com.abderrahmane.elearning.common.models.RequestForConnection;
import com.abderrahmane.elearning.common.models.SchoolTeacher;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;
import com.abderrahmane.elearning.socialservice.validators.JoinTeacherFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/teacher")
public class TeacherController {
    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private StringDateConverter stringDateConverter;

    @Autowired
    private MapSchoolProfileConverter schoolProfileConverter;

    @Autowired
    private JoinTeacherFormValidator joinTeacherFormValidator;

    @Autowired
    private MapStudentProfileConverter studentProfileConverter;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @GetMapping(path = "/schools")
    public Map<String, Object> getTeacherSchoolList (@RequestAttribute(name = "account") Account account) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("ok", true);

        if (!checkTeacherAccount(response, account)) return response;
        
        List<Map<String, Object>> schools = account.getTeacherProfile().getSchooles().stream().map(school -> {
           Map<String, Object> schoolTeacherMap = new HashMap<>();

           schoolTeacherMap.put("verified", school.isVerified());
           schoolTeacherMap.put("title", school.getTitle());
           schoolTeacherMap.put("addedDate", stringDateConverter.convert(school.getCreatedDate()));
           schoolTeacherMap.put("endedDate", school.getEndedDate() != null ? stringDateConverter.convert(school.getEndedDate()) : null);
           schoolTeacherMap.put("school", schoolProfileConverter.convert(school.getSchool()));

           return schoolTeacherMap;
        }).toList();

        response.put("data", schools);

        return response;
    }

    @PostMapping("/join-school")
    public Map<String, Object> joinSchool (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(body, "joinSchool");

        // Check if the account is a teacher account and if the profile is complete
        if (!checkTeacherAccount(response, account)) return response;

        joinTeacherFormValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.constructErrorResponse(errors);

        // Check if the teacher is current in a school
        List<SchoolTeacher> schools = account.getTeacherProfile().getSchooles().stream().filter(schoolTeacher -> schoolTeacher.getEndedDate() == null).toList();
        Optional<SchoolTeacher> endedSchool = account.getTeacherProfile().getSchooles().stream().filter(schoolTeacher -> schoolTeacher.getEndedDate() != null && schoolTeacher.getSchoolId().equals((String)body.get("id"))).findFirst();

        if (schools.size() > 0) {
            response.put("ok", false);
            response.put("errors", List.of("teacher_already_in_school"));
            return response;
        }

        if (endedSchool.isPresent()){
            boolean joined = profileDAO.teacherRejoinSchool(account.getTeacherProfile().getId(), (String)body.get("id"), (String)body.get("title"));
            response.put("ok", joined);
            return response;
        }

        // FIXME : Error could be handled globaly
        // TODO : Send notification to school account
        try {
            boolean created = profileDAO.teacherJoinSchool(account.getId(), (String)body.get("id"), (String)body.get("title"));
            response.put("ok", created);
        } catch (DataIntegrityViolationException ex) {      
            response.put("ok", false);
            response.put("errors", List.of(this.translateException(ex)));
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "] ");
            System.out.println(ex.getMessage());
            response.put("ok", false);
            response.put("errors", List.of("unknown_error")); 
        }

        return response;
    }

    @DeleteMapping(path = "/leave-school", params = "id")
    public Map<String, Object> leaveSchool (@RequestAttribute("account") Account account, @RequestParam(name = "id") String schoolId) {
        Map<String, Object> response = new HashMap<>();

        response.put("ok", true);

        if (!checkTeacherAccount(response, account)) return response;

        List<SchoolTeacher> schools = account.getTeacherProfile().getSchooles().stream().filter(schoolTeacher -> {
            return schoolTeacher.getEndedDate() == null && schoolTeacher.getSchool().getId().equals(schoolId);
        }).toList();

        if (schools.size() <= 0) {
            response.put("ok", false);
            response.put("errors", List.of("no_school_joined"));
            return response;
        }

        SchoolTeacher schoolTeacher = schools.get(0);

        if (!schoolTeacher.isVerified()) {
            boolean deleted = profileDAO.deleteTeacherSchool(account.getTeacherProfile().getId(), schoolId);
            response.put("ok", deleted);
        } else {
            boolean ended = profileDAO.endTeacherSchool(account.getTeacherProfile().getId(), schoolId);
            response.put("ok", ended);
        }

        return response;
    }

    // TODO : check if student already connected with teacher
    @GetMapping(path = "/requests")
    public Map<String, Object> getRequestsForConnections (@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();

        if (!checkTeacherAccount(response, account)) return response;

        response.put("ok", true);
        response.put("data", account.getTeacherProfile().getRequests().stream().map(request -> {
            Map<String, Object> requestObject = studentProfileConverter.convert(request.getStudentProfile());
            requestObject.put("id", request.getId());

            return requestObject;
        }).toList());

        return response;
    }

    @PostMapping(path = "/requests/accept")
    public Map<String, Object> acceptConnectionRequest (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        RequestForConnection requestForConnection = null;

        response.put("ok", true);

        if (!checkTeacherAccount(response, account)) return response;

        if (!body.containsKey("id") || !body.get("id").getClass().equals(String.class) || ((String)body.get("id")).length() <= 0) {
            response.put("ok", false);
            response.put("errors", List.of("Id field is required"));
            return response;
        }

        requestForConnection = profileDAO.getRequestForConnection((String)body.get("id"), account.getId());

        if (requestForConnection == null) {
            response.put("ok", false);
            response.put("errors", List.of("not_found"));
        }

        if (!profileDAO.deleteRequestForConnection((String)body.get("id"))) {
            response.put("ok", false);
            return response;
        }
        
        try {
            profileDAO.createTeacherStudentConnection(account.getId(), requestForConnection.getStudentProfile().getId());
        } catch (DataIntegrityViolationException ex) {
            response.put("ok", false);
            response.put("errors", List.of(this.translateException(ex)));
        }
        
        return response;
    }

    private boolean checkTeacherAccount (Map<String, Object> response, Account account) {
        if (!account.getAccountType().equals(AccountType.TEACHER)) {
            response.put("ok", false);
            response.put("errors", List.of("unauthorized"));
            return false;
        }

        if (account.getTeacherProfile() == null) {
            response.put("ok", false);
            response.put("errors", List.of("uncompleted_profile"));
            return false;
        }

        return true;
    }

    private String translateException (DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        if (message.contains("teacher_school_pkey")) {
            return "school_already_joined";
        } else if (message.contains("teacher_school_school_id_fkey")) {
            return "school_does_not_exist";
        } else if (message.contains("connection_pkey")) {
            return "connection_already_exists";
        }

        System.out.println("[UNKOWN-ERROR] " + ex.getMessage());

        return "unknown_error";
    }
}
