package com.abderrahmane.elearning.authservice.utils;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ErrorMessageResolver {
    @Autowired
    private MessageSource messageSource;

    public List<String> resolveField (Errors errors) {
        return errors.getFieldErrors().stream().map(errorField -> {
            return messageSource.getMessage(errorField, Locale.getDefault());
        }).toList();
    }
}
