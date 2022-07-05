package com.abderrahmane.elearning.socialservice.validators;

import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UpdateGroupValidator extends GenericMapValidator {
    public UpdateGroupValidator () {
        this.addAllowedFields("label", "description");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkAllowedFields(data, errors);
    }
}
