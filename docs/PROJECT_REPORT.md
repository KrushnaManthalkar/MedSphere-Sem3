# MedSphere – Project Report

## Unified Hospital Information Management System

**Academic Project:** MCA Semester 3 Mini Project  
**Technology:** Java 21, Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, Bootstrap, MariaDB/MySQL

> **Submission note:** This Markdown document is the structured master draft for the final college project report. Replace the placeholders marked `TODO` with institution-specific information and manually insert screenshots/figures where indicated.

---

# 1. Title Page

**Project Title:** MedSphere – Unified Hospital Information Management System

**Submitted By:**  
TODO – Student Name / Roll Number / PRN

**Course:** MCA – Semester 3

**Institute:**  
TODO – College / Institute Name

**Academic Year:**  
TODO – Academic Year

**Guided By:**  
TODO – Guide Name

**Department:**  
TODO – Department Name

---

# 2. Certificate

TODO – Insert the college-provided project certificate format here.

---

# 3. Declaration

TODO – Insert the college-required student declaration here.

---

# 4. Acknowledgement

TODO – Add acknowledgement according to the college's preferred format.

Suggested points:

- Project guide
- Department/faculty
- Institute
- Friends/classmates
- Family

---

# 5. Abstract

MedSphere is a web-based Unified Hospital Information Management System developed as an MCA Semester 3 mini project. The system provides a centralized platform for managing basic hospital information and workflows involving administrators, receptionists, doctors, patients, appointments, consultations, prescriptions, and medical history.

The application is implemented using Java 21 and Spring Boot with Spring MVC, Spring Security, Spring Data JPA/Hibernate, Thymeleaf, Bootstrap, and MariaDB/MySQL. A layered architecture separates controllers, business services, repositories, and persistent entities. Database-backed authentication and BCrypt password encoding are used to secure user accounts, while role-based authorization controls access to Admin, Reception, and Doctor workflows.

The system supports patient registration and management, department management, doctor account/profile creation, appointment scheduling, doctor consultations, prescriptions, and patient medical history. Validation and business rules are implemented at the application level, including duplicate username protection, doctor/date/time appointment conflict protection, controlled appointment status transitions, and doctor ownership checks.

The Semester 3 version focuses on core hospital information workflows and intentionally excludes advanced modules such as billing, pharmacy inventory, laboratory management, wards, and advanced notifications. The project was manually tested for authentication, authorization, functional workflows, validation, and database persistence.

---

# 6. Table of Contents

TODO – Generate/update the final table of contents after formatting the final report in Word/PDF.

Recommended chapters:

1. Introduction
2. Problem Statement
3. Objectives
4. Scope
5. Existing System
6. Proposed System
7. Requirements
8. Feasibility Study
9. System Design
10. Database Design
11. System Modules
12. Implementation
13. Security
14. Testing
15. Results
16. Limitations
17. Future Scope
18. Conclusion
19. References
20. Appendices

---

# 7. Introduction

Hospitals handle a large amount of information related to patients, doctors, departments, appointments, consultations, prescriptions, and medical history. Managing these records manually or through disconnected processes can make information difficult to organize and retrieve.

MedSphere was developed to demonstrate a simple centralized hospital information system that manages these core records through a role-based web application. The system provides separate workflows for administrators, receptionists, and doctors while maintaining relationships between patients, appointments, consultations, prescriptions, and medical history.

The project emphasizes a clean, understandable architecture suitable for an academic application while demonstrating practical Java and Spring Boot development concepts.

---

# 8. Problem Statement

A basic hospital environment requires coordinated management of:

- Patient registration and information
- Hospital departments
- Doctor profiles and accounts
- Appointment scheduling
- Doctor consultations
- Prescriptions
- Patient medical history
- User authentication and access control

Without a centralized application, these activities can become difficult to manage consistently. MedSphere addresses this problem by providing a single database-backed application with role-specific access and connected records.

---

# 9. Objectives

The major objectives of MedSphere are:

