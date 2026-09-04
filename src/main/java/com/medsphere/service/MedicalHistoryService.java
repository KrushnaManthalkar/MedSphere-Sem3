package com.medsphere.service;

import com.medsphere.dto.MedicalHistoryForm;
import com.medsphere.entity.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {

    MedicalHistory createMedicalHistory(
            Long patientId,
            MedicalHistoryForm form
    );

    List<MedicalHistory> getMedicalHistoryByPatientId(
            Long patientId
    );
}