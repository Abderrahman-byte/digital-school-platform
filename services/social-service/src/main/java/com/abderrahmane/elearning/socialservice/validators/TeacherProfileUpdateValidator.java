package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class TeacherProfileUpdateValidator extends GenericMapValidator {
    public TeacherProfileUpdateValidator () {
        this.addAllowedFields("firstName", "lastName", "title", "bio", "cityId");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkAllowedFields(data, errors);    

        if (errors.hasErrors()) return ;

        if (data.size() <= 0) errors.reject("invalidForm");

        if (errors.hasErrors()) return ;

        this.checkStringValues(data, errors, List.of("firstName", "lastName", "title", "bio"));

        this.checkPositiveIntegers(data, errors, List.of("cityId"));

        if (errors.hasErrors()) return;

        // TODO : normalize firstName to firstname in all forms

        if (data.containsKey("firstName")) {
            data.put("first_name", data.get("firstName"));
            data.remove("firstName");
        }

        if (data.containsKey("lastName")) {
            data.put("last_name", data.get("lastName"));
            data.remove("lastName");
        }
    }
}
