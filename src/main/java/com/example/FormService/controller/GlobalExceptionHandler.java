package com.example.FormService.controller;

import com.example.FormService.Exceptions.DuplicateNameException;
import com.example.FormService.Exceptions.InvalidNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleInvalidNameException(InvalidNameException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateNameException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleDuplicateNameException(DuplicateNameException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
