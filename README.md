# MedSphere – Unified Hospital Information Management System

MedSphere is a **Semester 3 MCA mini project** developed as a beginner-friendly hospital information management system. The application demonstrates how a hospital can manage users, departments, doctors, patients, appointments, consultations, prescriptions, and medical history through a role-based web application.

The project uses a simple layered architecture with **Spring Boot, Spring Security, Spring Data JPA/Hibernate, Thymeleaf, Bootstrap, and MariaDB/MySQL**.

---

## 1. Project Overview

### Project Name

**MedSphere – Unified Hospital Information Management System**

### Project Type

Semester 3 MCA Mini Project

### Main Objective

The objective of MedSphere is to provide a centralized system for basic hospital information management while demonstrating important Java and Spring Boot concepts such as:

- Spring Boot web application development
- MVC architecture
- Layered architecture
- Spring Security and role-based authorization
- Database-backed authentication
- JPA/Hibernate entity relationships
- Server-side validation
- Thymeleaf server-side rendering
- CRUD operations
- Appointment workflow management
- Doctor consultation management
- Prescription management
- Patient medical history management

### Current Scope

This version is the **Semester 3 mini version**. It intentionally focuses on the core hospital information workflows required for the academic project and does not attempt to implement a complete commercial hospital ERP.

---

## 2. Technology Stack

| Technology | Usage |
| --- | --- |
| Java 21 | Application programming language |
| Spring Boot 3.5.5 | Backend application framework |
| Spring MVC | Web request and controller handling |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access and repository layer |
| Hibernate | JPA implementation / ORM |
| Thymeleaf | Server-side HTML rendering |
| Bootstrap 5.3.3 | Responsive user interface |
| JavaScript | Client-side web support |
| MariaDB/MySQL | Relational database |
| Maven Wrapper | Build and dependency management |
| XAMPP | Local MariaDB database environment |

The project's Maven configuration defines Spring Web, Thymeleaf, Security, Data JPA, Validation, MySQL Connector/J, and Spring testing dependencies. 

---

## 3. Application Roles

MedSphere currently has three application roles.

### ADMIN

The administrator manages the hospital's master information and doctor accounts.

Main capabilities:

- Access the Admin dashboard
- Manage departments
- Create doctor accounts and doctor profiles
- Edit doctor profiles
- View doctors
- Manage patients
- Manage appointments
- Access reception workflows

### RECEPTIONIST

The receptionist handles front-desk patient and appointment operations.

Main capabilities:

- Access the Reception dashboard/workflows
- Register patients
- Search patients
- View patient profiles
- Edit patient information
- Add medical history
- Create appointments
- Search and filter appointments
- View appointment details
- Update appointment status where permitted

### DOCTOR

The doctor works with assigned appointments and clinical records.

Main capabilities:

- Access the Doctor workspace
- View assigned appointments
- Open appointment details
- Start consultations for scheduled appointments
- Save consultation records
- View completed consultations
- Add prescriptions to consultations
- View prescriptions attached to consultations

---

## 4. Authorization Rules

The application uses Spring Security with database-backed users and BCrypt password encoding.

| URL area | Allowed roles |
| --- | --- |
| `/admin/**` | ADMIN |
| `/reception/**` | ADMIN, RECEPTIONIST |
| `/doctors/**` | ADMIN, DOCTOR |
| Other application routes | Authenticated users |

This means that a user cannot simply type another role's URL into the browser and gain access to the protected functionality.

---

## 5. Main Modules

### 5.1 Authentication

The application provides:

- Login page
- Database-backed username/password authentication
- BCrypt password hashing
- Role-based authorization
- CSRF protection
- Logout support
- Development seed accounts

---

### 5.2 Patient Management

Patient management is available under `/reception/patients`.

Features:

- Patient registration
- Generated patient registration code
- Patient list
- Patient search
- Patient profile/details
- Patient editing
- Blood group and contact information
- Immutable patient code and registration date
- Medical history section on the patient profile

Patient registration codes use the format:

```text
MSP-YYYY-000001
```

---

### 5.3 Department Management

Departments are managed by administrators.

Features:

- Department list
- Add department
- Edit department
- Department validation
- Department assignment to doctors

---

### 5.4 Doctor Management

