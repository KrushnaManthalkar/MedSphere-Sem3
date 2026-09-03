package com.medsphere.controller;

import com.medsphere.dto.PrescriptionForm;
import com.medsphere.entity.Appointment;
import com.medsphere.entity.Consultation;
import com.medsphere.entity.Doctor;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.AppointmentService;
import com.medsphere.service.ConsultationService;
import com.medsphere.service.PrescriptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors/appointments")
public class DoctorPrescriptionController {

    private final AppointmentService appointmentService;
    private final ConsultationService consultationService;
    private final PrescriptionService prescriptionService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorPrescriptionController(
            AppointmentService appointmentService,
            ConsultationService consultationService,
            PrescriptionService prescriptionService,
            DoctorRepository doctorRepository,
            UserRepository userRepository) {

        this.appointmentService = appointmentService;
        this.consultationService = consultationService;
        this.prescriptionService = prescriptionService;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/consultation/prescription")
    public String showPrescriptionForm(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to access this appointment.");
        }

        Consultation consultation =
                consultationService.getConsultationByAppointmentId(id);

        model.addAttribute("appointment", appointment);
        model.addAttribute("consultation", consultation);
        model.addAttribute("doctor", doctor);
        model.addAttribute("prescriptionForm", new PrescriptionForm());

        return "doctors/prescription-form";
    }

    @PostMapping("/{id}/consultation/prescription")
    public String savePrescription(
            @PathVariable Long id,
            @ModelAttribute PrescriptionForm form,
            Authentication authentication) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Appointment appointment = appointmentService.getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to access this appointment.");
        }

        Consultation consultation =
                consultationService.getConsultationByAppointmentId(id);

        prescriptionService.createPrescription(
                consultation.getId(),
                form
        );

        return "redirect:/doctors/appointments/"
                + id + "/consultation/details";
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