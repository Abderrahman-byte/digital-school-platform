package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.authservice.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.authservice.validators.RegisterFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterController {
    @Autowired
    private RegisterFormValidator validator;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> handlePost(@RequestBody Map<String, Object> body) {
        MapBindingResult errors = new MapBindingResult(body, "register");
        validator.validate(body, errors);

        if (errors.hasErrors()) return constructErrorResponse(errors);

        return new HashMap<String, Object>();
    }

    private Map<String, Object> constructErrorResponse(Errors errors) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("errors", messageResolver.resolveField(errors));
        response.put("ok", false);

        return response;
    }
}
