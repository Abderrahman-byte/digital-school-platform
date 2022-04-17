package com.abderrahmane.elearning.schoolservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapTeacherSchool;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.SchoolTeacher;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;
import com.abderrahmane.elearning.common.repositories.SchoolDAO;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.schoolservice.validators.AcceptTeacherFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/${api.version}/requests")
public class RequestsController {
    @Autowired
    private MapTeacherSchool teacherSchoolConverter;

    @Autowired
    private AcceptTeacherFormValidator acceptTeacherFormValidator;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @Autowired
    private SchoolDAO schoolDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, Object> getRequests (@RequestAttribute("account") Account account) {
        Map<String, Object> response = new HashMap<>();
        List<SchoolTeacher> teachers = account.getSchoolProfile().getTeachers().stream().filter(teacher -> !teacher.isVerified() && teacher.getEndedDate() == null).toList();

        response.put("ok", true);
        response.put("data", teacherSchoolConverter.convertList(teachers));

        return response;
    }

    @PostMapping(path = "/accept")
    public ResponseEntity<Map<String, Object>> acceptRequest (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(body, "acceptTeacher");
        boolean accepted = false;

        acceptTeacherFormValidator.validate(body, errors);
        
        if (errors.hasErrors()) {
            return new ResponseEntity<Map<String, Object>>(messageResolver.constructErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }   
    
        try {
            // TODO : Should send notification to teacher
            accepted = schoolDAO.acceptTeacher(account.getId(), (String)body.get("id"));
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "]");
            System.out.println(ex.getMessage());
        }
        
        
        response.put("ok", accepted);
        return new ResponseEntity<Map<String, Object>>(response, accepted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/reject", params = "id")
    public Map<String, Object> rejectTeacher (@RequestAttribute("account") Account account, @RequestParam("id") String teacherId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // TODO : Should send notification to teacher
            boolean deleted = profileDAO.deleteTeacherSchool(teacherId, account.getId());
            response.put("ok", deleted);
        } catch (Exception ex) {
            System.out.print("[" + ex.getClass().getName() + "]");
            System.out.println(ex.getMessage());
            response.put("ok", false);
        }


        return response;
    }
}
