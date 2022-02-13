package com.abderrahmane.elearning.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

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
        return this.resolveList(errors.getFieldErrors().stream().map(error -> (ObjectError)error).toList());
    }

    public List<String> resolveGlobalErrors (Errors errors) {
        return this.resolveList(errors.getGlobalErrors());
    }

    private List<String> resolveList (List<ObjectError> errors) {
        return errors.stream().map(errorField -> {
            return messageSource.getMessage(errorField, Locale.getDefault());
        }).toList();
    }
}
