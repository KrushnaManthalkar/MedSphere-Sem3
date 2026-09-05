# MedSphere – Semester 4 Major Project Master Plan

> **Status:** Planning only – no Semester 4 implementation starts from this document.
>
> This document is a future reference and planning baseline for extending the completed Semester 3 MedSphere mini project into the Semester 4 major project.

---

## 1. Planning Objective

The Semester 4 project will evolve MedSphere from a **basic hospital information management system** into a more complete **Hospital Management / Hospital Information System** while preserving the stable Semester 3 foundation.

The key principle is:

```text
Semester 3 Mini Project
        |
        |  Preserve working foundation
        v
Semester 4 Major Project
        |
        +--> More hospital modules
        +--> Better workflows
        +--> Stronger security
        +--> Better reporting
        +--> More complete database
        +--> Production-style engineering
```

The major project should be an **extension of MedSphere**, not an unrelated rewrite.

---

## 2. Starting Point

The Semester 3 mini version already provides the following foundation:

- Spring Boot 3.5.5
- Java 21
- Spring MVC
- Spring Security
- BCrypt authentication
- Role-based authorization
- Spring Data JPA / Hibernate
- Thymeleaf
- Bootstrap
- MariaDB/MySQL
- Layered architecture
- Admin workflow
- Reception workflow
- Doctor workflow
- Patient management
- Department management
- Doctor management
- Doctor account creation
- Appointment management
- Consultation management
- Prescription management
- Medical history
- Responsive UI

The Semester 4 plan should build on this working foundation rather than unnecessarily replacing it.

---

## 3. Major Project Vision

### Proposed Vision

**MedSphere – Unified Hospital Management and Information System**

The Semester 4 version should provide an integrated platform for managing the major operational and clinical workflows of a hospital.

The system should gradually cover:

```text
Users & Security
       |
       +---- Patients
       +---- Doctors
       +---- Departments
       |
       +---- Appointments
       +---- Consultations
       +---- Prescriptions
       +---- Medical History
       |
       +---- Laboratory
       +---- Pharmacy
       +---- Billing
       +---- Wards / Beds
       +---- Notifications
       +---- Reports / Analytics
```

Not every future feature needs to be implemented at once. The final scope should be selected according to Semester 4 academic requirements, available development time, and actual project feasibility.

---

## 4. Core Goals for Semester 4

1. Extend the working Semester 3 system instead of starting from zero.
2. Add meaningful hospital-management functionality.
3. Improve separation of responsibilities where the larger system requires it.
4. Strengthen authorization and data-access rules.
5. Improve database design for the larger feature set.
6. Introduce better reporting and operational visibility.
7. Improve validation, error handling, auditability, and testing.
8. Keep the system understandable enough for academic demonstration and viva.
9. Keep the UI consistent with the MedSphere design established in Semester 3.
10. Maintain documentation throughout development.

---

## 5. Proposed Semester 4 Modules

## 5.1 User, Role & Access Management

### Planned enhancements

- More granular permissions.
- Better user administration.
- Account activation/deactivation.
- Password management.
- Role-specific navigation.
- Stronger authorization checks.
- Audit trail for important administrative actions.

### Possible future roles

The current roles are:

- ADMIN
- RECEPTIONIST
- DOCTOR

Additional roles may be introduced only if they are justified by the final Semester 4 requirements, for example:

- PHARMACIST
- LAB TECHNICIAN
- ACCOUNTANT
- NURSE
- PATIENT

Role expansion should not be done merely for increasing the number of roles; every role should have a meaningful workflow.

---

## 5.2 Patient Management – Major Version

Extend the current patient module with a more complete patient record.

### Planned enhancements

- Improved patient profile.
- Emergency contact management.
- Allergies.
- Chronic conditions.
- Additional demographic information if required.
- Complete visit history.
- Consultation history.
- Prescription history.
- Laboratory report history.
- Billing history.
- Patient document support if required.

The exact fields should be finalized after reviewing the Semester 4 academic requirements.

---

## 5.3 Doctor & Department Management

Build on the current Admin doctor workflow.

### Planned enhancements

- Doctor availability.
- Working schedules.
- Consultation timings.
- Department-wise doctor availability.
- Doctor specialization management.
- Leave/unavailability records.
- Better doctor profile information.

---

## 5.4 Appointment & Scheduling System

The Semester 3 appointment system should become a more complete scheduling module.

### Planned enhancements

