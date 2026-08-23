package com.medsphere.repository;

import com.medsphere.entity.Appointment;
import com.medsphere.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // All appointments ordered by date and time
    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();

    // Appointments of a particular doctor
    List<Appointment> findByDoctorIdOrderByAppointmentDateAscAppointmentTimeAsc(Long doctorId);

    // Doctor's appointments for a particular date
    List<Appointment> findByDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(
            Long doctorId,
            LocalDate appointmentDate
    );

    // Appointments for a particular date
    List<Appointment> findByAppointmentDateOrderByAppointmentTimeAsc(
            LocalDate appointmentDate
    );

    // Appointments according to status
    List<Appointment> findByStatusOrderByAppointmentDateAscAppointmentTimeAsc(
            AppointmentStatus status
    );

    // Prevent same doctor from being booked twice at the same date and time
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );
}