package com.medsphere.repository;

import com.medsphere.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    List<MedicalHistory> findByPatientIdOrderByRecordDateDesc(Long patientId);
}
