package com.abderrahmane.elearning.schoolservice.validators;

import java.util.Map;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AcceptTeacherFormValidator extends GenericMapValidator{
    public AcceptTeacherFormValidator () {
        this.addRequiredFields("id");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkAllowedFields(data, errors);
        this.checkRequiredFields(data, errors);

        if (errors.hasErrors()) return;

        if (!data.get("id").getClass().equals(String.class)) {
            errors.rejectValue("id", "invalidType");
        } else if (((String)data.get("id")).length() <= 0) {
            errors.rejectValue("id", "invalidValue");
        }
    }
}
