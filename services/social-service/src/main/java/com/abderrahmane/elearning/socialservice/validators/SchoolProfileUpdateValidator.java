package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SchoolProfileUpdateValidator extends GenericMapValidator {
    public SchoolProfileUpdateValidator () {
        this.addAllowedFields("name", "subtitle", "cityId", "overview");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkAllowedFields(data, errors);

        if (errors.hasErrors()) return;

        if (data.size() <= 0) errors.reject("invalidForm");

        if (errors.hasErrors()) return ;

        // TODO : allow subtitle and overview fields to be empty
        this.checkStringValues(data, errors, List.of("name", "subtitle", "overview"));
        this.checkPositiveIntegers(data, errors, List.of("cityId"));

        if (errors.hasErrors()) return;

        if (data.containsKey("name")) {
            String name = (String)data.get("name");

            if (name.length() <= 3) errors.rejectValue("name", "invalidValue");
        }

        if (data.containsKey("cityId")) {
            data.put("location", data.get("cityId"));
            data.remove("cityId");
        }

    }
}
