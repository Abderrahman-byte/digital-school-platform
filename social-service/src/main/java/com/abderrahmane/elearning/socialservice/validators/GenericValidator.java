package com.abderrahmane.elearning.socialservice.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Validator;

public abstract class GenericValidator implements Validator {
    private List<String> allowedFields = new ArrayList<>();
    private List<String> requiredFields = new ArrayList<>();

    public void addAllowedFields (String ...fields) {
        this.allowedFields.addAll(List.of(fields));
    }

    public void addRequiredFields (String ...fields) {
        this.requiredFields.addAll(List.of(fields));
    }

    protected List<String> getAllowedFields() {
        return allowedFields;
    }

    protected List<String> getRequiredFields() {
        return requiredFields;
    }
}
