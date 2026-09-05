# MedSphere – Viva Preparation

## Purpose

This document is the master viva-preparation guide for the MCA Semester 3 MedSphere mini project.

The answers are based on the current implementation and should be explained naturally rather than memorized word-for-word.

---

# 1. Project Introduction

### Q1. What is your project?

**Answer:**

My project is **MedSphere – Unified Hospital Information Management System**. It is a web-based hospital information management application developed as an MCA Semester 3 mini project. It manages patients, departments, doctors, appointments, consultations, prescriptions, and medical history using a role-based system.

### Q2. What is the main objective?

**Answer:**

The main objective is to centralize basic hospital information and workflows in one database-backed application while demonstrating Java, Spring Boot, Spring Security, JPA/Hibernate, MVC, validation, and relational database concepts.

### Q3. Why did you choose this project?

**Answer:**

A hospital has multiple connected workflows such as patient registration, appointment scheduling, consultation, prescriptions, and medical history. This makes it a good domain for demonstrating database relationships, role-based access, CRUD operations, and a complete end-to-end workflow.

---

# 2. Technology Questions

### Q4. Why did you use Java?

**Answer:**

Java provides object-oriented programming, a mature ecosystem, strong database support, and the Spring framework. It is also suitable for developing structured and maintainable web applications.

### Q5. Why Spring Boot?

**Answer:**

Spring Boot simplifies Spring application development by providing auto-configuration, embedded server support, dependency management, and an easy way to create production-style web applications. It allowed me to focus on the application's business logic instead of configuring everything manually.

### Q6. What is Spring MVC?

**Answer:**

Spring MVC is the web framework used to handle HTTP requests and responses. In my project, Controllers receive requests, use Services for business logic, prepare Model data, and return Thymeleaf views.

### Q7. What is Thymeleaf?

**Answer:**

Thymeleaf is a server-side template engine. It allows the Spring Boot application to dynamically render HTML pages using model data. For example, patient and doctor records can be displayed in HTML tables using Thymeleaf expressions.

### Q8. Why Bootstrap?

**Answer:**

Bootstrap provides responsive UI components and grid utilities. I used Bootstrap 5.3.3 along with a custom `app.css` stylesheet to maintain a consistent responsive MedSphere interface.

---

# 3. Architecture Questions

### Q9. Explain your project architecture.

**Answer:**

The project follows a simple layered MVC architecture:

```text
Browser / Thymeleaf UI
        ↓
Controller
        ↓
Service
        ↓
Repository
        ↓
JPA/Hibernate
        ↓
MariaDB/MySQL
```

Controllers handle web requests, Services contain business logic, Repositories handle database access, and Entities represent persistent data.

### Q10. Why separate Controller and Service?

**Answer:**

The separation keeps responsibilities clear. Controllers handle web requests and responses, while Services contain business rules. This makes the application easier to understand, maintain, test, and extend.

### Q11. Why use a Repository layer?

**Answer:**

The Repository layer isolates database operations from business logic. Spring Data JPA provides CRUD operations and derived queries without requiring every query to be written manually.

### Q12. What is dependency injection?

**Answer:**

Dependency injection means that required objects are provided to a class instead of the class creating them itself. In this project, Spring injects repositories and services through constructors.

---

# 4. Database / JPA Questions

### Q13. Which database did you use?

**Answer:**

I used MariaDB/MySQL. For local development it runs through XAMPP on port 3307, while the Spring Boot application runs on port 8080.

### Q14. What is JPA?

**Answer:**

JPA, or Java Persistence API, is a standard for mapping Java objects to relational database tables. In my project, JPA annotations are used on entities and Spring Data JPA provides repository support.

### Q15. What is Hibernate?

**Answer:**

Hibernate is the ORM implementation used by Spring Data JPA in this project. It maps Java entities to database tables and handles persistence operations.

### Q16. What is ORM?

**Answer:**

ORM stands for Object-Relational Mapping. It maps objects in an object-oriented programming language to records and relationships in a relational database.

### Q17. What is `@Entity`?

**Answer:**

`@Entity` tells JPA that a Java class represents a persistent database entity.

### Q18. What is `@Id`?

**Answer:**

