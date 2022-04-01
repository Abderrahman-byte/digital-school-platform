package com.abderrahmane.elearning.common.validators;

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
            if (!data.containsKey(field) || data.get(field) == null || data.get(field).equals("")) {
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

    public void checkStringValues(Map<String, Object> data, Errors errors, List<String> fields) {
        fields.forEach(field -> {
            if (data.get(field) != null && !data.get(field).getClass().equals(String.class)) {
                errors.rejectValue(field, "invalidType");
            } else if (data.get(field) != null && ((String)data.get(field)).length() <= 0) {
                errors.rejectValue(field, "invalidValue");
            }
        });
    }

    public void checkPositiveIntegers (Map<String, Object> data, Errors errors, List<String> fields) {
        fields.forEach(field -> {
            if (data.get(field) != null && !data.get(field).getClass().equals(Integer.class)) {
                errors.rejectValue(field, "invalidType");
            } else if (data.get(field) != null && (Integer)data.get(field) <= 0) {
                errors.rejectValue(field, "invalidValue");
            }
        });
    }
}
