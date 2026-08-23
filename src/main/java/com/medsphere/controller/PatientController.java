package com.medsphere.controller;

import com.medsphere.dto.PatientForm;
import com.medsphere.entity.Patient;
import com.medsphere.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reception/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("patients", patientService.searchPatients(search));
        model.addAttribute("search", search == null ? "" : search);
        return "patients/list";
    }

    @GetMapping("/new")
    public String registrationForm(Model model) {
        model.addAttribute("patientForm", new PatientForm());
        return "patients/form";
    }

    @PostMapping
    public String registerPatient(@Valid @ModelAttribute PatientForm patientForm, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "patients/form";
        }
        Patient patient = patientService.registerPatient(patientForm);
        redirectAttributes.addFlashAttribute("successMessage", "Patient " + patient.getPatientCode() + " registered successfully.");
        return "redirect:/reception/patients/" + patient.getId();
    }

    @GetMapping("/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patients/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("patientForm", patientService.getPatientForm(id));
        return "patients/edit";
    }

    @PostMapping("/{id}")
    public String updatePatient(@PathVariable Long id, @Valid @ModelAttribute PatientForm patientForm,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patientService.getPatientById(id));
            return "patients/edit";
        }
        Patient patient = patientService.updatePatient(id, patientForm);
        redirectAttributes.addFlashAttribute("successMessage", "Patient information updated successfully.");
        return "redirect:/reception/patients/" + patient.getId();
    }
}