Doctor management is an Admin-only feature.

The current implementation supports creating a new doctor account and profile in one workflow.

Flow:

```text
ADMIN
  |
  v
Add Doctor
  |
  +--> Account Information
  |      - Username
  |      - Password
  |      - Full Name
  |      - Email
  |
  +--> Professional Information
         - Department
         - Specialization
         - Phone
  |
  v
Save
  |
  +--> ROLE_DOCTOR User Account
  |
  +--> Doctor Profile
```

When the administrator creates a doctor:

1. The username is checked for duplicates.
2. The DOCTOR role is assigned.
3. The password is BCrypt encoded.
4. The User record is created.
5. The Doctor profile is created and linked to the User.
6. The Doctor is assigned to the selected Department.
7. The new doctor can log in using the created credentials.

---

### 5.5 Appointment Management

Appointment management is available to Admin and Receptionist users under `/reception/appointments`.

Features:

- Appointment creation
- Patient selection
- Doctor selection
- Appointment date and time
- Reason for visit
- Appointment search
- Status filtering
- Appointment details
- Status updates
- Doctor/date/time conflict protection

Appointment statuses are:

```text
SCHEDULED
COMPLETED
CANCELLED
```

New appointments start as `SCHEDULED`.

Only scheduled appointments can have their status changed through the controlled status workflow. Completed and cancelled appointments are treated as locked states.

---

### 5.6 Doctor Consultation

Doctors can manage consultations for their own assigned appointments.

Flow:

```text
Doctor Login
    |
    v
My Appointments
    |
    v
Appointment Details
    |
    v
Start Consultation
    |
    v
Consultation Form
    |
    v
Save Consultation
    |
    +--> Consultation Record
    |
    +--> Appointment becomes COMPLETED
    |
    v
Consultation Details
```

Consultation information includes:

- Symptoms
- Diagnosis
- Notes
- Consultation date

Doctor ownership checks are used so a doctor can work only with their assigned appointments.

---

### 5.7 Prescription Management

Prescription management is connected to consultations.

Flow:

```text
Consultation Details
    |
    v
Add Prescription
    |
    v
Medicine
Dosage
Frequency
Duration
Instructions
    |
    v
Save
    |
    v
Prescription List
```

A prescription contains:

- Medicine name
- Dosage
- Frequency
- Duration
- Optional instructions

The current Semester 3 version intentionally does not include pharmacy inventory, medicine master management, billing, or advanced prescription processing.

---

### 5.8 Medical History

Medical history is attached directly to a patient profile.

Flow:

```text
Patient Profile
    |
    v
Medical History
    |
    v
Add Medical History
    |
    +--> Condition / Diagnosis
    +--> Details
    +--> Record Date
    |
    v
Save
    |
    v
Patient Profile
    |
    v
Medical History List
```

Medical history records are displayed newest-first.

The current implementation uses the existing entity fields only:

- Patient
- Condition name
- Details
- Record date

---

## 6. Entity Relationships

The main JPA relationships are:

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

### Relationship Explanation

- One Role can be assigned to many Users.
- One Department can contain many Doctors.
- A User account can be linked to zero or one Doctor profile.
- A Patient can have many Appointments.
- A Doctor can have many Appointments.
- An Appointment can have zero or one Consultation.
- A Consultation can have multiple Prescriptions.
- A Patient can have multiple Medical History records.

---

## 7. Architecture

MedSphere follows a simple layered MVC architecture designed to remain understandable for a Semester 3 academic project.

```text
+-------------------------------+
|       Browser / UI             |
| Thymeleaf + Bootstrap + JS     |
+---------------+---------------+
                |
                v
+-------------------------------+
|        Controller Layer        |
| Spring MVC Controllers         |
+---------------+---------------+
                |
                v
+-------------------------------+
|          Service Layer        |
| Business Logic                |
+---------------+---------------+
                |
                v
+-------------------------------+
|        Repository Layer        |
| Spring Data JPA Repositories   |
+---------------+---------------+
                |
                v
+-------------------------------+
|        MariaDB / MySQL         |
+-------------------------------+
```

### Layer Responsibilities

**Controller**

- Receives HTTP requests
- Validates form submissions
- Loads model data
- Selects Thymeleaf views
- Handles redirects

