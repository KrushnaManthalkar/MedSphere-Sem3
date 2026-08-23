package com.medsphere.service;

import com.medsphere.dto.AppointmentForm;
import com.medsphere.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment getAppointment(Long id);

    Appointment createAppointment(AppointmentForm form);

    Appointment updateAppointmentStatus(Long id, String status);

    List<Appointment> getDoctorAppointments(Long doctorId);

    List<Appointment> getDoctorAppointmentsForDate(Long doctorId);

    List<Appointment> searchAppointments(
            String search,
            String status
    );
}