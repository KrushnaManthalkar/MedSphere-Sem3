package com.medsphere.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PatientNotFoundException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(RuntimeException exception, Model model) {
        model.addAttribute("errorTitle", "Record not found");
        model.addAttribute("errorMessage", exception.getMessage() != null
                ? exception.getMessage()
                : "The requested record is not available.");
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            Model model) {
        model.addAttribute("errorTitle", "Data conflict");
        model.addAttribute("errorMessage",
                "The request conflicts with existing data. Please check the record and try again.");
        return "error";
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleIllegalState(IllegalStateException exception, Model model) {
        model.addAttribute("errorTitle", "Operation not allowed");
        model.addAttribute("errorMessage", exception.getMessage() != null
                ? exception.getMessage()
                : "The requested operation cannot be completed in the current state.");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException exception, Model model) {
        model.addAttribute("errorTitle", "Invalid request");
        model.addAttribute("errorMessage", exception.getMessage() != null
                ? exception.getMessage()
                : "Please verify the requested record and try again.");
        return "error";
    }
}