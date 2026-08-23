# MedSphere Project Context

## Project

**MedSphere – Unified Hospital Information Management System**

- **Current version:** Semester 3 Mini Version
- **Future version:** Semester 4 Advanced Version
- **Current status:** Phase 1 complete, Phase 2 complete, Phase 3 complete, Phase 4 complete, Phase 5 complete, Phase 6 complete
- **Next phase:** Awaiting approval

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

The datasource configuration is in `src/main/resources/application.properties`. It defaults to:

```properties
jdbc:mysql://localhost:3307/medsphere?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Kolkata
```

It supports these environment-variable overrides:

- `MEDSPHERE_DB_URL`
- `MEDSPHERE_DB_USERNAME`
- `MEDSPHERE_DB_PASSWORD`

## Completed Phases

### Phase 1 – Foundation

Completed foundation work:

- Created the Spring Boot Maven project with Java 21.
- Added Maven Wrapper so Maven does not need to be installed globally.
- Added Thymeleaf templates and Bootstrap support.
- Created the basic login page and protected home page.
- Configured Spring Security with form login, logout, and a protected root route.
- Configured MySQL/MariaDB, Spring Data JPA, and Hibernate.
- Configured the application to run on port `8080`.

Important decisions:

- The UI uses Thymeleaf with Bootstrap and can later call REST APIs through JavaScript.
- Bootstrap is loaded from CDN, so no Bootstrap JAR or local asset bundle is required.
- Database settings use environment variables with local XAMPP defaults.
- The temporary in-memory authentication created in Phase 1 was intentionally replaced in Phase 3.

Key files:

- `pom.xml`
- `mvnw`, `mvnw.cmd`, `.mvn/wrapper/`
- `src/main/resources/application.properties`
- `src/main/resources/templates/login.html`
- `src/main/resources/templates/home.html`
- `src/main/java/com/medsphere/config/SecurityConfig.java`

### Phase 2 – Database Foundation

Current JPA entities:

- `Role`
- `User`
- `Department`
- `Doctor`
- `Patient`
- `Appointment`
- `Consultation`
- `Prescription`
- `MedicalHistory`

Current Spring Data JPA repositories:

- `RoleRepository`
- `UserRepository`
- `DepartmentRepository`
- `DoctorRepository`
- `PatientRepository`
- `AppointmentRepository`
- `ConsultationRepository`
- `PrescriptionRepository`
- `MedicalHistoryRepository`

Enums:

- `RoleType`: `ADMIN`, `RECEPTIONIST`, `DOCTOR`
- `AppointmentStatus`: `SCHEDULED`, `COMPLETED`, `CANCELLED`

Entity relationships:

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

Important JPA decisions:

- All primary keys use generated identity values.
- Associations are deliberately unidirectional and lazy-loaded to keep the beginner-level model simple and avoid unnecessary serialization cycles in future APIs.
- `LocalDate` is used for dates and `LocalTime` for appointment time.
- `MedicalHistory` is a simple optional patient-history record, not a full EMR module.
- Bean validation annotations are used for essential required fields, lengths, and email fields.

Verified unique constraints:

- `roles.role_name`
- `users.username`
- `departments.name`
- `doctors.user_id`
- `patients.patient_code`
- `consultations.appointment_id`

Verified database tables:

```text
appointments
consultations
departments
doctors
medical_history
patients
prescriptions
roles
users
```

### Phase 3 – Database-Backed Authentication

Completed authentication work:

- Replaced all temporary in-memory users with database-backed authentication.
- Added `CustomUserDetailsService`, which loads `User` and `Role` records through `UserRepository`.
- Uses Spring Security’s conventional authorities: `ROLE_ADMIN`, `ROLE_RECEPTIONIST`, and `ROLE_DOCTOR`.
- Uses `BCryptPasswordEncoder`; passwords are stored as BCrypt hashes, never as plaintext.
- Added `DataInitializer`, which creates only missing roles and development users. It never deletes, resets, overwrites, or recreates existing users.
- The home page displays the current logged-in username.
- Added CSRF hidden fields to the custom login and logout forms.

Current development-only credentials:

> These accounts are for local development/demo use only. Replace or remove them before any real deployment.

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `admin123` | `ADMIN` |
| `receptionist` | `reception123` | `RECEPTIONIST` |
| `doctor` | `doctor123` | `DOCTOR` |

