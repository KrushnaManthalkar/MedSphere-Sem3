package com.medsphere.controller;

import com.medsphere.dto.MedicalHistoryForm;
import com.medsphere.service.MedicalHistoryService;
import com.medsphere.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reception/patients")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;
    private final PatientService patientService;

    public MedicalHistoryController(
            MedicalHistoryService medicalHistoryService,
            PatientService patientService) {

        this.medicalHistoryService = medicalHistoryService;
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}/medical-history/new")
    public String showMedicalHistoryForm(
            @PathVariable Long patientId,
            Model model) {

        model.addAttribute(
                "patient",
                patientService.getPatientById(patientId)
        );

        model.addAttribute(
                "medicalHistoryForm",
                new MedicalHistoryForm()
        );

        return "patients/medical-history-form";
    }

    @PostMapping("/{patientId}/medical-history")
    public String createMedicalHistory(
            @PathVariable Long patientId,
            @ModelAttribute MedicalHistoryForm form) {

        medicalHistoryService.createMedicalHistory(patientId, form);

        return "redirect:/reception/patients/" + patientId;
    }
}