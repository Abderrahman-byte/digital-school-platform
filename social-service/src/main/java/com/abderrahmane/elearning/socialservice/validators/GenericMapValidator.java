package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

public abstract class GenericMapValidator extends GenericValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    public void checkAllowedFields(Map<String, Object> target, Errors errors) {
        List<String> allowed = this.getAllowedFields();
        List<String> required = this.getRequiredFields();

        target.keySet().forEach(key -> {
            if (!allowed.contains(key) && !required.contains(key))
                errors.rejectValue(key, "notAllowedField", new String[] { key }, null);
        });
    }

    public void checkRequiredFields(Map<String, Object> target, Errors errors) {
        this.getRequiredFields().forEach(field -> {
            if (!target.containsKey(field) || target.get(field) == null)
                errors.rejectValue(field, "requiredField", new String[] { field }, null);
        });
    }
}
