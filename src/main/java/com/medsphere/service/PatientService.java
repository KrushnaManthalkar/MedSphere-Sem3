package com.medsphere.service;

import com.medsphere.dto.PatientForm;
import com.medsphere.entity.Patient;
import java.util.List;

public interface PatientService {
    Patient registerPatient(PatientForm patientForm);
    Patient updatePatient(Long id, PatientForm patientForm);
    Patient getPatientById(Long id);
    List<Patient> searchPatients(String search);
    PatientForm getPatientForm(Long id);
}
