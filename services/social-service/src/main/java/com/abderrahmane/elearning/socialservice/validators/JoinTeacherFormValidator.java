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

        this.checkStringValues(form, errors, List.of("id", "string"));
    }
}
