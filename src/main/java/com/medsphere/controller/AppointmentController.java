package com.medsphere.controller;

import com.medsphere.dto.AppointmentForm;
import com.medsphere.entity.Appointment;
import com.medsphere.enums.AppointmentStatus;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.PatientRepository;
import com.medsphere.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reception/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentController(
            AppointmentService appointmentService,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentService = appointmentService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public String listAppointments(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.searchAppointments(search, status)
        );

        model.addAttribute("search", search == null ? "" : search);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("statuses", AppointmentStatus.values());

        return "appointments/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("appointmentForm", new AppointmentForm());
        addFormData(model);

        return "appointments/form";
    }

    @PostMapping
    public String createAppointment(
            @Valid @ModelAttribute("appointmentForm") AppointmentForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            addFormData(model);
            return "appointments/form";
        }

        try {
            appointmentService.createAppointment(form);

            return "redirect:/reception/appointments";

        } catch (IllegalArgumentException | IllegalStateException exception) {

            model.addAttribute("formError", exception.getMessage());
            addFormData(model);

            return "appointments/form";
        }
    }

    @GetMapping("/{id}")
    public String viewAppointment(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getAppointment(id)
        );

        return "appointments/details";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        appointmentService.updateAppointmentStatus(id, status);

        return "redirect:/reception/appointments";
    }

    private void addFormData(Model model) {

        model.addAttribute(
                "patients",
                patientRepository.findAll()
        );

        model.addAttribute(
                "doctors",
                doctorRepository.findAll()
        );
    }
}