package com.medsphere.controller;

import com.medsphere.entity.Doctor;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.AppointmentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctors/appointments")
public class DoctorAppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorAppointmentController(
            AppointmentService appointmentService,
            DoctorRepository doctorRepository,
            UserRepository userRepository) {

        this.appointmentService = appointmentService;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listAppointments(
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        Long userId = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Logged-in user was not found."))
                .getId();

        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No doctor profile is linked to this user."));

        model.addAttribute(
                "appointments",
                appointmentService.getDoctorAppointments(doctor.getId())
        );

        model.addAttribute("doctor", doctor);

        return "doctors/appointments";
    }
}