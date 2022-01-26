package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleGenericException (Exception ex) {
        System.out.println("EXCEPTION : ");

        ex.printStackTrace();

        return this.responseWithError("unknown_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<?> handleMethodNotSupported (Exception ex) {
        return this.responseWithError("method_not_supported");
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<?> handleHttpNotReadable (Exception ex) {
        return this.responseWithError(ex.getMessage());
    }

    private ResponseEntity<?> responseWithError(String errorMessage) {
        return this.responseWithError(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> responseWithError(String errorMessage, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();

        body.put("ok", false);
        body.put("errors", List.of(errorMessage));

        return new ResponseEntity<>(body, status);
    }
}
