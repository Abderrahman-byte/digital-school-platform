package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SchoolProfilCreationValidator extends GenericMapValidator {
    public SchoolProfilCreationValidator() {
        this.addAllowedFields("name", "subtitle", "cityId", "overview");
        this.addRequiredFields("name", "cityId");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>) target;

        this.checkAllowedFields(data, errors);
        this.checkRequiredFields(data, errors);

        if (errors.hasErrors()) return;

        this.checkStringValues(data, errors, List.of("name", "subtitle", "overview"));
        this.checkPositiveIntegers(data, errors, List.of("cityId"));

        if (errors.hasErrors()) return;
        
        String name = (String)data.get("name");

        if (name.length() <= 3) errors.rejectValue("name", "invalidValue");
    }

}