- Doctor availability schedules.
- Time-slot generation.
- Appointment rescheduling.
- Appointment cancellation workflow.
- Better conflict detection.
- Appointment history.
- Appointment reminders.
- Daily doctor schedule.
- Department-wise appointment views.
- Appointment statistics.

### Planned workflow

```text
Patient / Reception
        |
        v
Select Department
        |
        v
Select Doctor
        |
        v
Check Availability
        |
        v
Select Date & Time Slot
        |
        v
Create Appointment
        |
        v
Confirmation / Notification
```

---

## 5.5 Consultation & Clinical Records

Expand the existing consultation workflow into a more complete clinical record.

### Planned enhancements

- Structured clinical information.
- Vital signs.
- Symptoms.
- Diagnosis.
- Treatment notes.
- Follow-up date.
- Clinical history timeline.
- Consultation history for a patient.
- Better doctor notes.
- Follow-up appointments.

The design should remain careful about sensitive medical information and should enforce appropriate role access.

---

## 5.6 Prescription Management – Major Version

The current basic prescription module should evolve into a more structured prescription workflow.

### Planned enhancements

- Medicine master.
- Medicine search.
- Structured dosage/frequency options.
- Prescription status.
- Prescription history.
- Printable prescription.
- Pharmacy integration.
- Medicine availability checking.

Pharmacy inventory should be implemented as a separate module rather than putting inventory logic directly inside the existing prescription entity.

---

## 5.7 Pharmacy Management

### Planned module

A dedicated pharmacy subsystem may include:

- Medicine master.
- Categories.
- Suppliers.
- Stock.
- Batch numbers.
- Expiry dates.
- Purchase records.
- Stock-in / stock-out.
- Low-stock alerts.
- Prescription fulfillment.
- Pharmacy sales records.

### High-level flow

```text
Doctor Prescription
        |
        v
Pharmacy
        |
        v
Check Medicine & Stock
        |
        v
Dispense Medicine
        |
        v
Update Inventory
```

---

## 5.8 Laboratory Management

### Planned module

- Test master.
- Lab test requests.
- Patient test orders.
- Sample/status tracking.
- Result entry.
- Report generation.
- Doctor access to reports.
- Patient laboratory history.

### High-level flow

```text
Doctor
  |
  v
Request Lab Test
  |
  v
Laboratory
  |
  v
Sample / Processing
  |
  v
Result Entry
  |
  v
Lab Report
  |
  v
Doctor / Patient Record
```

---

## 5.9 Billing & Payment Management

### Planned module

- Consultation charges.
- Laboratory charges.
- Pharmacy charges.
- Other hospital service charges.
- Invoice generation.
- Payment recording.
- Payment status.
- Receipt generation.
- Billing history.

### Important design decision

Billing should be implemented as its own domain/module. Financial data should not be mixed directly into appointment or consultation entities.

---

## 5.10 Ward & Bed Management

### Planned module

- Ward master.
- Bed master.
- Bed availability.
- Patient admission.
- Bed allocation.
- Transfer between beds/wards.
- Discharge.
- Occupancy status.

### High-level flow

```text
Patient Admission
       |
       v
Select Ward
       |
       v
Check Available Bed
       |
       v
Assign Bed
       |
       v
Patient Stay
       |
       v
Discharge
       |
       v
Release Bed
```

---

## 5.11 Notifications

### Planned enhancements

- Appointment reminders.
- Appointment status notifications.
- Follow-up reminders.
- Low-stock alerts.
- Laboratory result notifications.
- Administrative notifications.

Notification delivery can initially remain application-based. External email/SMS integration should be considered only after the core notification model is stable.

---

## 5.12 Reports & Analytics

The major project should provide useful operational reports rather than only CRUD screens.

### Possible reports

- Daily appointments.
- Doctor-wise appointments.
- Department-wise appointments.
- Patient registration statistics.
- Consultation statistics.
- Prescription statistics.
- Laboratory statistics.
- Pharmacy stock reports.
- Billing summaries.
- Ward occupancy.
- Revenue summaries.

### Dashboard direction

```text
              ADMIN DASHBOARD
                     |
       +-------------+-------------+
       |             |             |
   Operations     Clinical      Finance
       |             |             |
  Appointments   Consultations   Billing
  Admissions     Lab Reports    Payments
  Beds           Prescriptions  Revenue
```

---

## 6. Proposed Major-Version Architecture

The Semester 3 layered architecture should remain the base.

### Target architecture

