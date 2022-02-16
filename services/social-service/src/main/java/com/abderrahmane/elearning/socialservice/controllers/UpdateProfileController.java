package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.socialservice.validators.SchoolProfileUpdateValidator;
import com.abderrahmane.elearning.socialservice.validators.TeacherProfileUpdateValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class UpdateProfileController {
    @Autowired
    private ErrorMessageResolver messageResolver;

    @Autowired
    private TeacherProfileUpdateValidator teacherProfileUpdateValidator;

    @Autowired
    private SchoolProfileUpdateValidator schoolProfileUpdateValidator;

    @Autowired
    private ProfileDAO profileDAO;

    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> handlePut (@RequestBody Map<String, Object> body, @RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(body, this.getObjectName(account.getAccountType()));

        // Check if the profile has been created
        if (!this.checkProfile(account, errors)) return this.messageResolver.constructErrorResponse(errors);
        
        if (account.getAccountType().equals(AccountType.TEACHER)) {
            boolean updated = this.updateTeacherProfile(body, account, errors);
            response.put("ok", updated);
        } else if (account.getAccountType().equals(AccountType.SCHOOL)) {
            boolean updated = this.updateSchoolProfile(body, account, errors);
            response.put("ok", updated);
        } else {
            response.put("ok", false);
            response.put("errors", List.of("operation_not_supported"));
            return response;
        }
        
        if (errors.hasErrors()) return this.messageResolver.constructErrorResponse(errors);
        
        return response;
    }

    private boolean checkProfile (Account account, Errors errors) {
        boolean teacherProfileIncomplet = account.getAccountType().equals(AccountType.TEACHER) && account.getTeacherProfile() == null;
        boolean schoolProfileIncomplet = account.getAccountType().equals(AccountType.SCHOOL) && account.getSchoolProfile() == null;
        boolean studentProfileIncomplet = account.getAccountType().equals(AccountType.STUDENT) && account.getStudentProfile() == null;

        if (teacherProfileIncomplet && schoolProfileIncomplet && studentProfileIncomplet) {
            errors.reject("incompleteProfile");
            return false;
        }

        return true;
    }

    private String getObjectName (AccountType accountType) {
        return accountType.toString().toLowerCase() + "Profile";
    } 

    private boolean updateTeacherProfile (Map<String, Object> body, Account account, MapBindingResult errors) {
        this.teacherProfileUpdateValidator.validate(body, errors);

        if (errors.hasErrors()) return false;

        try {
            return profileDAO.updateTeacherProfile(body, account.getId());
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "] ");
            System.out.println(ex.getMessage());
        }

        return false;
    }

    private boolean updateSchoolProfile (Map<String, Object> body, Account account, MapBindingResult errors) {
        this.schoolProfileUpdateValidator.validate(body, errors);

        if (errors.hasErrors()) return false;

        try {
            return profileDAO.updateSchoolProfile(body, account.getId());
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "] ");
            System.out.println(ex.getMessage());
        }

        return false;
    }

    // private void updateStudentProfile (Map<String, Object> body, Account account, MapBindingResult errors) {

    // }
}