**Service**

- Contains business logic
- Performs entity lookups
- Applies application rules
- Coordinates repository operations

**Repository**

- Provides database access through Spring Data JPA
- Performs entity persistence and queries

**Entity / Model**

- Represents database tables and relationships

**DTO / Form Objects**

- Carry form data between the web layer and service layer
- Provide validation constraints where required

---

## 8. Project Structure

The important project structure is:

```text
MedSphere-Sem3/
|
+-- src/
|   +-- main/
|       +-- java/com/medsphere/
|       |   +-- config/
|       |   +-- controller/
|       |   +-- dto/
|       |   +-- entity/
|       |   +-- enums/
|       |   +-- exception/
|       |   +-- repository/
|       |   +-- service/
|       |       +-- impl/
|       |
|       +-- resources/
|           +-- templates/
|           |   +-- doctors/
|           |   +-- patients/
|           |   +-- appointments/
|           |   +-- departments/
|           |   +-- home.html
|           |   +-- login.html
|           |   +-- error.html
|           |
|           +-- static/css/
|           |   +-- app.css
|           |
|           +-- application.properties
|
+-- .mvn/
+-- mvnw
+-- mvnw.cmd
+-- pom.xml
+-- PROJECT_CONTEXT.md
+-- README.md
```

---

## 9. Database Configuration

The default local database configuration is:

| Setting | Value |
| --- | --- |
| Database | `medsphere` |
| Host | `localhost` |
| Port | `3307` |
| Server | XAMPP MariaDB |
| Application port | `8080` |
| Hibernate schema mode | `update` |

The default datasource URL is configured in `application.properties` and can be overridden through environment variables.

### Default Configuration

```properties
spring.datasource.url=${MEDSPHERE_DB_URL:jdbc:mysql://localhost:3307/medsphere?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Kolkata}
spring.datasource.username=${MEDSPHERE_DB_USERNAME:root}
spring.datasource.password=${MEDSPHERE_DB_PASSWORD:}
```

For local development, XAMPP MariaDB should be running on port `3307`.

---

## 10. Running the Project Locally

### Prerequisites

Install or have available:

- Java 21
- XAMPP with MariaDB/MySQL
- Git
- A modern web browser

Maven does not need to be installed globally because the project includes the Maven Wrapper.

### Step 1 – Start Database

Start **XAMPP MariaDB/MySQL** on port `3307`.

### Step 2 – Keep XAMPP Tomcat Off

XAMPP Tomcat should remain **OFF** because Spring Boot's embedded Tomcat uses port `8080`.

### Step 3 – Start MedSphere

Open a terminal in the project root and run:

```powershell
.\mvnw.cmd spring-boot:run
```

