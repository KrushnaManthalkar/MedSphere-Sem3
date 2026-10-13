-- MedSphere Demo Database Reset + Analytics Seed (FIXED)
-- This version uses DELETE instead of TRUNCATE for tables involved in
-- foreign-key relationships, so it works with MySQL/MariaDB constraints.
--
-- BACK UP the database first if you need the old data.

USE medsphere;

-- =========================================================
-- 1. Clear application records in CHILD -> PARENT order
-- =========================================================

DELETE FROM prescriptions;
DELETE FROM consultations;
DELETE FROM appointments;
DELETE FROM medical_history;
DELETE FROM doctors;
DELETE FROM patients;
DELETE FROM departments;

-- Reset auto-increment counters for clean demo IDs.
ALTER TABLE prescriptions AUTO_INCREMENT = 1;
ALTER TABLE consultations AUTO_INCREMENT = 1;
ALTER TABLE appointments AUTO_INCREMENT = 1;
ALTER TABLE medical_history AUTO_INCREMENT = 1;
ALTER TABLE doctors AUTO_INCREMENT = 1;
ALTER TABLE patients AUTO_INCREMENT = 1;
ALTER TABLE departments AUTO_INCREMENT = 1;

-- Keep the normal login accounts.
-- Remove demo doctor accounts only, if this script was run before.
DELETE FROM users
WHERE username IN ('demo.ortho','demo.pediatrics','demo.derma','demo.general');

-- =========================================================
-- 2. Departments
-- =========================================================

INSERT INTO departments (name, description) VALUES
('Cardiology','Heart and cardiovascular care'),
('General Medicine','General diagnosis and primary medical care'),
('Orthopedics','Bones, joints and musculoskeletal care'),
('Pediatrics','Medical care for children'),
('Dermatology','Skin, hair and nail care');

-- =========================================================
-- 3. Demo doctor accounts
-- Reuse the existing "doctor" account's BCrypt password.
-- =========================================================

INSERT INTO users (username,password,full_name,email,active,role_id)
SELECT 'demo.ortho', password, 'Dr. Rahul Patil',
       'rahul.patil@medsphere.local', TRUE, role_id
FROM users WHERE username='doctor';

INSERT INTO users (username,password,full_name,email,active,role_id)
SELECT 'demo.pediatrics', password, 'Dr. Sneha Kulkarni',
       'sneha.kulkarni@medsphere.local', TRUE, role_id
FROM users WHERE username='doctor';

INSERT INTO users (username,password,full_name,email,active,role_id)
SELECT 'demo.derma', password, 'Dr. Priya Deshmukh',
       'priya.deshmukh@medsphere.local', TRUE, role_id
FROM users WHERE username='doctor';

INSERT INTO users (username,password,full_name,email,active,role_id)
SELECT 'demo.general', password, 'Dr. Amit Joshi',
       'amit.joshi@medsphere.local', TRUE, role_id
FROM users WHERE username='doctor';

-- =========================================================
-- 4. Doctor profiles
-- =========================================================

INSERT INTO doctors (user_id,department_id,specialization,phone)
SELECT u.id,d.id,'Cardiology','9000000001'
FROM users u CROSS JOIN departments d
WHERE u.username='doctor' AND d.name='Cardiology';

INSERT INTO doctors (user_id,department_id,specialization,phone)
SELECT u.id,d.id,'Orthopedic Surgery','9000000002'
FROM users u CROSS JOIN departments d
WHERE u.username='demo.ortho' AND d.name='Orthopedics';

INSERT INTO doctors (user_id,department_id,specialization,phone)
SELECT u.id,d.id,'Pediatrics','9000000003'
FROM users u CROSS JOIN departments d
WHERE u.username='demo.pediatrics' AND d.name='Pediatrics';

INSERT INTO doctors (user_id,department_id,specialization,phone)
SELECT u.id,d.id,'Dermatology','9000000004'
FROM users u CROSS JOIN departments d
WHERE u.username='demo.derma' AND d.name='Dermatology';

INSERT INTO doctors (user_id,department_id,specialization,phone)
SELECT u.id,d.id,'General Medicine','9000000005'
FROM users u CROSS JOIN departments d
WHERE u.username='demo.general' AND d.name='General Medicine';

-- =========================================================
-- 5. Fictional demo patients
-- Registration dates are spread across 2026.
-- =========================================================

