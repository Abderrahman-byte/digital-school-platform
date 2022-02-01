package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.socialservice.converters.JsonSchoolProfileConverter;
import com.abderrahmane.elearning.socialservice.converters.StringDateConverter;
import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.AccountType;
import com.abderrahmane.elearning.socialservice.models.SchoolTeacher;
import com.abderrahmane.elearning.socialservice.repositories.ProfileDAO;
import com.abderrahmane.elearning.socialservice.utils.MessageResolver;
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

// TODO : Maybe allow teacher to join school again after leaving

@RestController
@RequestMapping(path = "/api/v1/teacher")
public class TeacherController {
    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private StringDateConverter stringDateConverter;

    @Autowired
    private JsonSchoolProfileConverter schoolProfileConverter;

    @Autowired
    private JoinTeacherFormValidator joinTeacherFormValidator;

    @Autowired
    private MessageResolver messageResolver;

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

    @PostMapping("/join-school")
    public Map<String, Object> joinSchool (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(body, "joinSchool");

        // Check if the account is a teacher account and if the profile is complete
        if (!checkTeacherAccount(response, account)) return response;

        joinTeacherFormValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.getErrorResponseBody(errors);

        // Check if the teacher is current in a school
        List<SchoolTeacher> schools = account.getTeacherProfil().getSchooles().stream().filter(schoolTeacher -> schoolTeacher.getEndedDate() == null).toList();

        if (schools.size() > 0) {
            response.put("ok", false);
            response.put("errors", List.of("teacher_already_in_school"));
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

    private String translateException (DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        if (message.contains("teacher_school_pkey")) {
            return "school_already_joined";
        } else if (message.contains("teacher_school_school_id_fkey")) {
            return "school_does_not_exist";
        }

        System.out.println("[UNKOWN-ERROR] " + ex.getMessage());

        return "unknown_error";
    }
}
