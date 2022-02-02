package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class JoinTeacherFormValidator extends GenericMapValidator {
    public JoinTeacherFormValidator() {
        this.addRequiredFields("id", "title");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> form = (Map<String, Object>) target;

        this.checkRequiredFields(form, errors);
        this.checkAllowedFields(form, errors);

        if (errors.hasErrors()) return;

        List.of("id", "title").forEach(field -> {
            if (form.containsKey(field) && !form.get(field).getClass().equals(String.class)) {
                errors.rejectValue(field, "invalidType");
            } else if (form.containsKey(field) && ((String) form.get(field)).length() <= 0) {
                errors.rejectValue(field, "invalidValue");
            }
        });
    }
}
