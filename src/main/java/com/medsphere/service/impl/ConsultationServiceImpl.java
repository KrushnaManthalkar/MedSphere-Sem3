package com.medsphere.service.impl;

import com.medsphere.dto.ConsultationForm;
import com.medsphere.entity.Appointment;
import com.medsphere.entity.Consultation;
import com.medsphere.repository.AppointmentRepository;
import com.medsphere.repository.ConsultationRepository;
import com.medsphere.service.ConsultationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;

    public ConsultationServiceImpl(
            ConsultationRepository consultationRepository,
            AppointmentRepository appointmentRepository) {

        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Consultation createConsultation(
            Long appointmentId,
            ConsultationForm form) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Appointment not found."));

        if (consultationRepository.findByAppointmentId(appointmentId).isPresent()) {
            throw new IllegalStateException(
                    "A consultation already exists for this appointment.");
        }

        Consultation consultation = new Consultation();

        consultation.setAppointment(appointment);
        consultation.setSymptoms(form.getSymptoms());
        consultation.setDiagnosis(form.getDiagnosis());
        consultation.setNotes(form.getNotes());

        LocalDate consultationDate = form.getConsultationDate();

        if (consultationDate == null) {
            consultationDate = LocalDate.now();
        }

        consultation.setConsultationDate(consultationDate);

        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation getConsultationByAppointmentId(
            Long appointmentId) {

        return consultationRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Consultation not found."));
    }
}