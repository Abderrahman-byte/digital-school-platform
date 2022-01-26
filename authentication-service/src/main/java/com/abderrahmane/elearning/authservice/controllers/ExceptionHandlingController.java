package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleGenericException (Exception ex) {
        System.out.println("EXCEPTION : ");

        ex.printStackTrace();

        return new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<?> handleMethodNotSupported (Exception ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("ok", false);
        body.put("errors", List.of("method_not_supported"));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