1. To develop a centralized hospital information management application.
2. To implement secure user authentication.
3. To implement role-based authorization for Admin, Receptionist, and Doctor users.
4. To manage patient registration and patient profiles.
5. To manage hospital departments and doctors.
6. To allow administrators to create doctor accounts and professional profiles.
7. To manage patient appointments with doctors.
8. To allow doctors to record consultations.
9. To manage prescriptions associated with consultations.
10. To maintain patient medical history records.
11. To persist information in a relational database.
12. To provide a responsive and consistent web interface.
13. To demonstrate Spring Boot, JPA/Hibernate, Spring Security, MVC, validation, and database concepts.

---

# 10. Scope of the Project

## 10.1 Included Scope

The Semester 3 mini version includes:

- Authentication and logout
- Role-based authorization
- Admin dashboard
- Reception workflow
- Doctor workflow
- Patient registration and management
- Department management
- Doctor account/profile management
- Appointment scheduling and management
- Doctor consultation
- Prescription management
- Medical history
- Validation and error handling
- Responsive web UI
- Relational database persistence

## 10.2 Excluded Scope

The current mini version does not include:

- Billing and invoices
- Pharmacy inventory
- Laboratory management
- Lab reports
- Ward and bed management
- Advanced notifications
- Online payment gateway
- Patient portal
- Advanced analytics
- Multi-hospital management

These can be considered for future development.

---

# 11. Existing System

A basic/manual hospital environment may use paper records, spreadsheets, or separate systems for different activities. Such an approach can lead to:

- Duplicate information
- Difficulty searching records
- Limited access control
- Manual appointment coordination
- Difficulty maintaining connected clinical records
- Increased administrative effort

The exact existing system at the target organization is outside the scope of this academic project; this section describes the general problem domain rather than claiming an analysis of a specific hospital.

---

# 12. Proposed System

MedSphere proposes a centralized role-based application in which hospital information is stored in a relational database and accessed through a Spring Boot web application.

The proposed system provides:

- Centralized patient information
- Department and doctor management
- Controlled appointment scheduling
- Doctor-specific consultation workflow
- Consultation-linked prescriptions
- Patient medical history
- Secure authentication
- Role-based access control
- Validation and business rules
- Persistent database storage

---

# 13. User Roles

## 13.1 Administrator

The Admin manages hospital master information and doctor accounts.

Main responsibilities:

- Manage departments
- Create doctor accounts
- Create doctor profiles
- Edit doctor profiles
- View doctors
- Access authorized patient and appointment workflows

## 13.2 Receptionist

The Receptionist manages front-desk operations.

Main responsibilities:

- Register patients
- Search patients
- View and edit patient information
- Add medical history
- Create appointments
- Search/filter appointments
- View appointment details
- Perform permitted appointment status updates

## 13.3 Doctor

The Doctor manages assigned clinical workflows.

Main responsibilities:

- View assigned appointments
- Open appointment details
- Start consultations
- Record symptoms, diagnosis, and notes
- Complete consultations
- Add prescriptions
- View consultation and prescription information

---

# 14. Functional Requirements

### Authentication

- User shall be able to log in using a username and password.
- Passwords shall be stored using BCrypt encoding.
- Users shall be able to log out.

### Authorization

- Admin-only functions shall be protected.
- Reception functions shall be available to Admin and Receptionist users.
- Doctor functions shall be available to Admin and Doctor users.

### Patient Management

- Register a patient.
- Generate a unique patient code.
- Search patients.
- View patient details.
- Edit patient information.
- Add and view medical history.

### Department Management

- Add department.
- View department list.
- Edit department.

### Doctor Management

- Create a doctor account.
- Assign the Doctor role.
- Create a linked Doctor profile.
- Assign department.
- Store specialization and phone.
- Edit doctor profile.

### Appointment Management

- Create appointments.
- Select patient and doctor.
- Store date, time, status, and reason.
- Search/filter appointments.
- Prevent doctor/date/time conflicts.
- Control status transitions.

### Consultation

- Allow a doctor to start a consultation for an eligible appointment.
- Record symptoms, diagnosis, notes, and consultation date.
- Mark the appointment completed after successful consultation.

### Prescription

- Add medicine details to a consultation.
- Store dosage, frequency, duration, and instructions.
- Display prescriptions on consultation details.

