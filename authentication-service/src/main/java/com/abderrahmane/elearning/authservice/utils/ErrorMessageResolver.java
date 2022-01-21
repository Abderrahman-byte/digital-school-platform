package com.abderrahmane.elearning.authservice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ErrorMessageResolver {
    @Autowired
    private MessageSource messageSource;

    public Map<String, Object> constructErrorResponse(Errors errors) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("errors", this.resolveErrors(errors));
        response.put("ok", false);

        return response;
    }

    public List<String> resolveErrors (Errors errors) {
        List<String> errorsMessages = new ArrayList<String>();

        errorsMessages.addAll(this.resolveFieldErrors(errors));
        errorsMessages.addAll(this.resolveGlobalErrors(errors));

        return errorsMessages;
    }

    public List<String> resolveFieldErrors (Errors errors) {
        return errors.getFieldErrors().stream().map(errorField -> {
            return messageSource.getMessage(errorField, Locale.getDefault());
        }).toList();
    }

    public List<String> resolveGlobalErrors (Errors errors) {
        return errors.getGlobalErrors().stream().map(errorField -> {
            return messageSource.getMessage(errorField, Locale.getDefault());
        }).toList();
    }
}
