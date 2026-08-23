package com.medsphere.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 30)
    @Column(name = "patient_code", nullable = false, unique = true, length = 30)
    private String patientCode;
    @NotBlank @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;
    @NotNull @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @NotBlank @Size(max = 20) @Column(nullable = false, length = 20)
    private String gender;
    @Size(max = 10) @Column(name = "blood_group", length = 10)
    private String bloodGroup;
    @NotBlank @Size(max = 20) @Column(nullable = false, length = 20)
    private String phone;
    @Email @Size(max = 100) @Column(length = 100)
    private String email;
    @Size(max = 500) @Column(length = 500)
    private String address;
    @Size(max = 100) @Column(name = "emergency_contact", length = 100)
    private String emergencyContact;
    @NotNull @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @PrePersist
    void setDefaultRegistrationDate() { if (registrationDate == null) registrationDate = LocalDate.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientCode() { return patientCode; }
    public void setPatientCode(String patientCode) { this.patientCode = patientCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
}
