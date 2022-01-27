package com.abderrahmane.elearning.socialservice.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MessageResolver {
    @Autowired
    private MessageSource messageSource;

    public Map<String, Object> getErrorResponseBody (Errors errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("ok", false);
        body.put("errors", this.resolveErrors(errors));

        return body;
    }

    public List<String> resolveErrors (Errors errors) {
        return errors.getAllErrors().stream().map(error -> messageSource.getMessage(error, Locale.getDefault())).toList();
    }
}
