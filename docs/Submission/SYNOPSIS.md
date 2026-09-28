# MedSphere – Unified Hospital Information Management System
## MCA Semester 3 Mini Project Synopsis

### Introduction
MedSphere is a Semester 3 MCA mini project providing a centralized web-based system for core hospital information and workflows. The current version covers patients, departments, doctors, appointments, consultations, prescriptions and medical history.

### Problem Statement
Connected hospital workflows require consistent handling of patient, doctor, appointment and clinical information. MedSphere provides an academic centralized system for these core workflows through a role-based database-backed web application.

### Objectives
- Centralize core hospital information.
- Provide role-based access for ADMIN, RECEPTIONIST and DOCTOR.
- Manage patients, departments and doctors.
- Schedule and manage appointments.
- Record consultations and prescriptions.
- Maintain patient medical history.
- Apply validation and business rules.
- Demonstrate secure authentication and authorization.
- Demonstrate MVC, layered architecture, JPA/Hibernate and relational database concepts.

### Scope
The Semester 3 version covers authentication, authorization, patient management, department management, doctor management, appointment management, consultation, prescription and medical history. Billing, pharmacy inventory, laboratory management, wards/beds, advanced notifications, analytics and expanded multi-building/multi-branch functionality are future scope.

### Technology
Java 21; Spring Boot 3.5.5; Spring MVC; Spring Security; Spring Data JPA; Hibernate; Thymeleaf; Bootstrap 5.3.3; JavaScript; MariaDB/MySQL; Maven Wrapper; XAMPP.

### Architecture
Browser/UI → Controller → Service → Repository → JPA/Hibernate → MariaDB/MySQL.

### Database
Core entities: Role, User, Department, Doctor, Patient, Appointment, Consultation, Prescription and MedicalHistory. Major relationships are Role–User, Department–Doctor, User–Doctor, Patient–Appointment, Doctor–Appointment, Appointment–Consultation, Consultation–Prescription and Patient–MedicalHistory.

### SDLC – Planning
The project was planned around a manageable Semester 3 hospital-information scope, with core workflows and three application roles identified before implementation.

### SDLC – Requirement Analysis
Functional requirements include login/logout, role authorization, patient registration/search/profile/edit, department and doctor management, appointment scheduling/status, consultation, prescription and medical history. Non-functional requirements include security, persistence, validation, responsive UI and maintainable structure.

### SDLC – System Design
The system uses layered MVC architecture. Controllers handle requests, services apply business logic, repositories provide database access, entities represent persistent data, and Thymeleaf renders views. Spring Security protects role-specific routes.

### SDLC – Implementation
The application uses Java 21 and Spring Boot 3.5.5. JPA/Hibernate handles persistence; Thymeleaf and Bootstrap provide the UI. Security uses database-backed authentication, BCrypt password encoding and CSRF protection.

### SDLC – Testing
Manual testing covered authentication, authorization, patient workflows, department management, doctor creation, duplicate username validation, appointment creation and conflict protection, consultation, prescriptions, medical history, validation and persistence.

### SDLC – Deployment / Operation
The project is designed for local execution using Java 21, XAMPP MariaDB/MySQL on port 3307 and Spring Boot on port 8080. Maven Wrapper is used to run/build the application.

### SDLC – Maintenance / Future Enhancement
The architecture can be expanded in the Semester 4 major version with billing, pharmacy inventory, laboratory management, wards/beds, notifications, analytics, audit logging and broader hospital/multi-building workflows.

### Expected Outcome
A working academic hospital information management application demonstrating secure role-based access and end-to-end database-backed workflows.

### Conclusion
MedSphere demonstrates how Java and Spring Boot technologies can be combined to implement a structured hospital information management application within Semester 3 scope.