INSERT INTO patients
(patient_code,name,date_of_birth,gender,blood_group,phone,email,address,emergency_contact,registration_date)
VALUES
('MSP-2026-000001','Aarav Sharma','1998-04-12','Male','B+','9001000001','aarav.sharma@example.com','Pune, Maharashtra','9002000001','2026-01-08'),
('MSP-2026-000002','Ananya Patil','2001-09-21','Female','O+','9001000002','ananya.patil@example.com','Pune, Maharashtra','9002000002','2026-01-24'),
('MSP-2026-000003','Rohan Kulkarni','1989-02-17','Male','A+','9001000003','rohan.kulkarni@example.com','Pune, Maharashtra','9002000003','2026-02-11'),
('MSP-2026-000004','Isha Deshmukh','1995-07-03','Female','AB+','9001000004','isha.deshmukh@example.com','Pune, Maharashtra','9002000004','2026-03-05'),
('MSP-2026-000005','Vivaan Joshi','2014-11-19','Male','O+','9001000005','vivaan.joshi@example.com','Pune, Maharashtra','9002000005','2026-03-19'),
('MSP-2026-000006','Meera Pawar','1992-06-28','Female','B-','9001000006','meera.pawar@example.com','Pune, Maharashtra','9002000006','2026-03-27'),
('MSP-2026-000007','Aditya More','1986-12-09','Male','A-','9001000007','aditya.more@example.com','Pune, Maharashtra','9002000007','2026-04-14'),
('MSP-2026-000008','Kavya Jadhav','2000-01-30','Female','B+','9001000008','kavya.jadhav@example.com','Pune, Maharashtra','9002000008','2026-04-28'),
('MSP-2026-000009','Omkar Shinde','1978-08-16','Male','O-','9001000009','omkar.shinde@example.com','Pune, Maharashtra','9002000009','2026-05-06'),
('MSP-2026-000010','Neha Chavan','1997-03-22','Female','A+','9001000010','neha.chavan@example.com','Pune, Maharashtra','9002000010','2026-05-18'),
('MSP-2026-000011','Siddharth Bhosale','1990-10-05','Male','AB+','9001000011','siddharth.bhosale@example.com','Pune, Maharashtra','9002000011','2026-05-29'),
('MSP-2026-000012','Riya Sawant','1985-05-14','Female','O+','9001000012','riya.sawant@example.com','Pune, Maharashtra','9002000012','2026-06-12'),
('MSP-2026-000013','Atharva Gaikwad','1999-01-08','Male','B+','9001000013','atharva.gaikwad@example.com','Pune, Maharashtra','9002000013','2026-07-03'),
('MSP-2026-000014','Pooja Wagh','1993-09-11','Female','A+','9001000014','pooja.wagh@example.com','Pune, Maharashtra','9002000014','2026-07-21'),
('MSP-2026-000015','Yash Khot','2003-12-24','Male','O+','9001000015','yash.khot@example.com','Pune, Maharashtra','9002000015','2026-08-04'),
('MSP-2026-000016','Tanvi Salunkhe','1996-02-13','Female','B+','9001000016','tanvi.salunkhe@example.com','Pune, Maharashtra','9002000016','2026-08-17'),
('MSP-2026-000017','Arjun Thorat','1982-06-07','Male','A+','9001000017','arjun.thorat@example.com','Pune, Maharashtra','9002000017','2026-08-25'),
('MSP-2026-000018','Sakshi Mane','1998-11-02','Female','O+','9001000018','sakshi.mane@example.com','Pune, Maharashtra','9002000018','2026-09-06'),
('MSP-2026-000019','Manav Kendre','2005-04-20','Male','B+','9001000019','manav.kendre@example.com','Pune, Maharashtra','9002000019','2026-09-19'),
('MSP-2026-000020','Nandini Shukla','1991-08-31','Female','AB+','9001000020','nandini.shukla@example.com','Pune, Maharashtra','9002000020','2026-10-02');

-- =========================================================
-- 6. Demo appointments
-- =========================================================

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-01-10','10:00:00','COMPLETED','Routine cardiac check-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000001' AND u.username='doctor';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-01-26','11:00:00','COMPLETED','General health consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000002' AND u.username='demo.general';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-02-13','09:30:00','COMPLETED','Joint pain assessment'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000003' AND u.username='demo.ortho';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-03-07','10:30:00','COMPLETED','Skin consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000004' AND u.username='demo.derma';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-03-21','11:30:00','COMPLETED','Child health check'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000005' AND u.username='demo.pediatrics';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-03-29','14:00:00','COMPLETED','Cardiac screening'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000006' AND u.username='doctor';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-04-16','10:00:00','COMPLETED','Back pain consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000007' AND u.username='demo.ortho';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-04-30','12:00:00','COMPLETED','General medical check'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000008' AND u.username='demo.general';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-05-08','09:00:00','COMPLETED','Cardiac follow-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000009' AND u.username='doctor';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-05-20','10:30:00','COMPLETED','Skin allergy consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000010' AND u.username='demo.derma';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-05-31','11:00:00','COMPLETED','Orthopedic consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000011' AND u.username='demo.ortho';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-06-14','09:30:00','COMPLETED','General health consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000012' AND u.username='demo.general';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-07-05','10:00:00','COMPLETED','Pediatric consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000013' AND u.username='demo.pediatrics';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-07-23','13:00:00','COMPLETED','Dermatology check'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000014' AND u.username='demo.derma';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-08-06','10:00:00','COMPLETED','Cardiac consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000015' AND u.username='doctor';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-08-19','11:00:00','COMPLETED','General medical consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000016' AND u.username='demo.general';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-08-27','14:30:00','COMPLETED','Orthopedic check-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000017' AND u.username='demo.ortho';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-09-08','09:30:00','COMPLETED','Skin consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000018' AND u.username='demo.derma';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-09-21','10:30:00','COMPLETED','Pediatric consultation'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000019' AND u.username='demo.pediatrics';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-10-03','10:00:00','SCHEDULED','General health check'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000020' AND u.username='demo.general';

-- Extra visits for a more useful department analysis.
INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-08-28','15:00:00','SCHEDULED','Cardiac follow-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000015' AND u.username='doctor';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-08-29','15:30:00','SCHEDULED','General follow-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000016' AND u.username='demo.general';

INSERT INTO appointments (patient_id,doctor_id,appointment_date,appointment_time,status,reason)
SELECT p.id,d.id,'2026-09-22','12:00:00','SCHEDULED','Orthopedic follow-up'
FROM patients p JOIN doctors d JOIN users u ON d.user_id=u.id
WHERE p.patient_code='MSP-2026-000017' AND u.username='demo.ortho';

-- =========================================================
-- 7. Verification
-- =========================================================

SELECT COUNT(*) AS total_patients FROM patients;
SELECT COUNT(*) AS total_appointments FROM appointments;

SELECT d.name AS department,
       COUNT(DISTINCT a.patient_id) AS unique_patients
FROM appointments a
JOIN doctors doc ON a.doctor_id = doc.id
JOIN departments d ON doc.department_id = d.id
GROUP BY d.id, d.name
ORDER BY unique_patients DESC, d.name;
