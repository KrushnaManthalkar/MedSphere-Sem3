package com.medsphere.service.impl;

import com.medsphere.dto.PatientForm;
import com.medsphere.entity.Patient;
import com.medsphere.exception.PatientNotFoundException;
import com.medsphere.repository.PatientRepository;
import com.medsphere.service.PatientService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public synchronized Patient registerPatient(PatientForm patientForm) {
        Patient patient = new Patient();
        copyFormToPatient(patientForm, patient);
        patient.setPatientCode(generateNextPatientCode());
        patient.setRegistrationDate(LocalDate.now());
        try {
            return patientRepository.save(patient);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("Unable to generate a unique patient code. Please try again.", exception);
        }
    }

    @Override
    public Patient updatePatient(Long id, PatientForm patientForm) {
        Patient patient = getPatientById(id);
        copyFormToPatient(patientForm, patient);
        // patientCode and registrationDate are intentionally never copied from the form.
        return patientRepository.save(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> searchPatients(String search) {
        if (search == null || search.isBlank()) {
            return patientRepository.findAll().stream()
                    .sorted(Comparator.comparing(Patient::getRegistrationDate).reversed()
                            .thenComparing(Patient::getPatientCode))
                    .toList();
        }
        String value = search.trim();
        return patientRepository.findByPatientCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                value, value, value);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientForm getPatientForm(Long id) {
        Patient patient = getPatientById(id);
        PatientForm form = new PatientForm();
        form.setName(patient.getName());
        form.setDateOfBirth(patient.getDateOfBirth());
        form.setGender(patient.getGender());
        form.setBloodGroup(patient.getBloodGroup());
        form.setPhone(patient.getPhone());
        form.setEmail(patient.getEmail());
        form.setAddress(patient.getAddress());
        form.setEmergencyContact(patient.getEmergencyContact());
        return form;
    }

    private String generateNextPatientCode() {
        String prefix = "MSP-" + LocalDate.now().getYear() + "-";
        int nextNumber = patientRepository.findTopByPatientCodeStartingWithOrderByPatientCodeDesc(prefix)
                .map(patient -> Integer.parseInt(patient.getPatientCode().substring(prefix.length())) + 1)
                .orElse(1);
        return prefix + String.format("%06d", nextNumber);
    }

    private void copyFormToPatient(PatientForm form, Patient patient) {
        patient.setName(form.getName().trim());
        patient.setDateOfBirth(form.getDateOfBirth());
        patient.setGender(form.getGender().trim());
        patient.setBloodGroup(blankToNull(form.getBloodGroup()));
        patient.setPhone(form.getPhone().trim());
        patient.setEmail(blankToNull(form.getEmail()));
        patient.setAddress(blankToNull(form.getAddress()));
        patient.setEmergencyContact(blankToNull(form.getEmergencyContact()));
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
