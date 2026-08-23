package com.medsphere.controller;

import com.medsphere.dto.DepartmentForm;
import com.medsphere.entity.Department;
import com.medsphere.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/admin/departments")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service) { this.service = service; }
    @GetMapping public String list(Model model) { model.addAttribute("departments", service.getAllDepartments()); return "departments/list"; }
    @GetMapping("/new") public String createForm(Model model) { model.addAttribute("departmentForm", new DepartmentForm()); return "departments/form"; }
    @PostMapping public String create(@Valid @ModelAttribute DepartmentForm departmentForm, BindingResult result, RedirectAttributes redirect) { if (!result.hasFieldErrors("name") && !service.isNameAvailable(departmentForm.getName().trim(), null)) result.rejectValue("name", "duplicate", "Department name already exists."); if(result.hasErrors()) return "departments/form"; Department d=service.create(departmentForm); redirect.addFlashAttribute("successMessage", "Department created successfully."); return "redirect:/admin/departments/"+d.getId()+"/edit"; }
    @GetMapping("/{id}/edit") public String editForm(@PathVariable Long id, Model model) { model.addAttribute("department", service.getDepartment(id)); model.addAttribute("departmentForm", service.getForm(id)); return "departments/edit"; }
    @PostMapping("/{id}") public String update(@PathVariable Long id, @Valid @ModelAttribute DepartmentForm departmentForm, BindingResult result, Model model, RedirectAttributes redirect) { if (!result.hasFieldErrors("name") && !service.isNameAvailable(departmentForm.getName().trim(), id)) result.rejectValue("name", "duplicate", "Department name already exists."); if(result.hasErrors()) { model.addAttribute("department", service.getDepartment(id)); return "departments/edit"; } service.update(id,departmentForm); redirect.addFlashAttribute("successMessage", "Department updated successfully."); return "redirect:/admin/departments/"+id+"/edit"; }
}