Login and logout behavior:

- `GET /login` shows the custom Thymeleaf login page.
- Valid login redirects to `/`.
- Invalid login shows a clear error message.
- `POST /logout` ends the session and returns to login with a confirmation message.
- Unauthenticated users requesting protected routes are redirected to login.
- The current home page requires authentication.

Current authorization URL rules:

| URL area | Allowed roles | Purpose |
| --- | --- | --- |
| `/admin/**` | `ADMIN` | Future user/role, department, and doctor administration |
| `/reception/**` | `ADMIN`, `RECEPTIONIST` | Future patient registration and appointment work |
| `/doctor/**` | `ADMIN`, `DOCTOR` | Future doctor appointments, consultation, and prescriptions |
| All other routes | Authenticated users | Current protected application area |

Phase 3 tests performed:

- Verified that the `ADMIN`, `RECEPTIONIST`, and `DOCTOR` roles exist in the database.
- Verified that the three development users exist and have BCrypt hashes (`$2a$`, 60 characters).
- Verified successful login for Admin, Receptionist, and Doctor.
- Verified invalid-login feedback.
- Verified logout and post-logout protection.
- Verified unauthenticated root access redirects to login.
- Verified Receptionist receives `403` for `/admin/future`.
- Verified Doctor receives `403` for `/reception/future`.
- Verified Doctor and Admin pass their allowed URL rules; the non-existent future routes then return expected `404` responses.
- Verified the application runs on `http://localhost:8080`.

Known warnings not yet addressed:

- Hibernate warns that explicitly setting `org.hibernate.dialect.MySQLDialect` is unnecessary and MariaDB reports compatibility version `5.5.5`.
- Spring warns that `spring.jpa.open-in-view` is enabled by default.

Neither warning currently prevents database connectivity, schema updates, or authentication. Do not change completed configuration without an approved reason.

### Phase 4 – Patient Management

Completed patient-management work:

- Added `PatientController` at `/reception/patients` for the Thymeleaf MVC flow.
- Added `PatientService` and `PatientServiceImpl` between the controller and existing `PatientRepository`.
- Added `PatientForm` DTO for create/edit input. It excludes `patientCode` and `registrationDate`, so users cannot modify either value.
- Added patient list, registration, details, and edit templates under `templates/patients/`.
- Added clean Bootstrap list, form, profile, search, View, and Edit UI elements without redesigning the application.
- Added a Patient Management link on the home page only for Admin and Receptionist users.
- Added `PatientNotFoundException`, `GlobalExceptionHandler`, and an error page for safe patient-related errors.

Patient code generation:

- The service generates codes in the format `MSP-YYYY-000001`.
- It finds the highest code for the current year and increments its six-digit numeric suffix.
- Registration is synchronized within this single application instance, and the existing unique database constraint on `patients.patient_code` remains the final duplicate-protection layer.
- The code and server-generated registration date are not included in create/edit forms and are not copied during updates.

Validation rules:

- Full name, date of birth, gender, and phone are required.
- Date of birth must be in the past.
- Email is optional but validated when supplied.
- Field lengths follow the existing Patient entity constraints.
- Invalid form input stays on the form and displays Bootstrap validation feedback.

Phase 4 authorization:

- All patient-management URLs are under `/reception/patients/**`.
- `ADMIN` and `RECEPTIONIST` can list, search, register, view, and edit patients.
- `DOCTOR` receives `403 Forbidden` for these patient-management URLs; no doctor patient screens were created.
- No physical patient deletion, soft deletion, or archiving was implemented.

Phase 4 files created or modified:

- `src/main/java/com/medsphere/controller/PatientController.java`
- `src/main/java/com/medsphere/dto/PatientForm.java`
- `src/main/java/com/medsphere/service/PatientService.java`
- `src/main/java/com/medsphere/service/impl/PatientServiceImpl.java`
- `src/main/java/com/medsphere/repository/PatientRepository.java`
- `src/main/java/com/medsphere/exception/PatientNotFoundException.java`
- `src/main/java/com/medsphere/exception/GlobalExceptionHandler.java`
- `src/main/resources/templates/patients/list.html`
- `src/main/resources/templates/patients/form.html`
- `src/main/resources/templates/patients/details.html`
- `src/main/resources/templates/patients/edit.html`
- `src/main/resources/templates/error.html`
- `src/main/java/com/medsphere/controller/HomeController.java`
- `src/main/resources/templates/home.html`

