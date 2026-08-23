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
        model.addAttribute("errorTitle", "Patient not found");
        model.addAttribute("errorMessage", "The requested patient record is not available.");
        return "error";
    }

    @ExceptionHandler({DataIntegrityViolationException.class, IllegalStateException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataConflict(RuntimeException exception, Model model) {
        model.addAttribute("errorTitle", "Unable to save patient");
        model.addAttribute("errorMessage", "The patient record could not be saved. Please review the information and try again.");
        return "error";
    }
}
