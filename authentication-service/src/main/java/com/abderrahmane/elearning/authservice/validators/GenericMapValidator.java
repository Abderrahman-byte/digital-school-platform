package com.abderrahmane.elearning.authservice.validators;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

public abstract class GenericMapValidator extends GenericValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    public void checkRequiredFields(Map<String, Object> data, Errors errors) {
        for (String field : this.getRequiredFields()) {
            if (!data.containsKey(field) || data.get(field).equals("")) {
                errors.rejectValue(field, "requiredField", new Object[] { field }, "The " + field + " field is required.");
            }
        }
    }

    public void checkAllowedFields(Map<String, Object> data, Errors errors) {
        List<String> allowed = this.getAllowedFields();
        List<String> required = this.getRequiredFields();

        for (String field : data.keySet()) {
            if (!allowed.contains(field) && !required.contains(field)) {
                errors.rejectValue(field, "notAllowedField", new Object[] { field }, "The " + field + " field is not allowed.");
            }
        }
    }
}