---

# 15. Non-Functional Requirements

- **Security:** Authentication, authorization, BCrypt password hashing, and CSRF protection.
- **Usability:** Clear role-specific dashboards and consistent UI.
- **Maintainability:** Layered architecture with separated controllers, services, repositories, and entities.
- **Reliability:** Database persistence and validation/business rules.
- **Scalability:** The layered design provides a foundation for adding future modules.
- **Performance:** The Semester 3 application is designed for local academic use rather than production-scale hospital workloads.
- **Portability:** The application can run on a Java 21 environment with MariaDB/MySQL.

---

# 16. Feasibility Study

## 16.1 Technical Feasibility

The system uses established Java and Spring technologies, a relational database, and a standard web browser. The project can run locally using Java 21, Maven Wrapper, Spring Boot, and XAMPP MariaDB/MySQL.

## 16.2 Economic Feasibility

The project uses commonly available development tools and open-source frameworks. No specialized commercial software is required for the local academic implementation.

## 16.3 Operational Feasibility

The system separates functionality by role, making the main workflows straightforward for administrators, receptionists, and doctors. The responsive interface supports normal browser-based use.

---

# 17. System Architecture

MedSphere follows a simple layered MVC architecture:

```text
+-----------------------------+
| Browser / User Interface    |
| Thymeleaf + Bootstrap + JS  |
+-------------+---------------+
              |
              v
+-----------------------------+
| Controller Layer            |
| Spring MVC                  |
+-------------+---------------+
              |
              v
+-----------------------------+
| Service Layer               |
| Business Logic              |
+-------------+---------------+
              |
              v
+-----------------------------+
| Repository Layer            |
| Spring Data JPA             |
+-------------+---------------+
              |
              v
+-----------------------------+
| MariaDB / MySQL             |
+-----------------------------+
```

### Security Layer

Spring Security operates across the web request flow to authenticate users and authorize protected URL areas.

### Local Deployment

- Spring Boot embedded Tomcat: `localhost:8080`
- XAMPP MariaDB/MySQL: `localhost:3307`

**Figure Placeholder:**

> `[INSERT FINAL SYSTEM ARCHITECTURE DIAGRAM HERE]`

---

# 18. Database Design

The current database contains these major entities:

- Role
- User
- Department
- Doctor
- Patient
- Appointment
- Consultation
- Prescription
- Medical History

The major relationships are:

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

Detailed database documentation is maintained separately in `docs/DATABASE_DOCUMENTATION.md`.

**Figure Placeholder:**

> `[INSERT FINAL ER DIAGRAM IMAGE HERE]`

---

# 19. System Modules

## Module 1 – Authentication

Provides login, logout, database-backed authentication, BCrypt password encoding, and role-based authorization.

## Module 2 – Patient Management

Provides patient registration, unique patient codes, searching, profiles, editing, and medical history integration.

## Module 3 – Department Management

Provides department creation, listing, editing, validation, and doctor assignment support.

## Module 4 – Doctor Management

Provides doctor listing, profile editing, and Admin-only creation of a new Doctor user account and Doctor profile.

## Module 5 – Appointment Management

Provides appointment scheduling, searching, filtering, details, status handling, and conflict protection.

## Module 6 – Consultation Management

Allows doctors to record consultation information for eligible assigned appointments.

## Module 7 – Prescription Management

Allows doctors to add medicine instructions to consultation records.

## Module 8 – Medical History

Allows authorized reception users to add and view patient medical history records.

## Module 9 – Dashboard and UI

Provides role-aware dashboards, responsive layouts, consistent navigation, tables, forms, cards, status indicators, and error pages.

---

# 20. Main Workflows

## 20.1 Doctor Creation Workflow

```text
Admin Login
    ↓
Add Doctor
    ↓
Username + Password + Full Name + Email
    ↓
Department + Specialization + Phone
    ↓
Save
    ↓
Create ROLE_DOCTOR User
    ↓
Create Doctor Profile
    ↓
Doctor Login
```

## 20.2 Appointment Workflow

