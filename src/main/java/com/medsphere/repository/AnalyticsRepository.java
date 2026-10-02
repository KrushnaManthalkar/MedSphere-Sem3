package com.medsphere.repository;

import com.medsphere.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface AnalyticsRepository extends JpaRepository<Patient, Long> {
    long countByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
            select month(p.registrationDate), count(p)
            from Patient p
            where p.registrationDate between :startDate and :endDate
            group by month(p.registrationDate)
            order by month(p.registrationDate)
            """)
    List<Object[]> countNewPatientsByMonth(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("""
            select d.name, count(distinct a.patient.id)
            from Appointment a
            join a.doctor doc
            join doc.department d
            where a.appointmentDate between :startDate and :endDate
              and (:departmentId is null or d.id = :departmentId)
            group by d.id, d.name
            order by count(distinct a.patient.id) desc, d.name asc
            """)
    List<Object[]> countUniquePatientsByDepartment(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("departmentId") Long departmentId);

    @Query("""
            select count(distinct a.patient.id)
            from Appointment a
            join a.doctor doc
            join doc.department d
            where a.appointmentDate between :startDate and :endDate
              and (:departmentId is null or d.id = :departmentId)
            """)
    long countUniqueVisitedPatients(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param("departmentId") Long departmentId);

    @Query("""
            select count(a)
            from Appointment a
            join a.doctor doc
            join doc.department d
            where a.appointmentDate between :startDate and :endDate
              and (:departmentId is null or d.id = :departmentId)
            """)
    long countVisits(@Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("departmentId") Long departmentId);
}
