# MedSphere – Semester 3 Final Project Report Source

## 1. Introduction
MedSphere is a Java/Spring Boot hospital information management application developed as an MCA Semester 3 mini project.

## 2. Problem Statement
Hospital information workflows connect patients, doctors, departments, appointments and clinical records. MedSphere provides a centralized academic system for these core workflows.

## 3. Objectives
Centralized patient information; role-based access; department and doctor management; appointment scheduling; consultation and prescription recording; medical history; validation; persistence; secure authentication.

## 4. Proposed System
The system uses ADMIN, RECEPTIONIST and DOCTOR roles and connects administrative, reception and clinical workflows through a shared relational database.

## 5. Functional Modules
1. Authentication
2. Patient Management
3. Department Management
4. Doctor Management
5. Appointment Management
6. Doctor Consultation
7. Prescription Management
8. Medical History
9. Dashboard and responsive UI

## 6. Technology
Java 21, Spring Boot 3.5.5, Spring MVC, Spring Security, Spring Data JPA, Hibernate, Thymeleaf, Bootstrap 5.3.3, JavaScript, MariaDB/MySQL, Maven Wrapper and XAMPP.

## 7. Architecture
Browser/UI → Controller → Service → Repository → JPA/Hibernate → MariaDB/MySQL.

## 8. Security
Database-backed authentication, BCrypt password hashing, CSRF protection and role-based authorization. Protected route groups include /admin/**, /reception/** and /doctors/** according to role.

## 9. Database
Main entities are Role, User, Department, Doctor, Patient, Appointment, Consultation, Prescription and MedicalHistory. Patient codes use MSP-YYYY-000001.

## 10. Core Workflows
Patient: Register → generate code → store → search/view/edit → medical history.
Appointment: select patient/doctor/date/time → validate conflict → create SCHEDULED appointment → controlled status workflow.
Consultation: assigned doctor opens scheduled appointment → records symptoms/diagnosis/notes → saves → appointment becomes COMPLETED.
Prescription: consultation → medicine/dosage/frequency/duration/instructions → save.
Doctor creation: Admin enters account/professional data → username check → DOCTOR role → BCrypt password → User → linked Doctor profile.

## 11. Validation and Business Rules
Required fields, email validation, password length, unique usernames, doctor ownership, appointment conflict protection, appointment state locking and consultation eligibility are enforced.

## 12. Testing
Manual functional testing covered authentication, authorization, CRUD workflows, validation, appointment conflicts, consultation/prescription flow and persistence after restart.

## 13. Results
The implemented system provides the documented Semester 3 workflows through a responsive role-based web application.

## 14. Limitations
The current version does not implement billing, pharmacy inventory, laboratory management, wards/beds, advanced notifications, analytics or a complete commercial hospital ERP.

## 15. Future Scope
The planned Semester 4 major version can expand the system with broader EMR, laboratory/radiology, pharmacy, billing, admission/discharge, bed management, notifications, analytics, backup/recovery and multi-building/multi-branch architecture.

## 16. Conclusion
MedSphere demonstrates an end-to-end Java/Spring Boot hospital information workflow with secure role-based access, relational persistence and layered architecture.

## 17. Final Report Assembly
Cover page; Certificate; Declaration; Acknowledgement; Abstract; Table of Contents; List of Figures/Tables; Introduction; Problem Statement; Objectives; Scope; Requirement Analysis; SDLC; Architecture; DFD; UML; ER/database design; Module descriptions; Implementation; Screenshots; Testing; Results; Limitations; Future Scope; Conclusion; References; Appendix.