`@Id` identifies the primary key field of an entity.

### Q19. What is `@GeneratedValue`?

**Answer:**

It tells JPA to generate the primary key value according to the configured generation strategy.

### Q20. Explain your main relationships.

**Answer:**

The main relationships are:

```text
Role 1 ── * User
Department 1 ── * Doctor
User 1 ── 0..1 Doctor
Patient 1 ── * Appointment * ── 1 Doctor
Appointment 1 ── 0..1 Consultation
Consultation 1 ── * Prescription
Patient 1 ── * MedicalHistory
```

A patient can have multiple appointments and medical history records. A doctor can have multiple appointments. An appointment can have one consultation, and a consultation can have multiple prescriptions.

### Q21. Why is User-to-Doctor one-to-zero-or-one?

**Answer:**

A User account may not be a doctor, so it can have zero Doctor profiles. If the user is used as a Doctor account, it can be linked to one Doctor profile. The doctor user reference is unique to prevent multiple profiles for the same account.

---

# 5. Spring Security Questions

### Q22. How does login work?

**Answer:**

The user submits a username and password through the login page. Spring Security uses the database-backed User details service to load the account and checks the password using the configured BCrypt password encoder.

### Q23. Why BCrypt?

**Answer:**

BCrypt is a password hashing algorithm designed for securely storing passwords. The application stores encoded passwords instead of plain-text passwords.

### Q24. What is role-based authorization?

**Answer:**

Role-based authorization controls which application functions a user can access based on their role. MedSphere has ADMIN, RECEPTIONIST, and DOCTOR roles.

### Q25. What are your security rules?

**Answer:**

```text
/admin/**      → ADMIN
/reception/**  → ADMIN, RECEPTIONIST
/doctors/**    → ADMIN, DOCTOR
Other routes   → Authenticated users
```

### Q26. Can a receptionist access the Admin doctor page by typing its URL?

**Answer:**

No. The `/admin/**` URL area is protected by Spring Security and requires the ADMIN role. Typing the URL manually does not bypass authorization.

### Q27. What is CSRF?

**Answer:**

CSRF stands for Cross-Site Request Forgery. It is an attack where an attacker attempts to make a user's browser perform an unwanted action. Spring Security provides CSRF protection, and the application's POST forms include the CSRF token.

---

# 6. Doctor Creation Questions

### Q28. How is a new doctor created?

**Answer:**

Only an Admin can create a doctor. The Admin enters username, password, full name, email, department, specialization, and phone. The service checks username uniqueness, finds the DOCTOR role, BCrypt-encodes the password, creates the User account, and then creates the linked Doctor profile.

### Q29. Why did you change the old doctor creation flow?

**Answer:**

The original flow required an existing DOCTOR user account before a doctor profile could be created. That created a usability gap because there was no normal Admin workflow for creating that account. I changed it so the Admin can create both the User account and Doctor profile in one transaction/workflow.

### Q30. What happens if the username already exists?

**Answer:**

The service checks `existsByUsername()` before creating the User. If the username already exists, an error is returned and the duplicate doctor account is not created.

---

# 7. Patient Questions

### Q31. How is the patient code generated?

**Answer:**

The application generates patient codes using the current year and a six-digit sequence, for example:

```text
MSP-2026-000001
```

The service finds the latest code for the current prefix and generates the next number. Registration date is also generated by the application.

### Q32. Can a patient change their patient code?

**Answer:**

No. The patient code is intentionally immutable during editing. The edit form copies normal patient information but does not overwrite the generated patient code or registration date.

---

# 8. Appointment Questions

### Q33. What are the appointment statuses?

**Answer:**

The statuses are:

```text
SCHEDULED
COMPLETED
CANCELLED
```

### Q34. What happens when an appointment is created?

**Answer:**

A new appointment starts with the `SCHEDULED` status.

### Q35. How do you prevent double booking?

**Answer:**

The appointment service checks whether the selected doctor already has an appointment at the same date and time before saving the new appointment. If a conflict exists, creation is rejected.

### Q36. Why can't a completed appointment be changed?

**Answer:**

The application treats completed and cancelled appointments as locked states. Only scheduled appointments are allowed through the controlled status-update workflow.