```text
Receptionist/Admin
    ↓
Create Appointment
    ↓
Select Patient + Doctor
    ↓
Date + Time + Reason
    ↓
SCHEDULED
    ↓
Doctor Views Appointment
    ↓
Start Consultation
    ↓
Save Consultation
    ↓
COMPLETED
```

## 20.3 Prescription Workflow

```text
Consultation Details
    ↓
Add Prescription
    ↓
Medicine + Dosage + Frequency + Duration + Instructions
    ↓
Save
    ↓
Prescription displayed with consultation
```

## 20.4 Medical History Workflow

```text
Patient Profile
    ↓
Medical History
    ↓
Add Record
    ↓
Condition + Details + Record Date
    ↓
Save
    ↓
History displayed newest-first
```

---

# 21. Security Implementation

MedSphere uses Spring Security for authentication and authorization.

Authorization rules:

| URL | Roles |
| --- | --- |
| `/admin/**` | ADMIN |
| `/reception/**` | ADMIN, RECEPTIONIST |
| `/doctors/**` | ADMIN, DOCTOR |
| Other application routes | Authenticated users |

Passwords use BCrypt encoding. CSRF protection is enabled by Spring Security's form-login setup.

The development seed accounts are created only if they do not already exist, so existing database records are not overwritten during application startup.

---

# 22. Validation and Business Rules

Important rules implemented by the application include:

- Required fields are validated.
- Email fields are validated where applicable.
- New doctor passwords must meet the configured minimum length.
- Doctor usernames must be unique.
- A Doctor user cannot be linked to multiple Doctor profiles.
- Appointment doctor/date/time conflicts are prevented.
- New appointments start as `SCHEDULED`.
- Completed and cancelled appointments cannot be changed through the controlled status workflow.
- Only eligible scheduled appointments can start a consultation.
- Saving a consultation marks the appointment as completed.
- Doctors are restricted to their assigned appointment workflows.
- Medical history must belong to an existing patient.

---

# 23. User Interface Design

The application uses a common UI foundation with Bootstrap 5.3.3 and `app.css`.

The redesign includes:

- MedSphere navigation bar
- Role-aware dashboards
- Consistent page headers
- Responsive forms
- Content cards
- Data tables
- Status badges
- Empty states
- Patient/record badges
- Error pages

**Screenshot Placeholder – Login:**

> `[INSERT LOGIN SCREENSHOT HERE]`

**Screenshot Placeholder – Admin Dashboard:**

> `[INSERT ADMIN DASHBOARD SCREENSHOT HERE]`

**Screenshot Placeholder – Reception Dashboard:**

> `[INSERT RECEPTION DASHBOARD SCREENSHOT HERE]`

**Screenshot Placeholder – Doctor Dashboard:**

> `[INSERT DOCTOR DASHBOARD SCREENSHOT HERE]`

---

# 24. Implementation Details

## Backend

The backend is implemented using Spring Boot and follows a layered design.

### Controllers

Controllers handle web requests, model preparation, validation results, and redirects.

### Services

Services contain application business logic and coordinate repository operations.

### Repositories

Spring Data JPA repositories provide persistence operations and derived queries.

### Entities

JPA entities represent database records and relationships.

### DTOs / Form Objects

Form classes carry validated input from Thymeleaf forms into the service layer.

## Frontend

Thymeleaf renders server-side HTML pages. Bootstrap provides responsive layout and reusable UI components, while `app.css` provides MedSphere-specific styling.

---

# 25. Testing

Testing was performed manually against the running application.

| Area | Test | Result |
| --- | --- | --- |
| Admin Login | Valid credentials | PASS |
| Receptionist Login | Valid credentials | PASS |
| Doctor Login | Valid credentials | PASS |
| Authorization | Unauthorized Admin URL blocked | PASS |
| Authorization | Unauthorized Reception URL blocked | PASS |
| Patient | Create/search/view/edit | PASS |
| Medical History | Create and display | PASS |
| Department | Create/edit/list | PASS |
| Doctor | Create account/profile | PASS |
| Doctor | Duplicate username rejected | PASS |
| Doctor | New account login | PASS |
| Appointment | Create/list/details | PASS |
| Appointment | Status restriction | PASS |
| Consultation | Create/view | PASS |
| Prescription | Create/view | PASS |
| Validation | Invalid required inputs | PASS |
| Persistence | Data retained after application restart | PASS |

