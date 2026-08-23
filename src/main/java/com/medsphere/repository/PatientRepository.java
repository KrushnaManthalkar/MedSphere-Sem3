package com.medsphere.repository;

import com.medsphere.entity.Patient;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientCode(String patientCode);
    boolean existsByPatientCode(String patientCode);
    Optional<Patient> findTopByPatientCodeStartingWithOrderByPatientCodeDesc(String patientCodePrefix);
    List<Patient> findByPatientCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(
            String patientCode, String name, String phone);
}
