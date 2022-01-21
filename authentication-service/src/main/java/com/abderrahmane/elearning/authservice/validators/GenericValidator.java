package com.abderrahmane.elearning.authservice.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Validator;

public abstract class GenericValidator implements Validator {
    private List<String> requiredFields = new ArrayList<String>(); 
    private List<String> allowedFields = new ArrayList<String>(); 
    private List<String> disallowedFields = new ArrayList<String>(); 

    protected void addAllowedFields (String ...fields) {
        this.allowedFields.addAll(List.of(fields));
    }

    protected void addRequiredFields (String ...fields) {
        this.requiredFields.addAll(List.of(fields));
    }

    protected void addDisallowedFields (String ...fields) {
        this.disallowedFields.addAll(List.of(fields));
    }

    protected List<String> getRequiredFields() {
        return requiredFields;
    }

    protected List<String> getAllowedFields() {
        return allowedFields;
    }

    protected List<String> getDisallowedFields() {
        return disallowedFields;
    }
}
