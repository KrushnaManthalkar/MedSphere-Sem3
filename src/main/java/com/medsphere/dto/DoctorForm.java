package com.medsphere.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DoctorForm {
    @NotNull(message = "Select a doctor user account.") private Long userId;
    @NotNull(message = "Select a department.") private Long departmentId;
    @NotBlank(message = "Specialization is required.") @Size(max = 100) private String specialization;
    @NotBlank(message = "Phone number is required.") @Size(max = 20) private String phone;
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
