# MedSphere Project Context

## Project

**MedSphere – Unified Hospital Information Management System**

- **Current version:** Semester 3 Mini Version
- **Current status:** Phase 1 complete, Phase 2 complete, Phase 3 complete, Phase 4 complete, Phase 5 complete, Phase 6 complete, Phase 7 complete, Phase 8 complete
- **Next phase:** To be planned after Phase 8 verification

This file is the permanent project context and development history. Update it after every approved phase with the implementation details, files changed, tests performed, important decisions, and known issues.

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
- **JPA schema mode:** `spring.jpa.hibernate.ddl-auto=update`

The datasource configuration is in `src/main/resources/application.properties` and defaults to the local XAMPP MariaDB instance.

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

Development-only accounts:

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
| All other routes | Authenticated users |

### Phase 4 – Patient Management

Completed patient registration, list/search, profile details, and edit flow at `/reception/patients`. Added PatientForm, service layer, patient validation, generated `MSP-YYYY-000001` patient codes, immutable patient code/registration date, error handling, Bootstrap templates, and role-specific navigation. Verified patient data and authorization in MariaDB.

### Phase 5 – Department & Doctor Management

Completed Admin-only Department and Doctor management. Added department and doctor services/forms/controllers/templates, doctor-user linking, department assignment, duplicate validation, and role restrictions. Verified `Phase Five Verification Department` and its doctor profile in the database.

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
- Appointment Management navigation for Admin/Receptionist.

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

Implemented:

- Doctor appointment list at `/doctors/appointments`.
- Doctor appointment details at `/doctors/appointments/{id}`.
- Doctor ownership checks so a doctor can only access their own appointments.
- Consultation DTO with symptoms, diagnosis, notes, and consultation date.
- Consultation service and implementation under the existing `service` / `service/impl` structure.
- Repository lookup by appointment ID and duplicate consultation protection.
- Consultation form at `/doctors/appointments/{id}/consultation`.
- Consultation save through POST to the same route.
- Scheduled appointments only can start a consultation.
- Successful consultation save changes the appointment status to `COMPLETED`.
- Consultation details at `/doctors/appointments/{id}/consultation/details`.
- Appointment Details UI shows `Start Consultation` for scheduled appointments and `View Consultation` for completed appointments.
- Doctor navigation on the home page through `My Appointments`.
- Templates are under `src/main/resources/templates/doctors/`.

Phase 7 files created or modified:

- `src/main/java/com/medsphere/controller/DoctorAppointmentController.java`
- `src/main/java/com/medsphere/controller/DoctorAppointmentDetailsController.java`
- `src/main/java/com/medsphere/controller/DoctorConsultationController.java`
- `src/main/java/com/medsphere/controller/DoctorConsultationDetailsController.java`
- `src/main/java/com/medsphere/dto/ConsultationForm.java`
- `src/main/java/com/medsphere/service/ConsultationService.java`
- `src/main/java/com/medsphere/service/impl/ConsultationServiceImpl.java`
- `src/main/java/com/medsphere/repository/ConsultationRepository.java`
- `src/main/java/com/medsphere/controller/HomeController.java`
- `src/main/resources/templates/home.html`
- `src/main/resources/templates/doctors/appointments.html`
- `src/main/resources/templates/doctors/appointment-details.html`
- `src/main/resources/templates/doctors/consultation-form.html`
- `src/main/resources/templates/doctors/consultation-details.html`

Phase 7 tests performed:

- Doctor login succeeded.
- My Appointments page successfully loaded assigned appointments from MariaDB.
- Appointment Details page successfully opened for the doctor's appointment.
- Start Consultation button appeared only for a `SCHEDULED` appointment.
- Consultation form successfully opened.
- Consultation was successfully saved with test symptoms, diagnosis, notes, and consultation date.
- Consultation record was verified in the `consultations` table in the `medsphere` database on XAMPP MariaDB port 3307.
- Appointment status changed from `SCHEDULED` to `COMPLETED` after successful consultation save.
- Consultation Details page successfully loaded the saved record.
- View Consultation button was added for completed appointments.
- Existing completed appointments do not expose Start Consultation.
- Doctor ownership checks were implemented in appointment and consultation controllers.
- Application continues to run on port 8080 with XAMPP Tomcat kept OFF.

Important Phase 7 decisions:

