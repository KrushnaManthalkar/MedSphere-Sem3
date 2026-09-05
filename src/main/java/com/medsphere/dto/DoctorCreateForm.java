package com.medsphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DoctorCreateForm {

    @NotBlank(message = "Username is required.")
    @Size(max = 50, message = "Username must not exceed 50 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name must not exceed 100 characters.")
    private String fullName;

    @Email(message = "Enter a valid email address.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    @NotNull(message = "Select a department.")
    private Long departmentId;

    @NotBlank(message = "Specialization is required.")
    @Size(max = 100, message = "Specialization must not exceed 100 characters.")
    private String specialization;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number must not exceed 20 characters.")
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}