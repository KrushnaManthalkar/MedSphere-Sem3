package com.medsphere.controller;

import com.medsphere.dto.ConsultationForm;
import com.medsphere.entity.Appointment;
import com.medsphere.entity.Doctor;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.AppointmentService;
import com.medsphere.service.ConsultationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/doctor/appointments")
public class DoctorConsultationController {

    private final AppointmentService appointmentService;
    private final ConsultationService consultationService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorConsultationController(
            AppointmentService appointmentService,
            ConsultationService consultationService,
            DoctorRepository doctorRepository,
            UserRepository userRepository) {

        this.appointmentService = appointmentService;
        this.consultationService = consultationService;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/consultation")
    public String showConsultationForm(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "You are not authorized to access this appointment.");
        }

        if (!appointment.getStatus().name().equals("SCHEDULED")) {
            throw new IllegalStateException(
                    "Consultation can only be started for a scheduled appointment.");
        }

        ConsultationForm form = new ConsultationForm();
        form.setConsultationDate(LocalDate.now());

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctor", doctor);
        model.addAttribute("form", form);

        return "doctors/consultation-form";
    }

    @PostMapping("/{id}/consultation")
    public String saveConsultation(
            @PathVariable Long id,
            @ModelAttribute("form") ConsultationForm form,
            Authentication authentication) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "You are not authorized to access this appointment.");
        }

        if (!appointment.getStatus().name().equals("SCHEDULED")) {
            throw new IllegalStateException(
                    "Consultation can only be created for a scheduled appointment.");
        }

        consultationService.createConsultation(id, form);

        appointmentService.updateAppointmentStatus(id, "COMPLETED");

        return "redirect:/doctor/appointments/" + id + "/consultation/details";
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