- Standardized doctor-side URL paths under `/doctors/**` and kept Thymeleaf templates under `templates/doctors/`.
- Kept the existing beginner-friendly layered architecture and did not add unnecessary advanced features.
- Consultation is one-to-one with Appointment and is located through appointment ID.
- Consultation creation is limited to scheduled appointments; saving it marks the appointment completed.
- No prescription, medical history, dashboard, billing, lab, pharmacy, or advanced module work was included in Phase 7.

### Phase 8 – Basic Prescription

Completed the basic prescription workflow for the Semester 3 mini version.

Flow:

```text
Consultation Details
    ↓
Add Prescription
    ↓
Prescription Form
    ↓
Medicine / Dosage / Frequency / Duration / Instructions
    ↓
Save Prescription
    ↓
Database
    ↓
Consultation Details
    ↓
Prescription List
```

Implemented:

- Prescription entity remains linked to Consultation through a mandatory many-to-one relationship.
- Added `PrescriptionRepository` with lookup by consultation ID.
- Added `PrescriptionService` and `PrescriptionServiceImpl`.
- Added `PrescriptionForm` for medicine name, dosage, frequency, duration, and optional instructions.
- Added `DoctorPrescriptionController` under `/doctors/appointments`.
- Added GET route `/doctors/appointments/{id}/consultation/prescription` to open the form.
- Added POST route `/doctors/appointments/{id}/consultation/prescription` to save a prescription.
- Added doctor ownership checks before accessing or saving a prescription for an appointment.
- Prescription form includes CSRF protection and required fields for core prescription information.
- Consultation Details page lists all prescriptions belonging to the consultation.
- After saving, the user is redirected back to Consultation Details.
- Kept the implementation basic; no pharmacy inventory, medicine master, billing, or advanced prescription features were added.

Phase 8 files created or modified:

- `src/main/java/com/medsphere/repository/PrescriptionRepository.java`
- `src/main/java/com/medsphere/service/PrescriptionService.java`
- `src/main/java/com/medsphere/service/impl/PrescriptionServiceImpl.java`
- `src/main/java/com/medsphere/dto/PrescriptionForm.java`
- `src/main/java/com/medsphere/controller/DoctorPrescriptionController.java`
- `src/main/resources/templates/doctors/prescription-form.html`
- `src/main/resources/templates/doctors/consultation-details.html`
- `src/main/java/com/medsphere/entity/Prescription.java` was retained as the existing Phase 2 prescription entity.

Phase 8 tests performed:

- Doctor login and doctor appointment access continued to work.
- Consultation Details page successfully displayed the `+ Add Prescription` button.
- Prescription form initially exposed a Thymeleaf model error because the controller did not add the `doctor` object to the model; this was fixed with `model.addAttribute("doctor", doctor)`.
- After the fix, the prescription form successfully opened.
- Test prescription was successfully saved with medicine `Paracetamol`, dosage `500mg`, frequency `Twice daily`, duration `5 days`, and instruction `After Food`.
- Saved prescription successfully appeared in the Consultation Details prescription table.
- Prescription data was successfully persisted and retrieved from MariaDB.
- Final doctor-side URL convention was standardized to `/doctors/**`.

Important Phase 8 decisions:

- Reused the existing doctor authentication and ownership-check pattern.
- Kept prescription functionality attached to an existing consultation rather than creating standalone prescriptions.
- Kept the UI simple and viva-friendly for the Semester 3 mini project.
- Prescriptions are displayed directly within Consultation Details instead of adding an unnecessary separate prescription-details page.

## Known Warnings / Environment Notes

- XAMPP MariaDB/MySQL must be running on port 3307 for local development.
- XAMPP Tomcat must remain OFF because Spring Boot uses embedded Tomcat on port 8080.
- Hibernate may warn that explicitly configuring `MySQLDialect` is unnecessary and may report MariaDB compatibility details.
- Spring may warn that `spring.jpa.open-in-view` is enabled by default.
- These warnings currently do not prevent the application from running.

## Development Rules

- GitHub repository `KrushnaManthalkar/MedSphere-Sem3` on `main` is the source of truth.
- Keep changes incremental and beginner-friendly.
- Avoid unnecessary rewrites of completed phases.
- Preserve existing verification data unless there is an approved reason to remove it.
- Update this `PROJECT_CONTEXT.md` at the end of each completed phase.
- Use `/doctors/**` consistently for doctor-side URL paths; Java class/entity names such as `Doctor` remain singular where grammatically appropriate.

## Next Objective

To be planned after Phase 8 verification. Before starting a new feature, review the current repository and preserve the working Phase 8 prescription flow.