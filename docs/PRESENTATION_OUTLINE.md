# MedSphere – Presentation / PPT Outline

## Purpose

This document is the master content plan for the final MCA Semester 3 MedSphere presentation. It is intentionally written as a slide-by-slide outline so it can be transferred into PowerPoint/Google Slides later.

> **Visuals:** All screenshots and final diagrams are intentionally left as placeholders. Insert them manually.

---

## Slide 1 – Title Slide

### MedSphere
**Unified Hospital Information Management System**

- MCA Semester 3 Mini Project
- Student: TODO
- Roll No./PRN: TODO
- Guide: TODO
- Institute: TODO
- Academic Year: TODO

**Visual Placeholder:**
`[INSERT MEDSPHERE LOGO / TITLE VISUAL HERE]`

---

## Slide 2 – Introduction

- Hospitals manage large amounts of patient and clinical information.
- Manual or disconnected processes can make information difficult to organize.
- MedSphere provides a centralized web-based information management system.
- The system connects patients, doctors, departments, appointments, consultations, prescriptions, and medical history.

**Speaker Point:**
Explain that the project focuses on the core hospital information workflow rather than attempting to implement a complete commercial hospital ERP.

---

## Slide 3 – Problem Statement

- Patient information needs centralized storage.
- Appointment scheduling requires coordination between patients and doctors.
- Doctor consultation records should be linked to appointments.
- Prescriptions should be linked to consultations.
- Patient medical history should be easily accessible.
- Different hospital users require different access permissions.

### Problem
A basic hospital workflow needs a connected and secure system instead of isolated manual records.

---

## Slide 4 – Objectives

- Develop a centralized hospital information system.
- Implement secure authentication.
- Implement role-based authorization.
- Manage patients and departments.
- Manage doctors and doctor accounts.
- Schedule and manage appointments.
- Record consultations.
- Manage prescriptions.
- Maintain medical history.
- Demonstrate Spring Boot, JPA/Hibernate, Spring Security, and relational database concepts.

---

## Slide 5 – Proposed System

### MedSphere provides

- Centralized database
- Role-based dashboards
- Patient management
- Department management
- Doctor management
- Appointment management
- Consultation management
- Prescription management
- Medical history
- Validation and error handling

**Visual Placeholder:**
`[INSERT SYSTEM OVERVIEW DIAGRAM HERE]`

---

## Slide 6 – Technology Stack

| Layer | Technology |
| --- | --- |
| Language | Java 21 |
| Backend | Spring Boot 3.5.5 |
| Web | Spring MVC |
| Security | Spring Security |
| ORM | Spring Data JPA / Hibernate |
| Frontend | Thymeleaf + HTML + CSS + JavaScript |
| UI | Bootstrap 5.3.3 |
| Database | MariaDB / MySQL |
| Local Environment | XAMPP |
| Build | Maven Wrapper |
| Server | Spring Boot Embedded Tomcat |

---

## Slide 7 – User Roles

### ADMIN
- Manage departments
- Create/manage doctors
- Access authorized patient and appointment workflows

### RECEPTIONIST
- Manage patients
- Add medical history
- Manage appointments

### DOCTOR
- View assigned appointments
- Conduct consultations
- Add prescriptions

**Visual Placeholder:**
`[INSERT ROLE / DASHBOARD VISUAL HERE]`

---

## Slide 8 – System Architecture

```text
Browser / UI
     ↓
Thymeleaf + Bootstrap
     ↓
Spring MVC Controllers
     ↓
Service Layer
     ↓
Spring Data JPA Repositories
     ↓
MariaDB / MySQL
```

Security is applied through Spring Security across the request flow.

**Visual Placeholder:**
`[INSERT FINAL SYSTEM ARCHITECTURE DIAGRAM HERE]`

---

## Slide 9 – Database / ER Diagram

Main entities:

- Role
- User
- Department
- Doctor
- Patient
- Appointment
- Consultation
- Prescription
- Medical History

Main relationships:

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

**Visual Placeholder:**
`[INSERT FINAL ER DIAGRAM IMAGE HERE]`

---

## Slide 10 – Patient Management

Features:

- Patient registration
- Automatic patient code
- Patient search
- Patient profile
- Patient editing
- Medical history integration

Patient code example:

```text
MSP-2026-000001
```

**Screenshot Placeholder:**
`[INSERT PATIENT LIST / PROFILE SCREENSHOT HERE]`

---

## Slide 11 – Doctor Management

### Admin-only doctor creation

The Admin can create:

- Username
- Password
- Full name
- Email
- Department
- Specialization
- Phone

The system creates:

```text
ROLE_DOCTOR User Account
          +
Doctor Profile
```

Password is BCrypt encoded and duplicate usernames are rejected.

**Screenshot Placeholder:**
`[INSERT ADD DOCTOR SCREENSHOT HERE]`

