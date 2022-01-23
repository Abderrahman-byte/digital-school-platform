package com.abderrahmane.elearning.authservice.validators;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class LoginFormValidator extends GenericMapValidator {
    public LoginFormValidator () {
        this.addRequiredFields("username", "password");
        this.addAllowedFields("username", "password");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> form = (Map<String, Object>)target;

        checkAllowedFields(form, errors);
        checkRequiredFields(form, errors);

        if (errors.hasErrors()) return ;

        if (!form.get("username").getClass().equals(String.class)) errors.rejectValue("username", "invalidType");
        if (!form.get("password").getClass().equals(String.class)) errors.rejectValue("password", "invalidType");
    }
    
}
