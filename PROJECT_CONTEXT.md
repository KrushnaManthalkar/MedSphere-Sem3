# MedSphere Project Context

## Project

**MedSphere – Unified Hospital Information Management System**

- **Current version:** Semester 3 Mini Version
- **Current status:** Phase 1 through Phase 13 complete
- **Phase 13 status:** Documentation foundation completed

This file is the permanent project context and development history. It records the implementation details, files, tests, important decisions, and known issues for each completed phase.

## Current Technology

- Java 21
- Spring Boot 3.5.5
- Spring Security
- Spring Data JPA / Hibernate
- MariaDB/MySQL
- Thymeleaf
- Bootstrap 5.3.3 (CDN)
- JavaScript
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Database

- **Database:** `medsphere`
- **Host:** `localhost`
- **Port:** `3307`
- **Server:** XAMPP MariaDB
- **Application port:** `8080`
- **JPA schema mode:** `spring.jpa.hibernate.ddl-auto=update`

The datasource configuration is in `src/main/resources/application.properties` and defaults to the local XAMPP MariaDB instance. Environment variables can override the default datasource values.

## Entity Relationships

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

## Completed Phases

### Phase 1 – Foundation

Completed the Spring Boot Maven foundation with Java 21, Maven Wrapper, Thymeleaf, Bootstrap, Spring Security, MySQL/MariaDB, JPA/Hibernate, protected home page, login/logout, and application port 8080.

### Phase 2 – Database Foundation

Created the core JPA entities and repositories for roles, users, departments, doctors, patients, appointments, consultations, prescriptions, and medical history. Added RoleType and AppointmentStatus enums and the core entity relationships.

### Phase 3 – Database-Backed Authentication

Replaced temporary authentication with database-backed users and roles. Added CustomUserDetailsService, BCrypt password encoding, DataInitializer, CSRF protection, login/logout behavior, and role-based URL authorization.

Development accounts:

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `admin123` | `ADMIN` |
| `receptionist` | `reception123` | `RECEPTIONIST` |
| `doctor` | `doctor123` | `DOCTOR` |

Authorization rules:

| URL area | Allowed roles |
| --- | --- |
| `/admin/**` | `ADMIN` |
| `/reception/**` | `ADMIN`, `RECEPTIONIST` |
| `/doctors/**` | `ADMIN`, `DOCTOR` |
| Other routes | Authenticated users |

### Phase 4 – Patient Management

Completed patient registration, list/search, profile details, and edit flow at `/reception/patients`. Added PatientForm, service layer, patient validation, generated `MSP-YYYY-000001` patient codes, immutable patient code/registration date, error handling, Bootstrap templates, and role-specific navigation.

### Phase 5 – Department & Doctor Management

Completed Admin-only Department and Doctor management. Added department and doctor services/forms/controllers/templates, doctor-user linking, department assignment, duplicate validation, and role restrictions.

### Phase 6 – Appointment Management

Completed Receptionist/Admin appointment management at `/reception/appointments`.

- Appointment listing with patient/doctor information and status.
- Search by patient code, patient name, or doctor name.
- Status filtering for `SCHEDULED`, `COMPLETED`, and `CANCELLED`.
- Appointment creation using existing Patient and Doctor records.
- Server-side validation for required fields, future date, and reason length.
- Doctor/date/time conflict protection.
- New appointments start as `SCHEDULED`.
- Appointment details and controlled status updates.

### Phase 7 – Doctor Consultation

Completed the doctor-side consultation workflow.

Flow:

```text
Doctor Login
    ↓
My Appointments
    ↓
View Appointment
    ↓
Start Consultation
    ↓
Consultation Form
    ↓
Save Consultation
    ↓
Database
    ↓
Appointment becomes COMPLETED
    ↓
View Consultation
    ↓
Consultation Details
```

Implemented doctor appointment listing, ownership checks, consultation DTO/service/repository, consultation form and save routes, duplicate consultation protection, and completed-appointment consultation viewing.

Important decisions:

- Doctor-side URLs use `/doctors/**` consistently.
- Consultation is one-to-one with Appointment.
- Only scheduled appointments can start a consultation.
- Successful consultation save marks the appointment `COMPLETED`.

### Phase 8 – Basic Prescription

Completed the basic prescription workflow for the Semester 3 mini version.

Implemented:

- Prescription repository lookup by consultation ID.
- Prescription service and implementation.
- Prescription form for medicine name, dosage, frequency, duration, and optional instructions.
- Doctor prescription controller under `/doctors/appointments`.
- Doctor ownership checks.
- CSRF-protected prescription form.
- Prescription listing inside Consultation Details.

A Thymeleaf model error found during testing was fixed by adding the doctor object to the model. A test prescription using Paracetamol was saved and displayed successfully.

### Phase 9 – Medical History

Completed the basic medical history workflow.

Implemented:

- MedicalHistoryRepository with newest-first patient lookup.
- MedicalHistoryForm.
- MedicalHistoryService and implementation.
- MedicalHistoryController under `/reception/patients`.
- Patient Profile integration.
- Add Medical History form and history table.

Verified test data:

- Condition: `Fever`
- Details: `Patient had fever and mild weakness.`
- Record date: `2026-09-04`

A Git rebase conflict in MedicalHistoryRepository was safely resolved without losing the implementation.

### Phase 10 – Dashboard & UI Redesign

