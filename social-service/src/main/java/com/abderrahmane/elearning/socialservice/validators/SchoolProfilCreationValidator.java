package com.abderrahmane.elearning.socialservice.validators;

import java.util.Map;

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

        if (!data.get("name").getClass().equals(String.class))
            errors.rejectValue("name", "invalid_type");
        if (!data.get("cityId").getClass().equals(Integer.class))
            errors.rejectValue("cityId", "invalid_type");
        if (data.containsKey("subtitle") && !data.get("subtitle").getClass().equals(String.class))
            errors.rejectValue("subtitle", "invalid_type");
        if (data.containsKey("overview") && !data.get("overview").getClass().equals(String.class))
            errors.rejectValue("overview", "invalid_type");

        if (errors.hasErrors()) return;
        
        String name = (String)data.get("name");
        int cityId = (Integer)data.get("cityId");

        if (cityId <= 0) errors.rejectValue("cityId", "invalid_value");

        if (name.length() <= 3) errors.rejectValue("name", "invalid_value");
    }

}
