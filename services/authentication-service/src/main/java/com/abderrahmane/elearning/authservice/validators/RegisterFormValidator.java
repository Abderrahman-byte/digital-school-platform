package com.abderrahmane.elearning.authservice.validators;

import java.util.Map;
import java.util.regex.Pattern;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RegisterFormValidator extends GenericMapValidator {
    Pattern validUsernamePattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{3,}$");
    Pattern validEmailPattern = Pattern.compile("^[^@]{3,}@[^@]+\\.[A-Za-z]{2,3}$");
    Pattern validPasswordPattern = Pattern.compile("(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?=.{8,})");
    Pattern validAccountType = Pattern.compile("^(student|teacher|school)$", Pattern.CASE_INSENSITIVE);

    public RegisterFormValidator() {
        this.addRequiredFields("username", "email", "password", "password2");
        this.addAllowedFields("username", "email", "password", "password2", "accountType");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        Map<String, Object> form = (Map<String, Object>) target;

        this.checkRequiredFields(form, errors);
        this.checkAllowedFields(form, errors);

        if (errors.hasErrors()) return;
        
        for (String field : form.keySet()) {
            if (!form.get(field).getClass().equals(String.class)) {
                errors.rejectValue(field, "invalidType", new Object[] { field }, "Invalid field type, " + field + " must be a string");
            }
        }

        if (errors.hasErrors()) return;

        String username = (String)form.get("username");
        String email = (String)form.get("email");
        String password = (String)form.get("password");
        String password2 = (String)form.get("password2");
        String accountType = (String)form.get("accountType");

        if (username.length() <= 3 || !validUsernamePattern.matcher(username).matches()) errors.rejectValue("username", "invalidField");
        if (!validEmailPattern.matcher(email).matches()) errors.rejectValue("email", "invalidField");
        if (!validPasswordPattern.matcher(password).find()) errors.rejectValue("password", "invalidField");
        if (!password.equals(password2)) errors.rejectValue("password2", "noMatch");
        if (accountType != null && !validAccountType.matcher(accountType).matches()) errors.rejectValue("accountType", "invalidField");
    }
}
