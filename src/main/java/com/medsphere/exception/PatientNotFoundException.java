package com.medsphere.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super("Patient with id " + id + " was not found.");
    }
}