```text
+--------------------------------------------------+
|                  Web / UI Layer                  |
| Thymeleaf / Bootstrap / JavaScript               |
+-------------------------+------------------------+
                          |
                          v
+--------------------------------------------------+
|              Controller / Web Layer              |
+-------------------------+------------------------+
                          |
                          v
+--------------------------------------------------+
|                 Service Layer                    |
| Business Rules / Transactions / Validation       |
+-------------------------+------------------------+
                          |
                          v
+--------------------------------------------------+
|               Repository Layer                   |
| Spring Data JPA                                  |
+-------------------------+------------------------+
                          |
                          v
+--------------------------------------------------+
|                MariaDB / MySQL                   |
+--------------------------------------------------+
```

As the system grows, the service/domain structure should be organized by business module rather than creating an unnecessarily complicated architecture.

A separate REST API layer can be considered if the Semester 4 requirements benefit from API integration, mobile clients, asynchronous UI, or external system integration. It should not be added only for complexity.

---

## 7. Proposed Major Database Expansion

The Semester 3 database currently centers on:

```text
Role
User
Department
Doctor
Patient
Appointment
Consultation
Prescription
MedicalHistory
```

The Semester 4 database may add entities such as:

```text
DoctorAvailability
FollowUp
Medicine
MedicineBatch
Supplier
InventoryTransaction
LabTest
LabOrder
LabResult
Invoice
InvoiceItem
Payment
Ward
Bed
Admission
Discharge
Notification
AuditLog
```

These are **planning candidates**, not confirmed final tables. The final schema must be designed only after the final Semester 4 feature scope is approved.

---

## 8. Security Plan

Security should become stronger in the major version.

### Planned improvements

- Maintain BCrypt password hashing.
- Maintain CSRF protection for server-rendered forms.
- Strengthen role/permission checks.
- Enforce ownership checks on clinical records.
- Prevent unauthorized access through manually entered URLs.
- Validate all user input server-side.
- Avoid exposing internal exception details to users.
- Keep credentials outside source control.
- Add audit logging for sensitive administrative actions.
- Consider session/security hardening appropriate for the final deployment environment.

### Security principle

```text
Authentication
      ↓
Authorization
      ↓
Ownership / Access Check
      ↓
Validation
      ↓
Business Rule
      ↓
Database Operation
```

---

## 9. Testing Strategy for Semester 4

The major project should move beyond manual happy-path testing.

### Planned testing levels

**Unit testing**

- Service business rules.
- Validation logic.
- Utility methods.

**Repository testing**

- Important custom queries.
- Relationship queries.
- Constraint behavior.

**Controller / integration testing**

- Authentication.
- Authorization.
- Main workflows.
- Validation responses.

**End-to-end testing**

- Complete hospital workflows.

**Security testing**

- Role restrictions.
- Ownership restrictions.
- Direct URL access.
- Invalid input.
- Session behavior.

**Regression testing**

Every major Semester 4 change should confirm that the existing Semester 3 workflows still work.

---

## 10. UI / UX Direction

The Semester 3 UI foundation should be preserved and extended.

### Planned improvements

- Keep the MedSphere visual identity.
- Keep common navigation.
- Improve dashboards as module count increases.
- Use role-specific navigation.
- Add clearer module grouping.
- Improve responsive behavior.
- Use reusable components/styles.
- Provide clear empty states.
- Provide meaningful validation and error messages.
- Avoid overcrowded dashboards.

The major project should look like an evolution of the same product, not a completely different application.

---

## 11. Suggested Development Phases

The exact phase count can change after the Semester 4 syllabus is confirmed. The following is the initial planning roadmap.

### Major Phase 1 – Requirement Finalization

- Confirm Semester 4 syllabus/project requirements.
- Freeze major-project scope.
- Identify mandatory vs optional modules.
- Review Semester 3 implementation.
- Finalize use cases.

### Major Phase 2 – Architecture & Database Planning

- Finalize module boundaries.
- Finalize ER design.
- Plan new tables.
- Plan relationships and constraints.
- Review security model.

### Major Phase 3 – Core Foundation Upgrade

- Refactor only where genuinely required.
- Improve shared validation/error handling.
- Improve security and authorization.
- Establish reusable UI components.

### Major Phase 4 – Scheduling & Clinical Expansion

- Doctor availability.
- Appointment scheduling improvements.
- Follow-up workflow.
- Expanded consultation records.

### Major Phase 5 – Laboratory Module

- Lab tests.
- Orders.
- Results.
- Reports.

