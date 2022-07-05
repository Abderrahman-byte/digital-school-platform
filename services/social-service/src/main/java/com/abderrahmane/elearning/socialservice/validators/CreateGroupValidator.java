package com.abderrahmane.elearning.socialservice.validators;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CreateGroupValidator extends GenericMapValidator {
    public CreateGroupValidator () {
        this.addRequiredFields("label");
        this.addAllowedFields("description");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkRequiredFields(data, errors);
        this.checkRequiredFields(data, errors);
        this.checkStringValues(data, errors, List.of("label", "description"));
    }
}
