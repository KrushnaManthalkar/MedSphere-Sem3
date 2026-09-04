package com.medsphere.service.impl;

import com.medsphere.dto.MedicalHistoryForm;
import com.medsphere.entity.MedicalHistory;
import com.medsphere.entity.Patient;
import com.medsphere.repository.MedicalHistoryRepository;
import com.medsphere.repository.PatientRepository;
import com.medsphere.service.MedicalHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;

    public MedicalHistoryServiceImpl(
            MedicalHistoryRepository medicalHistoryRepository,
            PatientRepository patientRepository) {

        this.medicalHistoryRepository = medicalHistoryRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public MedicalHistory createMedicalHistory(
            Long patientId,
            MedicalHistoryForm form) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found: " + patientId));

        MedicalHistory history = new MedicalHistory();

        history.setPatient(patient);
        history.setConditionName(form.getConditionName().trim());
        history.setDetails(blankToNull(form.getDetails()));
        history.setRecordDate(form.getRecordDate());

        return medicalHistoryRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> getMedicalHistoryByPatientId(
            Long patientId) {

        return medicalHistoryRepository
                .findByPatientIdOrderByRecordDateDesc(patientId);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim();
    }
}