package com.abderrahmane.elearning.socialservice.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;
import com.abderrahmane.elearning.common.models.City;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.common.repositories.GeoDAO;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;
import com.abderrahmane.elearning.socialservice.validators.SchoolProfilCreationValidator;
import com.abderrahmane.elearning.socialservice.validators.StudentProfileCreationValidator;
import com.abderrahmane.elearning.socialservice.validators.TeacherProfilCreationValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}/profile")
public class CreateProfileController {
    @Autowired
    private ProfileDAO profilDAO;

    @Autowired
    private GeoDAO geoDAO;

    @Autowired
    private SchoolProfilCreationValidator schoolProfilValidator;

    @Autowired
    private TeacherProfilCreationValidator teacherProfilValidator;

    @Autowired
    private StudentProfileCreationValidator studentProfileValidator;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> handlePost(@RequestBody Map<String, Object> body, @RequestAttribute("account") Account account) {
        AccountType accountType = account.getAccountType();

        if (accountType.equals(AccountType.SCHOOL)) return this.createSchoolProfile(body, account);
        else if (accountType.equals(AccountType.TEACHER)) return this.createTeacherProfil(body, account);
        return this.createStudentProfile(body, account);
    }   
 
    
    private Map<String, Object> createSchoolProfile (Map<String, Object> body, Account account) {
        MapBindingResult errors = new MapBindingResult(body, "schoolProfile");
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        
        if (account.getSchoolProfile() != null) {
            response.put("ok", false);
            response.put("errors", List.of("profile_already_exists"));
            return response;
        }
        
        schoolProfilValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.constructErrorResponse(errors);

        City location = geoDAO.getCity((Integer)body.get("cityId"));

        if (location == null) {
            response.put("ok", false);
            response.put("errors", List.of("unexisting_location"));
            return response;
        }

        profilDAO.createSchoolProfil(
            (String) body.get("name"), 
            (String) body.get("overview"),
            (String) body.get("subtitle"), 
            account, 
            location
        );

        return response;
    }

    private Map<String, Object> createTeacherProfil (Map<String, Object> body, Account account) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(body, "teacherProfile");

        response.put("ok", true);

        if (account.getTeacherProfile() != null) {
            response.put("ok", false);
            response.put("errors", List.of("profile_already_exists"));
            return response;
        }

        teacherProfilValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.constructErrorResponse(errors);

        City location = geoDAO.getCity((Integer)body.get("cityId"));

        if (location == null) {
            response.put("ok", false);
            response.put("errors", List.of("unexisting_location"));
            return response;
        }

        profilDAO.createTeacherProfil(
            (String)body.get("firstName"), 
            (String)body.get("lastName"), 
            (String)body.get("title"), 
            (String)body.get("bio"), 
            account, 
            location
        );

        return response;
    }

    public Map<String, Object> createStudentProfile (Map<String, Object> body, Account account) {
        MapBindingResult errors = new MapBindingResult(body, "studentProfile");
        Map<String, Object> response = new HashMap<>();

        response.put("ok", true);

        if (account.getStudentProfile() != null) {
            response.put("ok", false);
            response.put("errors", List.of("profile_already_exists"));
            return response;
        }

        studentProfileValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.constructErrorResponse(errors);

        City location = geoDAO.getCity((Integer)body.get("cityId"));

        if (location == null) {
            response.put("ok", false);
            response.put("errors", List.of("unexisting_location"));
            return response;
        }

        profilDAO.creatStudentProfile(
            (String)body.get("firstName"),
            (String)body.get("lastName"),
            (Calendar)body.get("dayOfBirth"),
            location,
            account
        );

        return response;
    }
    
}