**Screenshot Placeholder – Validation:**

> `[INSERT VALIDATION SCREENSHOT HERE]`

**Screenshot Placeholder – Security/Access Denied:**

> `[INSERT ACCESS CONTROL SCREENSHOT HERE]`

---

# 26. Results

The completed Semester 3 version successfully demonstrates a connected hospital information workflow from patient registration and appointment scheduling through doctor consultation and prescription management, while maintaining patient medical history.

The final application provides:

- Secure role-based access
- Persistent relational storage
- Connected JPA entities
- Controlled appointment workflow
- Doctor consultation workflow
- Prescription records
- Medical history records
- Responsive user interface
- Validation and error handling

---

# 27. Limitations

The current project is an academic mini version and has limitations:

- No billing system.
- No pharmacy inventory.
- No laboratory module.
- No ward/bed management.
- No patient self-service portal.
- No production deployment configuration.
- No advanced reporting/analytics.
- Development credentials are included for demonstration only.
- Local database configuration is intended for development.

---

# 28. Future Scope

Possible future enhancements include:

1. Billing and invoice management.
2. Pharmacy inventory.
3. Laboratory and report management.
4. Ward and bed management.
5. Patient portal.
6. Doctor availability scheduling.
7. Appointment reminders.
8. Email/SMS notifications.
9. Prescription PDF generation.
10. Advanced analytics and reports.
11. Audit logging.
12. Production deployment.
13. More granular permissions.
14. Multi-hospital support.

---

# 29. Conclusion

MedSphere successfully implements a basic Unified Hospital Information Management System for an MCA Semester 3 mini project. The application combines patient management, department and doctor management, appointments, consultations, prescriptions, and medical history in a single database-backed system.

The project demonstrates practical use of Java 21, Spring Boot, Spring MVC, Spring Security, Spring Data JPA/Hibernate, Thymeleaf, Bootstrap, validation, and relational database design. The layered architecture keeps the application understandable and maintainable, while role-based security provides controlled access to different hospital workflows.

The completed implementation provides a strong foundation for future expansion into a more comprehensive hospital management system.

---

# 30. References

TODO – Add the references required by the college format.

Suggested technical references:

- Spring Boot documentation
- Spring Security documentation
- Spring Data JPA documentation
- Hibernate/JPA documentation
- Thymeleaf documentation
- Bootstrap documentation
- MySQL/MariaDB documentation
- Java documentation

---

# 31. Appendices

## Appendix A – Application Screenshots

TODO – Insert final screenshots manually.

Suggested screenshot sequence:

1. Login
2. Admin Dashboard
3. Department List
4. Add Department
5. Doctor List
6. Add Doctor
7. Doctor Details
8. Reception/Patient List
9. Add Patient
10. Patient Details
11. Medical History
12. Appointment List
13. Add Appointment
14. Appointment Details
15. Doctor Appointments
16. Consultation Form
17. Consultation Details
18. Prescription Form
19. Validation Message
20. Access Control / Error Page

## Appendix B – ER Diagram

> `[INSERT FINAL ER DIAGRAM IMAGE HERE]`

## Appendix C – System Architecture Diagram

> `[INSERT FINAL ARCHITECTURE DIAGRAM HERE]`

## Appendix D – Database Screenshots

> `[INSERT DATABASE / TABLE SCREENSHOTS HERE]`

## Appendix E – GitHub Repository

> `[INSERT FINAL GITHUB REPOSITORY LINK HERE]`

---

# 32. Final Submission Checklist

- [ ] Replace student/institute/guide placeholders.
- [ ] Add certificate.
- [ ] Add declaration.
- [ ] Add acknowledgement.
- [ ] Generate final table of contents.
- [ ] Insert final ER diagram image.
- [ ] Insert final architecture diagram.
- [ ] Insert selected application screenshots.
- [ ] Insert database screenshots if required.
- [ ] Add GitHub repository link.
- [ ] Review formatting and page numbers.
- [ ] Export final report to PDF.