If Java needs to be selected explicitly:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
.\mvnw.cmd spring-boot:run
```

### Step 4 – Open the Application

Open:

```text
http://localhost:8080/login
```

---

## 11. Development Login Accounts

The application initializes the following development accounts if they do not already exist:

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `admin123` | ADMIN |
| `receptionist` | `reception123` | RECEPTIONIST |
| `doctor` | `doctor123` | DOCTOR |

These are **development/demo credentials only** and should not be used as production credentials.

Administrators can also create additional doctor accounts through the Add Doctor workflow.

---

## 12. Important Routes

### General

| Purpose | Route |
| --- | --- |
| Login | `/login` |
| Dashboard | `/` |

### Admin

| Purpose | Route |
| --- | --- |
| Departments | `/admin/departments` |
| Add Department | `/admin/departments/new` |
| Doctors | `/admin/doctors` |
| Add Doctor | `/admin/doctors/new` |

### Reception

| Purpose | Route |
| --- | --- |
| Patients | `/reception/patients` |
| Appointments | `/reception/appointments` |
| Add Medical History | `/reception/patients/{patientId}/medical-history/new` |

### Doctor

| Purpose | Route |
| --- | --- |
| My Appointments | `/doctors/appointments` |
| Appointment Details | `/doctors/appointments/{id}` |
| Consultation | `/doctors/appointments/{id}/consultation` |
| Consultation Details | `/doctors/appointments/{id}/consultation/details` |
| Add Prescription | `/doctors/appointments/{id}/consultation/prescription` |

---

## 13. Validation and Business Rules

MedSphere uses server-side validation and service-level business rules.

Important rules include:

- Required form fields cannot be left blank.
- Email fields use email validation where applicable.
- Passwords for newly created doctor accounts must meet the configured minimum length.
- Doctor usernames must be unique.
- Only accounts with the DOCTOR role can be associated with a doctor profile through the existing edit flow.
- A doctor user cannot be linked to multiple doctor profiles.
- Appointment creation prevents doctor/date/time conflicts.
- New appointments start as `SCHEDULED`.
- Only scheduled appointments can transition through the appointment status update workflow.
- A consultation can be started only for a scheduled appointment.
- Saving a consultation changes the appointment status to `COMPLETED`.
- Doctors can access only their own assigned appointments and related consultation/prescription workflows.
- Medical history is linked to an existing patient.

---

## 14. User Interface

The application uses a common Bootstrap-based UI foundation.

The redesigned interface includes:

- MedSphere branded navigation bar
- Role-aware dashboard content
- Consistent page headers
- Responsive Bootstrap layouts
- Reusable content cards
- Consistent buttons and forms
- Styled data tables
- Empty-state sections
- Status indicators
- Patient and record badges
- Responsive behavior for smaller screens

The current application contains redesigned pages across:

- Patients
- Appointments
- Doctors
- Departments
- Login
- Dashboard
- Doctor consultation
- Prescription
- Medical history
- Error handling

---

## 15. Testing Performed

The project has been manually verified through the running Spring Boot application and MariaDB database.

### Authentication Testing

- Admin login tested.
- Receptionist login tested.
- Doctor login tested.
- Logout tested.
- Protected role-specific URL access tested.
- Unauthorized role access was blocked.

### Patient Testing

- Patient creation tested.
- Patient search tested.
- Patient profile tested.
- Patient edit tested.
- Medical history creation tested.
- Medical history display tested.

### Department Testing

- Department creation tested.
- Department editing tested.
- Department listing tested.

### Doctor Testing

- Existing doctor profile management tested.
- New doctor account creation tested.
- Doctor profile creation tested.
- Duplicate username validation tested.
- New doctor login tested.
- Doctor workspace access tested.

### Appointment Testing

- Appointment creation tested.
- Appointment listing tested.
- Search/filter functionality tested.
- Appointment details tested.
- Scheduled-to-completed workflow tested.
- Completed/cancelled status locking tested.
- Doctor/date/time conflict protection was implemented and tested during development.

### Consultation Testing

- Doctor appointment access tested.
- Consultation form tested.
- Consultation save tested.
- Appointment completion after consultation tested.
- Consultation details tested.

### Prescription Testing

- Prescription form tested.
- Prescription save tested.
- Prescription display under consultation tested.
- Database persistence tested.

### Database Persistence Testing

The application was stopped and restarted, after which previously created records remained available. This verified that application data is persisted in MariaDB rather than being held only in browser/application memory.

### Validation Testing

Invalid and incomplete doctor creation input was tested, including:

- Missing required fields
- Short password
- Invalid email
- Missing department
- Missing specialization
- Missing phone
- Duplicate username

Validation messages and duplicate-username protection worked as expected.

---

## 16. Project Development Phases

The project was developed incrementally.

### Phase 1 – Foundation

Created the Spring Boot foundation, Java 21 configuration, Maven Wrapper, Thymeleaf, Bootstrap, Security, database connectivity, login/logout foundation, and application port configuration.

### Phase 2 – Database Foundation

Created the main JPA entities, relationships, repositories, enums, and database model.

### Phase 3 – Database-Backed Authentication

Implemented database-backed users and roles, BCrypt password encoding, development data initialization, CSRF protection, and role-based URL authorization.

### Phase 4 – Patient Management

Implemented patient registration, patient codes, patient listing/search, patient details, editing, validation, and role-specific access.

### Phase 5 – Department & Doctor Management

Implemented department management, doctor management, department assignment, doctor-user linking, validation, and Admin-only access.

### Phase 6 – Appointment Management

Implemented appointment creation, listing, details, searching/filtering, status handling, validation, and doctor/date/time conflict protection.

### Phase 7 – Doctor Consultation

Implemented doctor appointments, ownership checks, consultation creation, consultation details, and automatic appointment completion after consultation.

### Phase 8 – Basic Prescription

Implemented prescriptions linked to consultations, prescription creation, validation, storage, and display on consultation details.

### Phase 9 – Medical History

Implemented patient medical history creation, storage, newest-first retrieval, and display on patient profiles.

### Phase 10 – Dashboard & UI Redesign

Redesigned the main dashboard and application interface with role-aware sections, common navigation, responsive cards, page headers, tables, forms, and reusable styling.

### Phase 11 – UI Consistency Pass

Reviewed the application pages and applied the common UI system consistently across the patient, appointment, doctor, department, consultation, prescription, medical history, login, home, and error pages.

### Phase 12 – Final Testing & Bug Fixing

Performed end-to-end manual testing covering authentication, role authorization, CRUD workflows, consultation/prescription workflows, validation, duplicate doctor usernames, status restrictions, and database persistence after application restart.

An important functionality gap found during testing was the old doctor creation flow, which required an existing DOCTOR user account. This was replaced with Admin-only creation of a new DOCTOR user account and Doctor profile in a single workflow.

### Phase 13 – Documentation

Prepared the project documentation structure, including this README and the permanent development context document.

---

## 17. Known Environment Notes

- XAMPP MariaDB/MySQL should run on port `3307`.
- XAMPP Tomcat should remain OFF while running Spring Boot on port `8080`.
- The project uses `spring.jpa.hibernate.ddl-auto=update` for the local development database.
- Hibernate may display a warning that explicitly specifying `MySQLDialect` is unnecessary; this does not currently prevent the application from running.
- Spring may display an `open-in-view` warning; this does not currently prevent the application from running.

---

## 18. Scope Not Included in the Semester 3 Mini Version

The following features are intentionally outside the current mini-project scope:

- Hospital billing and invoices
- Pharmacy inventory
- Medicine master/inventory management
- Laboratory management
- Lab reports
- Ward/bed management
- Advanced notifications
- Online patient registration
- Appointment payment gateway
- Advanced reporting/analytics
- Production deployment infrastructure
- Multi-hospital support

These can be considered for a future major version if required.

---

## 19. Future Scope

Possible future enhancements include:

- Billing and payment management
- Pharmacy inventory
- Laboratory and report management
- Ward and bed management
- Appointment reminders
- Email/SMS notifications
- Advanced dashboards and analytics
- Prescription printing/PDF generation
- Patient portal
- Doctor availability schedules
- Audit logging
- Production deployment
- More granular permissions

---

## 20. Academic Project Notes

MedSphere is intentionally implemented with a straightforward architecture so that the complete flow can be understood and explained during an academic demonstration or viva.

Important concepts demonstrated by the project include:

- Java classes and object-oriented programming
- Spring Boot application configuration
- MVC pattern
- Dependency injection
- Service and repository layers
- JPA entity relationships
- CRUD operations
- Form DTOs
- Validation annotations
- Database transactions
- Spring Security
- BCrypt password hashing
- Role-based authorization
- Thymeleaf model binding
- Bootstrap responsive UI
- Relational database persistence

---

## 21. GitHub Development Rules

The repository uses `main` as the primary branch and is maintained incrementally.

Development conventions:

- Preserve completed working features.
- Make changes in small, understandable phases.
- Avoid unnecessary rewrites of completed modules.
- Keep doctor-side routes under `/doctors/**`.
- Keep Admin routes under `/admin/**`.
- Keep Reception routes under `/reception/**`.
- Update `PROJECT_CONTEXT.md` after meaningful project phases.

---

## 22. Current Project Status

**Semester 3 Mini Version: Core implementation complete.**

Completed areas:

- Authentication and authorization
- Admin dashboard
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
- Responsive UI redesign
- UI consistency pass
- End-to-end manual testing
- Database persistence verification
- Project documentation foundation

The next work can focus on final academic submission materials such as screenshots, ER diagram, architecture diagram, project report, presentation, and viva preparation.

---

## 23. License / Academic Use

This project is developed as an academic Semester 3 MCA mini project. It is intended for educational and demonstration purposes and is not presented as a production-ready hospital information system.
