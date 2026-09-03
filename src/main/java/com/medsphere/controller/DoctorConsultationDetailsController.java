package com.medsphere.controller;

import com.medsphere.entity.Appointment;
import com.medsphere.entity.Consultation;
import com.medsphere.entity.Doctor;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.AppointmentService;
import com.medsphere.service.ConsultationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor/appointments")
public class DoctorConsultationDetailsController {

    private final AppointmentService appointmentService;
    private final ConsultationService consultationService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorConsultationDetailsController(
            AppointmentService appointmentService,
            ConsultationService consultationService,
            DoctorRepository doctorRepository,
            UserRepository userRepository) {

        this.appointmentService = appointmentService;
        this.consultationService = consultationService;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/consultation/details")
    public String viewConsultation(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "You are not authorized to view this consultation.");
        }

        Consultation consultation =
                consultationService.getConsultationByAppointmentId(id);

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctor", doctor);
        model.addAttribute("consultation", consultation);

        return "doctors/consultation-details";
    }

    private Doctor getLoggedInDoctor(Authentication authentication) {

        String username = authentication.getName();

        Long userId = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Logged-in user was not found."))
                .getId();

        return doctorRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No doctor profile is linked to this user."));
    }
}