Phase 4 tests performed:

- Maven Wrapper build completed successfully.
- Admin registered two necessary verification patients.
- Generated codes were verified as `MSP-2026-000001` and `MSP-2026-000002` and confirmed sequential.
- The records were verified directly in MariaDB on port 3307.
- Search by patient code, name, and phone succeeded.
- Patient profile opening and patient editing succeeded.
- Patient code and registration date remained unchanged after edit.
- Invalid required fields and invalid email displayed validation feedback.
- Receptionist patient-page access succeeded.
- Doctor patient-page access returned `403 Forbidden`.
- Existing authentication logout behavior succeeded.
- The application was verified running on port 8080.

The two Phase 4 verification patients remain in the database because the project rule is to avoid deleting/resetting data. They are clearly named `Phase Four Verification One Updated` and `Phase Four Verification Two`.

### Phase 5 – Department & Doctor Management

Completed Admin-only management work:

- Added Department create, list, and edit pages at `/admin/departments`.
- Added Doctor create, list, profile-view, and edit pages at `/admin/doctors`.
- Added `DepartmentService` / `DepartmentServiceImpl` and `DoctorService` / `DoctorServiceImpl`.
- Added form DTOs for Department and Doctor input.
- Department names are validated and duplicate names are rejected before saving.
- A Doctor profile must select an existing `User` account whose role is `DOCTOR`.
- A Doctor user account can be linked to only one Doctor profile; already-assigned users are excluded from the creation form and checked again in the service.
- Every Doctor profile requires a Department, Specialization, and Phone.
- Added an Admin-only home-page link to the new management screens.
- No deletion, appointments, consultations, prescriptions, dashboards, billing, lab, pharmacy, or advanced modules were added.

Phase 5 authorization:

- All management routes use `/admin/**`, which is restricted by Spring Security to `ADMIN`.
- Receptionist and Doctor users receive `403 Forbidden` for Department and Doctor management URLs.

Phase 5 files created or modified:

- `src/main/java/com/medsphere/controller/DepartmentController.java`
- `src/main/java/com/medsphere/controller/DoctorController.java`
- `src/main/java/com/medsphere/dto/DepartmentForm.java`
- `src/main/java/com/medsphere/dto/DoctorForm.java`
- `src/main/java/com/medsphere/service/DepartmentService.java`
- `src/main/java/com/medsphere/service/DoctorService.java`
- `src/main/java/com/medsphere/service/impl/DepartmentServiceImpl.java`
- `src/main/java/com/medsphere/service/impl/DoctorServiceImpl.java`
- `src/main/java/com/medsphere/exception/ResourceNotFoundException.java`
- `src/main/java/com/medsphere/exception/GlobalExceptionHandler.java`
- `src/main/java/com/medsphere/repository/DepartmentRepository.java`
- `src/main/java/com/medsphere/repository/DoctorRepository.java`
- `src/main/java/com/medsphere/repository/UserRepository.java`
- `src/main/resources/templates/departments/`
- `src/main/resources/templates/doctors/`
- `src/main/java/com/medsphere/controller/HomeController.java`
- `src/main/resources/templates/home.html`

Phase 5 tests performed:

- Maven Wrapper build completed successfully.
- Admin created the required verification department `Phase Five Verification Department`.
- Duplicate Department name validation was verified.
- Admin created a Doctor profile linked to existing database user `doctor` with `ROLE_DOCTOR`.
- The Doctor was assigned to the verification department and the Doctor phone edit was verified in MariaDB.
- Admin access to Department and Doctor management succeeded.
- Receptionist and Doctor access to `/admin/**` returned `403 Forbidden`.
- Receptionist access to existing Patient Management continued to succeed.
- The application remained available on port 8080.

The Phase 5 verification Department and Doctor profile remain in the database because the project rule is to avoid deleting/resetting data.

### Phase 6 – Appointment Management

Completed appointment-management work:

