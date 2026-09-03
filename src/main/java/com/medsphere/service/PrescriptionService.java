package com.medsphere.service;

import com.medsphere.dto.PrescriptionForm;
import com.medsphere.entity.Prescription;

import java.util.List;

public interface PrescriptionService {

    Prescription createPrescription(Long consultationId, PrescriptionForm form);

    List<Prescription> getPrescriptionsByConsultationId(Long consultationId);
}
