package com.medsphere.controller;

import com.medsphere.entity.Appointment;
import com.medsphere.entity.Doctor;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.AppointmentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctors/appointments")
public class DoctorAppointmentDetailsController {

    private final AppointmentService appointmentService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorAppointmentDetailsController(
            AppointmentService appointmentService,
            DoctorRepository doctorRepository,
            UserRepository userRepository) {

        this.appointmentService = appointmentService;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String viewAppointment(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        Long userId = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Logged-in user was not found."))
                .getId();

        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No doctor profile is linked to this user."));

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "You are not authorized to view this appointment.");
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctor", doctor);

        return "doctors/appointment-details";
    }
}