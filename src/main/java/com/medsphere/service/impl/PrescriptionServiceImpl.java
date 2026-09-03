package com.medsphere.service.impl;

import com.medsphere.dto.PrescriptionForm;
import com.medsphere.entity.Consultation;
import com.medsphere.entity.Prescription;
import com.medsphere.repository.ConsultationRepository;
import com.medsphere.repository.PrescriptionRepository;
import com.medsphere.service.PrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;

    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            ConsultationRepository consultationRepository) {

        this.prescriptionRepository = prescriptionRepository;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public Prescription createPrescription(
            Long consultationId,
            PrescriptionForm form) {

        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Consultation not found: " + consultationId));

        Prescription prescription = new Prescription();

        prescription.setConsultation(consultation);
        prescription.setMedicineName(form.getMedicineName().trim());
        prescription.setDosage(form.getDosage().trim());
        prescription.setFrequency(form.getFrequency().trim());
        prescription.setDuration(form.getDuration().trim());
        prescription.setInstructions(blankToNull(form.getInstructions()));

        return prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescription> getPrescriptionsByConsultationId(
            Long consultationId) {

        return prescriptionRepository.findByConsultationId(consultationId);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim();
    }
}