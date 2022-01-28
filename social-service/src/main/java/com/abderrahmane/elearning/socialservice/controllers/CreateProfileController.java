package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.AccountType;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.repositories.GeoDAO;
import com.abderrahmane.elearning.socialservice.repositories.ProfilDAO;
import com.abderrahmane.elearning.socialservice.utils.MessageResolver;
import com.abderrahmane.elearning.socialservice.validators.SchoolProfilCreationValidator;
import com.abderrahmane.elearning.socialservice.validators.TeacherProfilCreationValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class CreateProfileController {
    @Autowired
    private ProfilDAO profilDAO;

    @Autowired
    private GeoDAO geoDAO;

    @Autowired
    private SchoolProfilCreationValidator schoolProfilValidator;

    @Autowired
    private TeacherProfilCreationValidator teacherProfilValidator;

    @Autowired
    private MessageResolver messageResolver;

    // TODO : this function must be refactored
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> handlePost(@RequestBody Map<String, Object> body, @RequestAttribute("account") Account account) {
        AccountType accountType = account.getAccountType();

        if (accountType.equals(AccountType.SCHOOL)) {
            Map<String, Object> responseBody = this.createSchoolProfile(body, account);
            return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.OK);
        } else if (accountType.equals(AccountType.TEACHER)) {
            Map<String, Object> responseBody = this.createTeacherProfil(body, account);
            return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.OK);
        }

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> message = new HashMap<>();
        message.put("ok", false);
        message.put("errors", List.of("not_implemented_yet"));
        headers.set("Content-Type", "application/json");

        return new ResponseEntity<Map<String, Object>>(message, headers, HttpStatus.OK);
    }   
 
    
    private Map<String, Object> createSchoolProfile (Map<String, Object> body, Account account) {
        MapBindingResult errors = new MapBindingResult(body, "schoolProfil");
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        
        if (account.getSchoolProfil() != null) {
            response.put("ok", false);
            response.put("errors", List.of("profil_already_exists"));
            return response;
        }
        
        schoolProfilValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.getErrorResponseBody(errors);

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
        MapBindingResult errors = new MapBindingResult(body, "teacherProfil");

        response.put("ok", true);

        if (account.getTeacherProfil() != null) {
            response.put("ok", false);
            response.put("errors", List.of("profil_already_exists"));
            return response;
        }

        teacherProfilValidator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.getErrorResponseBody(errors);

        // Must create teacher profil after validation


        return response;
    }
    
}