---

# 9. Consultation Questions

### Q37. How does a doctor start a consultation?

**Answer:**

The doctor opens their assigned appointment and can start a consultation only when the appointment is eligible, such as being scheduled. The consultation form records symptoms, diagnosis, notes, and consultation date.

### Q38. What happens after saving a consultation?

**Answer:**

The consultation is saved and the associated appointment is changed to `COMPLETED`.

### Q39. Can a doctor access another doctor's appointment?

**Answer:**

Doctor-side workflows perform ownership checks so the logged-in doctor can access only their assigned appointments.

---

# 10. Prescription Questions

### Q40. What is the relationship between Consultation and Prescription?

**Answer:**

One Consultation can have multiple Prescription records. Each prescription belongs to one consultation.

### Q41. What information does a prescription store?

**Answer:**

It stores medicine name, dosage, frequency, duration, and optional instructions.

### Q42. Why did you not implement pharmacy inventory?

**Answer:**

Pharmacy inventory is outside the scope of the Semester 3 mini version. The current project focuses on the basic prescription workflow. Pharmacy can be added as part of the future major version.

---

# 11. Medical History Questions

### Q43. What does Medical History store?

**Answer:**

The current entity stores the patient reference, condition name, details, and record date.

### Q44. Can one patient have multiple medical history records?

**Answer:**

Yes. The relationship is one-to-many: one patient can have multiple medical history records.

### Q45. How are records displayed?

**Answer:**

They are retrieved in descending record-date order, so the newest medical history appears first.

---

# 12. Validation Questions

### Q46. Why use DTO/Form classes?

**Answer:**

Form DTOs separate web form input from persistent entities. They also provide a convenient place for validation constraints such as `@NotBlank`, `@Size`, `@Email`, and `@NotNull`.

### Q47. What happens if validation fails?

**Answer:**

The controller checks `BindingResult`. If validation errors exist, the same form is returned with validation messages instead of calling the service to save invalid data.

### Q48. Give an example of validation you tested.

**Answer:**

I tested blank required fields, short doctor passwords, invalid email input, missing department, missing specialization, missing phone, and duplicate doctor usernames.

---

# 13. Transaction Questions

### Q49. Why use `@Transactional`?

**Answer:**

`@Transactional` groups related database operations into a transaction. For example, creating a new doctor involves creating a User and then a Doctor profile. Keeping the operation transactional helps prevent a partially completed creation if a runtime failure occurs.

### Q50. Where is business logic placed?

**Answer:**

Business logic is placed mainly in the Service layer rather than directly in the Controller. For example, doctor creation checks username uniqueness, loads the DOCTOR role, creates the User, and creates the Doctor profile in the DoctorService implementation.

---

# 14. Error Handling Questions

### Q51. What happens if a requested record does not exist?

**Answer:**

The service layer uses the application's resource-not-found exceptions where appropriate. The application also has a common error page for displaying a user-friendly error state instead of exposing internal details.

### Q52. Why should internal exceptions not be shown directly to users?

**Answer:**

Internal exception messages can be confusing and may expose implementation details. A user-friendly error page provides a safer and clearer experience.

---

# 15. Testing Questions

### Q53. What testing did you perform?

**Answer:**

I manually tested authentication, role authorization, patient workflows, department management, doctor management, doctor account creation, duplicate username validation, appointment creation and status handling, consultation, prescriptions, medical history, form validation, and database persistence after application restart.

### Q54. How did you test database persistence?

**Answer:**

I stopped the Spring Boot application, started it again, logged in, and verified that previously created records were still available. This confirmed that the data was persisted in MariaDB/MySQL.

### Q55. Did you test unauthorized access?

**Answer:**

Yes. I logged in using users with different roles and attempted to access protected URL areas belonging to other roles. Spring Security blocked unauthorized access.

---

# 16. Project Scope Questions

### Q56. Why doesn't your project have billing?

**Answer:**

Billing is outside the scope of the Semester 3 mini version. The project focuses on the core information workflow. Billing and other advanced modules can be added in a future major version.

### Q57. What can you add in the future?

**Answer:**