Redesigned the main application interface with a common MedSphere navigation system, role-aware dashboard sections, responsive Bootstrap layouts, content cards, page headers, tables, forms, badges, empty states, and status indicators.

The redesigned UI was applied across the core application pages while preserving existing backend workflows.

### Phase 11 – UI Consistency Pass

Audited the complete template set and standardized the UI across:

- Patients: 5 pages
- Appointments: 3 pages
- Doctors: 9 pages
- Departments: 3 pages
- Login, Home, and Error pages
- Doctor consultation and prescription pages
- Medical history page

The audit confirmed 23 application UI pages were covered by the new UI system. No unnecessary backend rewrites were made during this pass.

### Phase 12 – Final Testing & Bug Fixing

Performed end-to-end manual testing of authentication, authorization, patient workflows, department workflows, doctor workflows, appointments, consultations, prescriptions, medical history, validation, duplicate username handling, appointment status restrictions, and database persistence.

Important functionality gap identified and fixed:

- The original Add Doctor workflow required an existing `DOCTOR` user account.
- This was replaced with an Admin-only workflow that creates the DOCTOR user account and Doctor profile together.
- The new flow collects username, password, full name, email, department, specialization, and phone.
- The password is BCrypt encoded.
- Duplicate usernames are rejected.
- The newly created doctor can log in and access the doctor workspace.

Final testing also verified that data remained available after stopping and restarting the Spring Boot application, confirming database persistence.

### Phase 13 – Documentation

Completed the initial project documentation foundation directly in the GitHub repository.

Created:

- `README.md` – comprehensive project documentation covering project overview, technology stack, roles, authorization, modules, entity relationships, architecture, project structure, database configuration, setup instructions, routes, validation rules, UI, testing, development phases, known environment notes, scope, future scope, academic notes, and current project status.

Updated:

- `PROJECT_CONTEXT.md` – synchronized project history through Phase 13, including the UI redesign, UI consistency audit, final testing, doctor account creation improvement, database persistence verification, and documentation status.

## Current Application Features

### Authentication and Security

- Database-backed authentication.
- BCrypt password hashing.
- Role-based URL authorization.
- CSRF protection.
- Login and logout.

### Admin

- Admin dashboard.
- Department management.
- Doctor management.
- New doctor account and profile creation.
- Patient and appointment access through authorized reception routes.

### Receptionist

- Patient registration and management.
- Patient search and profile viewing.
- Patient editing.
- Medical history creation.
- Appointment creation, search, filtering, details, and permitted status updates.

### Doctor

- Assigned appointment list.
- Appointment details.
- Consultation creation and viewing.
- Prescription creation and viewing.
- Doctor ownership checks.

## Important Business Rules

- Only ADMIN can access `/admin/**`.
- ADMIN and RECEPTIONIST can access `/reception/**`.
- ADMIN and DOCTOR can access `/doctors/**`.
- Doctor usernames must be unique.
- Newly created doctor passwords are BCrypt encoded.
- A User account can be linked to at most one Doctor profile.
- Appointment doctor/date/time conflicts are prevented.
- New appointments start as `SCHEDULED`.
- Only scheduled appointments can use the controlled status update flow.
- Completed and cancelled appointments are locked from further status changes.
- Only scheduled appointments can start a consultation.
- Saving a consultation changes the appointment to `COMPLETED`.
- Doctors can access only their own assigned appointment workflows.
- Medical history belongs to an existing patient and is shown newest-first.

## Important Routes

### General

- `/login`
- `/`

### Admin

- `/admin/departments`
- `/admin/departments/new`
- `/admin/doctors`
- `/admin/doctors/new`

### Reception

- `/reception/patients`
- `/reception/appointments`
- `/reception/patients/{patientId}/medical-history/new`

### Doctor

- `/doctors/appointments`
- `/doctors/appointments/{id}`
- `/doctors/appointments/{id}/consultation`
- `/doctors/appointments/{id}/consultation/details`
- `/doctors/appointments/{id}/consultation/prescription`

## Known Environment Notes

- XAMPP MariaDB/MySQL must run on port 3307 for local development.
- XAMPP Tomcat must remain OFF because Spring Boot uses embedded Tomcat on port 8080.
- Maven does not need to be installed globally because the project includes Maven Wrapper.
- Hibernate may warn that explicitly configuring `MySQLDialect` is unnecessary. This currently does not block application startup.
- Spring may warn that `spring.jpa.open-in-view` is enabled by default. This currently does not block application startup.

## Development Rules

- GitHub repository `KrushnaManthalkar/MedSphere-Sem3` on `main` is the source of truth.
- Keep changes incremental and beginner-friendly.
- Avoid unnecessary rewrites of completed phases.
- Preserve existing verification data unless an approved reason exists to remove it.
- Update this `PROJECT_CONTEXT.md` after meaningful phases.
- Use `/doctors/**` consistently for doctor-side URLs.
- Java class/entity names such as `Doctor` remain singular where grammatically appropriate.

## Documentation / Submission Next Steps

The core Semester 3 mini application and documentation foundation are complete. Remaining academic submission work can include:

1. Capture final application screenshots.
2. Prepare a polished ER diagram.
3. Prepare a system architecture diagram.
4. Prepare database/table documentation.
5. Prepare detailed project report.
6. Prepare presentation/PPT.
7. Prepare viva questions and answers.
8. Perform a final GitHub repository cleanup before submission.
