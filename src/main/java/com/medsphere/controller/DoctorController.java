package com.medsphere.controller;

import com.medsphere.dto.DoctorCreateForm;
import com.medsphere.dto.DoctorForm;
import com.medsphere.entity.Doctor;
import com.medsphere.service.DepartmentService;
import com.medsphere.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public DoctorController(
            DoctorService doctorService,
            DepartmentService departmentService) {

        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "doctors/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );

        model.addAttribute(
                "doctorCreateForm",
                new DoctorCreateForm()
        );

        return "doctors/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("doctorCreateForm")
            DoctorCreateForm doctorCreateForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments()
            );

            return "doctors/form";
        }

        try {

            Doctor doctor =
                    doctorService.createNewDoctor(doctorCreateForm);

            redirect.addFlashAttribute(
                    "successMessage",
                    "Doctor account and profile created successfully."
            );

            return "redirect:/admin/doctors/" + doctor.getId();

        } catch (IllegalArgumentException ex) {

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments()
            );

            model.addAttribute(
                    "createError",
                    ex.getMessage()
            );

            return "doctors/form";
        }
    }

    @GetMapping("/{id}")
    public String details(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "doctor",
                doctorService.getDoctor(id)
        );

        return "doctors/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "doctor",
                doctorService.getDoctor(id)
        );

        model.addAttribute(
                "doctorForm",
                doctorService.getForm(id)
        );

        populate(model, id);

        return "doctors/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute DoctorForm doctorForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "doctor",
                    doctorService.getDoctor(id)
            );

            populate(model, id);

            return "doctors/edit";
        }

        Doctor doctor =
                doctorService.update(id, doctorForm);

        redirect.addFlashAttribute(
                "successMessage",
                "Doctor profile updated successfully."
        );

        return "redirect:/admin/doctors/" + doctor.getId();
    }

    private void populate(
            Model model,
            Long currentDoctorId) {

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );

        model.addAttribute(
                "doctorUsers",
                doctorService.getAvailableDoctorUsers(
                        currentDoctorId
                )
        );
    }
}