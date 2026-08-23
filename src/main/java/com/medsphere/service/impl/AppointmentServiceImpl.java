package com.medsphere.service.impl;

import com.medsphere.dto.AppointmentForm;
import com.medsphere.entity.Appointment;
import com.medsphere.entity.Doctor;
import com.medsphere.entity.Patient;
import com.medsphere.enums.AppointmentStatus;
import com.medsphere.repository.AppointmentRepository;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.PatientRepository;
import com.medsphere.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository
                .findAllByOrderByAppointmentDateAscAppointmentTimeAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Appointment not found: " + id));
    }

    @Override
    public Appointment createAppointment(AppointmentForm form) {

        Patient patient = patientRepository.findById(form.getPatientId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Selected patient was not found."));

        Doctor doctor = doctorRepository.findById(form.getDoctorId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Selected doctor was not found."));

        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                                doctor.getId(),
                                form.getAppointmentDate(),
                                form.getAppointmentTime()
                        );

        if (alreadyBooked) {
            throw new IllegalStateException(
                    "This doctor already has an appointment at the selected date and time."
            );
        }

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(form.getAppointmentDate());
        appointment.setAppointmentTime(form.getAppointmentTime());
        appointment.setReason(blankToNull(form.getReason()));
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointmentStatus(Long id, String status) {

        Appointment appointment = getAppointment(id);

        AppointmentStatus newStatus;

        try {
            newStatus = AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new IllegalArgumentException("Invalid appointment status.");
        }

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Only scheduled appointments can have their status changed."
            );
        }

        appointment.setStatus(newStatus);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository
                .findByDoctorIdOrderByAppointmentDateAscAppointmentTimeAsc(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getDoctorAppointmentsForDate(Long doctorId) {
        return appointmentRepository
                .findByDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(
                        doctorId,
                        LocalDate.now()
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> searchAppointments(String search, String status) {

        List<Appointment> appointments =
                appointmentRepository
                        .findAllByOrderByAppointmentDateAscAppointmentTimeAsc();

        if (search != null && !search.isBlank()) {

            String value = search.trim().toLowerCase();

            appointments = appointments.stream()
                    .filter(appointment ->
                            appointment.getPatient().getPatientCode()
                                    .toLowerCase().contains(value)
                                    || appointment.getPatient().getName()
                                    .toLowerCase().contains(value)
                                    || appointment.getDoctor().getUser().getFullName()
                                    .toLowerCase().contains(value)
                    )
                    .toList();
        }

        if (status != null && !status.isBlank()) {

            AppointmentStatus selectedStatus;

            try {
                selectedStatus =
                        AppointmentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException exception) {
                return List.of();
            }

            appointments = appointments.stream()
                    .filter(appointment ->
                            appointment.getStatus() == selectedStatus)
                    .toList();
        }

        return appointments;
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim();
    }
}