### Major Phase 6 – Pharmacy Module

- Medicine master.
- Inventory.
- Suppliers.
- Stock transactions.
- Prescription fulfillment.

### Major Phase 7 – Billing Module

- Services.
- Invoice.
- Invoice items.
- Payments.
- Receipts.

### Major Phase 8 – Ward / Admission Module

- Wards.
- Beds.
- Admissions.
- Transfers.
- Discharge.

### Major Phase 9 – Notifications & Reports

- Notifications.
- Operational reports.
- Analytics dashboards.

### Major Phase 10 – Security & Quality Pass

- Authorization audit.
- Validation audit.
- Error handling.
- Testing.
- Regression testing.
- Performance review.

### Major Phase 11 – UI / UX Finalization

- Responsive audit.
- Consistency pass.
- Dashboard refinement.
- Accessibility/usability improvements.

### Major Phase 12 – Final Documentation & Demonstration

- Final report.
- ER diagram.
- Architecture diagram.
- Screenshots.
- PPT.
- Viva preparation.
- Final repository cleanup.

---

## 12. What Should NOT Be Done Automatically

The following decisions must wait until the Semester 4 requirements are known:

- Do not blindly add every future-scope module.
- Do not change the technology stack without a reason.
- Do not rewrite working Semester 3 modules without need.
- Do not introduce microservices merely for complexity.
- Do not add a REST API unless it has a clear project purpose.
- Do not add unnecessary third-party services.
- Do not expand roles without meaningful workflows.
- Do not design the final database before the final module scope is frozen.

---

## 13. Semester 3 → Semester 4 Mapping

| Semester 3 | Semester 4 Direction |
| --- | --- |
| Basic patient management | Complete patient record/history |
| Basic appointments | Availability + scheduling |
| Basic consultation | Expanded clinical record |
| Basic prescription | Structured prescription + pharmacy |
| Medical history | Full patient clinical timeline |
| Departments | Department operations |
| Doctors | Availability, schedule, leave |
| Basic security | Granular permissions + audit |
| Basic dashboard | Analytics / operational dashboards |
| No billing | Billing + payments |
| No laboratory | Laboratory management |
| No pharmacy | Pharmacy + inventory |
| No wards | Ward + bed + admission |
| Basic UI | Expanded role-based UX |
| Manual testing | Automated + integration + regression testing |

---

## 14. Definition of a Successful Semester 4 Major Project

The major project should be considered successful when it demonstrates:

- A clear real-world hospital problem.
- A coherent multi-module solution.
- Proper relational database design.
- Secure authentication and authorization.
- Meaningful business rules.
- Multiple interconnected workflows.
- Reliable validation and error handling.
- Appropriate testing.
- A consistent and usable interface.
- Clear documentation.
- A system that can be explained confidently during viva.

The goal is **quality and integration**, not simply the maximum number of screens or tables.

---

## 15. Final Major-Project Scope Checklist

### Existing Foundation

- [x] Authentication
- [x] Role-based authorization
- [x] Patients
- [x] Departments
- [x] Doctors
- [x] Appointments
- [x] Consultations
- [x] Prescriptions
- [x] Medical history

### Candidate Semester 4 Features

- [ ] Doctor availability
- [ ] Advanced appointment scheduling
- [ ] Follow-up management
- [ ] Expanded clinical records
- [ ] Laboratory management
- [ ] Pharmacy management
- [ ] Billing and payments
- [ ] Ward and bed management
- [ ] Notifications
- [ ] Reports and analytics
- [ ] Audit logging
- [ ] More granular permissions

### Finalization Required Later

- [ ] Semester 4 syllabus review
- [ ] Final scope freeze
- [ ] Final use-case diagram
- [ ] Final ER diagram
- [ ] Final architecture decision
- [ ] Final database schema
- [ ] Final implementation roadmap
- [ ] Final testing strategy

---

## 16. Important Future Reference Rule

This document is the **planning baseline**, not a commitment to implement every listed feature.

When Semester 4 development begins, the first step should be to review this plan against the official Semester 4 syllabus and project requirements. The final scope should then be frozen before coding begins.

The Semester 3 codebase should remain the stable starting point, and each new major-project feature should be implemented incrementally and documented in `PROJECT_CONTEXT.md`.

---

## 17. Current Status

**Planning complete. Implementation intentionally not started.**

Semester 4 work should begin only when the official Semester 4 requirements are available and the final scope has been reviewed.
