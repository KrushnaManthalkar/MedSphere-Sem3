package com.medsphere.service;

import com.medsphere.dto.ConsultationForm;
import com.medsphere.entity.Consultation;

public interface ConsultationService {

    Consultation createConsultation(
            Long appointmentId,
            ConsultationForm form
    );

    Consultation getConsultationByAppointmentId(
            Long appointmentId
    );
}