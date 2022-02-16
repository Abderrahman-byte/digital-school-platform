package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class TeacherProfilCreationValidator extends GenericMapValidator {
    public TeacherProfilCreationValidator () {
        this.addRequiredFields("firstName", "lastName", "title", "cityId");
        this.addAllowedFields("firstName", "lastName", "title", "cityId", "bio");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkRequiredFields(data, errors);
        this.checkAllowedFields(data, errors);

        if (errors.hasErrors()) return ;

        this.checkStringValues(data, errors, List.of("firstName", "lastName", "title", "bio"));
        this.checkPositiveIntegers(data, errors, List.of("cityId"));
    }    
}