---

## Slide 12 – Appointment Management

- Create appointment
- Select patient
- Select doctor
- Set date and time
- Add reason
- Search/filter appointments
- Manage status
- Prevent doctor/date/time conflicts

### Status

```text
SCHEDULED → COMPLETED
SCHEDULED → CANCELLED
```

Completed and cancelled appointments are locked from further controlled status changes.

**Screenshot Placeholder:**
`[INSERT APPOINTMENT SCREENSHOT HERE]`

---

## Slide 13 – Doctor Consultation

### Workflow

```text
Doctor Login
    ↓
My Appointments
    ↓
Appointment Details
    ↓
Start Consultation
    ↓
Save Consultation
    ↓
Appointment → COMPLETED
```

Consultation stores:

- Symptoms
- Diagnosis
- Notes
- Consultation date

**Screenshot Placeholder:**
`[INSERT CONSULTATION SCREENSHOT HERE]`

---

## Slide 14 – Prescription Management

A doctor can add prescriptions to a consultation.

Fields:

- Medicine
- Dosage
- Frequency
- Duration
- Instructions

```text
Consultation
     ↓
Prescription(s)
```

**Screenshot Placeholder:**
`[INSERT PRESCRIPTION SCREENSHOT HERE]`

---

## Slide 15 – Medical History

Medical history is attached to a patient.

Fields:

- Condition / diagnosis
- Details
- Record date

Records are displayed newest-first on the patient profile.

**Screenshot Placeholder:**
`[INSERT MEDICAL HISTORY SCREENSHOT HERE]`

---

## Slide 16 – Security

Spring Security provides:

- Database-backed authentication
- BCrypt password hashing
- Role-based URL authorization
- CSRF protection
- Login/logout

### Authorization

```text
/admin/**      → ADMIN
/reception/**  → ADMIN, RECEPTIONIST
/doctors/**    → ADMIN, DOCTOR
```

**Screenshot Placeholder:**
`[INSERT ACCESS CONTROL / ERROR SCREENSHOT HERE]`

---

## Slide 17 – Validation & Business Rules

Examples:

- Required fields cannot be blank.
- Doctor username must be unique.
- Doctor password must satisfy minimum length.
- Doctor/date/time appointment conflicts are rejected.
- Only scheduled appointments can be transitioned through the status workflow.
- Only eligible scheduled appointments can start consultation.
- Doctor ownership is checked for doctor-side workflows.
- Medical history must belong to an existing patient.

---

## Slide 18 – Testing

### Tested areas

- Login/logout
- Role authorization
- Patient CRUD
- Department CRUD
- Doctor creation
- Duplicate username
- Appointment workflow
- Consultation
- Prescription
- Medical history
- Validation
- Database persistence after application restart

### Result

**Core functional testing: PASS**

**Visual Placeholder:**
`[INSERT TESTING EVIDENCE SCREENSHOT HERE]`

---

## Slide 19 – Results / Screenshots

Show the strongest final application screens.

Suggested 4–6 screenshots:

- Admin dashboard
- Patient profile
- Add Doctor
- Appointment details
- Consultation + prescription
- Medical history

**Visual Placeholder:**
`[INSERT FINAL SCREENSHOT COLLAGE HERE]`

---

## Slide 20 – Limitations

Current Semester 3 mini version does not include:

- Billing
- Pharmacy inventory
- Laboratory management
- Ward/bed management
- Patient portal
- Advanced notifications
- Advanced analytics
- Production deployment infrastructure

---

## Slide 21 – Future Scope

Possible future enhancements:

- Billing and payments
- Pharmacy inventory
- Laboratory reports
- Ward and bed management
- Patient portal
- Doctor availability schedules
- Notifications
- Prescription PDF generation
- Audit logging
- Advanced analytics
- Production deployment

---

## Slide 22 – Conclusion

- MedSphere provides a centralized hospital information workflow.
- Role-based access separates Admin, Receptionist, and Doctor responsibilities.
- JPA relationships connect patients, appointments, consultations, prescriptions, and medical history.
- Spring Security protects application workflows.
- The Semester 3 mini version provides a foundation for future expansion.

---

## Slide 23 – Thank You / Questions

# Thank You

**Questions?**

Student: TODO  
Project: MedSphere

**Visual Placeholder:**
`[INSERT FINAL MEDSPHERE VISUAL HERE]`

---

# Presentation Delivery Tips

- Keep each slide visually simple.
- Explain the workflow rather than reading text.
- Demonstrate one complete workflow during the viva/demo: patient → appointment → consultation → prescription.
- Highlight the Admin-only doctor account creation improvement.
- Be ready to explain the entity relationships.
- Be ready to explain why Service and Repository layers are separate.
- Mention that the Semester 3 scope intentionally excludes advanced hospital ERP modules.