- Added Receptionist/Admin appointment management at `/reception/appointments`.
- Added appointment listing with search by patient code, patient name, or doctor name.
- Added appointment status filtering for `SCHEDULED`, `COMPLETED`, and `CANCELLED`.
- Added new appointment creation using existing Patient and Doctor records.
- Added server-side validation for required patient, doctor, date, and time fields, plus a maximum 500-character reason.
- Appointment dates cannot be in the past.
- Added doctor/date/time conflict protection so a doctor cannot receive two appointments at the same date and time.
- New appointments are created with `SCHEDULED` status.
- Added appointment details view.
- Added appointment status update from `SCHEDULED` to the selected valid status.
- Status changes are blocked once an appointment is no longer `SCHEDULED`.
- Added Appointment Management navigation to the authenticated home page for Admin and Receptionist users.
- Kept the existing `AppointmentForm` structure; no unnecessary replacement of the DTO was performed.
- No consultation, prescription, medical-history UI, dashboards, billing, or other advanced modules were added in this phase.

Phase 6 authorization:

- Appointment URLs are under `/reception/appointments/**`.
- `ADMIN` and `RECEPTIONIST` are allowed by the existing `/reception/**` Spring Security rule.
- Doctor-specific appointment screens were not added in this phase.

Phase 6 files created or modified:

- `src/main/java/com/medsphere/controller/AppointmentController.java`
- `src/main/java/com/medsphere/dto/AppointmentForm.java`
- `src/main/java/com/medsphere/service/AppointmentService.java`
- `src/main/java/com/medsphere/service/impl/AppointmentServiceImpl.java`
- `src/main/java/com/medsphere/repository/AppointmentRepository.java`
- `src/main/resources/templates/appointments/list.html`
- `src/main/resources/templates/appointments/form.html`
- `src/main/resources/templates/appointments/details.html`
- `src/main/java/com/medsphere/controller/HomeController.java`
- `src/main/resources/templates/home.html`

Phase 6 tests performed:

- Verified the Appointment Management page opens on `http://localhost:8080/reception/appointments`.
- Verified existing patient and doctor data appears correctly in appointment management.
- Verified appointment data is displayed with date, time, patient, doctor, reason, and status.
- Verified the appointment details page opens successfully.
- Verified a scheduled appointment can be changed to `COMPLETED` and the updated status is reflected in the appointment list.
- Verified the home page navigation provides Appointment Management access for the Receptionist/Admin flow, removing the need to enter the direct URL manually.
- Verified the application continues running on port 8080 with XAMPP MariaDB on port 3307.

Known Phase 6 notes:

- The current appointment status rule intentionally allows changes only while the appointment is `SCHEDULED`; completed/cancelled appointments cannot be changed again.
- The current search implementation filters the loaded appointment list in the service layer, which is acceptable for the Semester 3 mini scope but may be optimized with repository queries later if the dataset grows significantly.

## Semester 3 Mini Scope

The Semester 3 Mini version should include:

- Authentication
- Patient Management
- Doctor Management
- Department Management
- Appointment Management
- Doctor Consultation
- Basic Prescription Management
- Patient Medical History
- Role-specific basic dashboards

Do **not** add any of the following unless explicitly approved:

- Billing
- Laboratory Management
- Pharmacy Inventory
- Admission/Discharge
- Bed Management
- Notifications
- QR Patient ID
- AI features
- Multi-building hospital management
- Multi-hospital management
- Advanced analytics
- Payment gateway
- Other advanced features

## Semester 4 Future Direction

The Semester 3 project is intended to become the foundation for a Semester 4 advanced centralized hospital information management system. Advanced features must be designed only after the Semester 3 Mini modules are complete and explicitly approved.

## Important Development Rules

- Never implement future phases automatically.
- Work one phase at a time.
- Do not modify completed phases unnecessarily.
- Do not delete or reset the database.
- Prefer a beginner-friendly architecture that an MCA student can explain in a viva.
- Test each phase before proceeding.
- Keep the code modular and extensible.
- Record major architecture decisions in this file.
- When a phase is completed, update this file with implementation details, files changed, tests performed, and known issues.
- Always inspect the current project before making assumptions.
- Use the GitHub repository as the current source of truth when reviewing existing project files.
- After meaningful local changes, commit and push the working state so the repository remains synchronized.

## Current Development Phase

**No active phase — Phase 6 complete**

## Next Objective

Await approval for the next Semester 3 Mini phase. Do not implement any further module automatically.

Always inspect this file and the current project before beginning the next approved phase.