Possible future modules include billing, pharmacy inventory, laboratory management, ward and bed management, notifications, patient portal, doctor availability schedules, prescription PDF generation, audit logging, and advanced analytics.

---

# 17. Rapid-Fire Questions

### Q58. What port does your application use?

**Answer:** `8080`.

### Q59. What port does your local database use?

**Answer:** `3307`.

### Q60. What is the database name?

**Answer:** `medsphere`.

### Q61. What build tool do you use?

**Answer:** Maven Wrapper.

### Q62. What Java version?

**Answer:** Java 21.

### Q63. What template engine?

**Answer:** Thymeleaf.

### Q64. What ORM?

**Answer:** Hibernate through Spring Data JPA.

### Q65. What password encoder?

**Answer:** BCryptPasswordEncoder.

### Q66. What are your roles?

**Answer:** ADMIN, RECEPTIONIST, and DOCTOR.

### Q67. What is the default appointment status?

**Answer:** SCHEDULED.

### Q68. What happens when consultation is saved?

**Answer:** The appointment becomes COMPLETED.

### Q69. Can one consultation have multiple prescriptions?

**Answer:** Yes.

### Q70. Can one patient have multiple appointments?

**Answer:** Yes.

### Q71. Can one doctor have multiple appointments?

**Answer:** Yes.

### Q72. Can one user have multiple doctor profiles?

**Answer:** No. The doctor-user relationship is unique.

---

# 18. Demo / Viva Flow

If asked to demonstrate the project, use this sequence:

```text
1. Login as Admin
       ↓
2. Show Admin Dashboard
       ↓
3. Show Departments
       ↓
4. Create / show Doctor
       ↓
5. Show Doctor Details
       ↓
6. Logout
       ↓
7. Login as Receptionist
       ↓
8. Register / show Patient
       ↓
9. Create Appointment
       ↓
10. Logout
       ↓
11. Login as Doctor
       ↓
12. Show My Appointments
       ↓
13. Open Appointment
       ↓
14. Start Consultation
       ↓
15. Save Consultation
       ↓
16. Add Prescription
       ↓
17. Show Consultation + Prescription
       ↓
18. Return to Patient Profile
       ↓
19. Show Medical History
```

This demonstrates the connected end-to-end workflow.

---

# 19. Questions You Should Be Ready to Answer on Your Own Code

Before the final viva, be able to locate and explain:

- `SecurityConfig`
- `DataInitializer`
- `CustomUserDetailsService`
- `HomeController`
- `PatientController`
- `DoctorController`
- `AppointmentController`
- `DoctorConsultationController`
- `DoctorPrescriptionController`
- `MedicalHistoryController`
- `PatientServiceImpl`
- `DoctorServiceImpl`
- `AppointmentServiceImpl`
- `ConsultationServiceImpl`
- `PrescriptionServiceImpl`
- `MedicalHistoryServiceImpl`
- Entity classes
- Repository interfaces
- Form/DTO classes
- `application.properties`
- `app.css`

---

# 20. Viva Answer Strategy

When answering a technical question, use this pattern:

**What → Why → How → Example**

Example:

> **What is JPA?** It is a Java persistence standard for mapping objects to relational databases. **Why did I use it?** To avoid manually handling every database operation. **How?** I use JPA entities and Spring Data repositories. **Example:** Patient is an entity and PatientRepository provides database operations.

Do not claim features that are not implemented. If a feature is future scope, say so clearly.

---

# 21. Final Viva Checklist

Before the viva, make sure you can explain without reading:

- [ ] Project objective
- [ ] Problem statement
- [ ] Three user roles
- [ ] Complete architecture
- [ ] Controller → Service → Repository flow
- [ ] JPA and Hibernate
- [ ] Entity relationships
- [ ] Primary key and foreign key
- [ ] Spring Security
- [ ] BCrypt
- [ ] CSRF
- [ ] Doctor account creation
- [ ] Patient code generation
- [ ] Appointment conflict rule
- [ ] Appointment status workflow
- [ ] Consultation workflow
- [ ] Prescription relationship
- [ ] Medical history
- [ ] Validation
- [ ] `@Transactional`
- [ ] Database configuration
- [ ] Testing performed
- [ ] Limitations
- [ ] Future scope
