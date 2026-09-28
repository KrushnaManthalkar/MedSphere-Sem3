# MedSphere – User Manual

## Prerequisites
Java 21, XAMPP MariaDB/MySQL, browser, Git and project source.

## Start Database
Start XAMPP MariaDB/MySQL on port 3307. Keep XAMPP Tomcat off because Spring Boot uses port 8080.

## Start Application
From the project root run: .\mvnw.cmd spring-boot:run
Open http://localhost:8080/login

## Development Accounts
admin / admin123 — ADMIN
receptionist / reception123 — RECEPTIONIST
doctor / doctor123 — DOCTOR

These are development/demo credentials only.

## Admin
Login → Admin Dashboard → manage departments → create/view doctors → manage administrative records.

## Receptionist
Login → Reception → register/search patient → open profile → create appointment → manage permitted appointment status → add medical history.

## Doctor
Login → My Appointments → open assigned scheduled appointment → start consultation → enter symptoms/diagnosis/notes → save → add prescription.

## Patient
Register patient → system generates MSP-YYYY-000001 style code → search/view/edit profile → maintain medical history.

## Appointment
Create appointment with patient, doctor, date/time and reason. The system checks doctor/date/time conflicts. New appointments are SCHEDULED.

## Consultation
An assigned doctor can start an eligible consultation. Saving records symptoms, diagnosis, notes and date and changes the appointment to COMPLETED.

## Security
Protected URLs are enforced by Spring Security. Use the correct role account; manually typing a protected URL does not bypass authorization.

## Troubleshooting
Database error: verify MariaDB/MySQL is running on port 3307.
Port conflict: verify port 8080 is available and XAMPP Tomcat is off.
Login failure: verify database availability and account.
Build issue: verify Java 21 and use Maven Wrapper.

## Scope Note
Semester 3 does not include billing, pharmacy inventory, laboratory management, wards/beds or other advanced future-scope modules.
