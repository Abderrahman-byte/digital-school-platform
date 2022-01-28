package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

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

        List.of("firstName", "lastName", "title", "bio").forEach(field -> {
            if (data.containsKey(field) && !data.get(field).getClass().equals(String.class)) {
                errors.rejectValue(field, "invalidType");
            } else if (data.containsKey(field) && ((String)data.get(field)).length() <= 0) {
                errors.rejectValue(field, "invalidValue");
            }
        });

        if (!data.get("cityId").getClass().equals(Integer.class)) {
            errors.rejectValue("cityId", "invalidType");
        } else if ((Integer)data.get("cityId") <= 0) {
            errors.rejectValue("cityId", "invalidValue");
        }

        if (errors.hasErrors()) return;
    }    
}
