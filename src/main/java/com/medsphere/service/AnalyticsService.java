package com.medsphere.service;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {
    long getTotalPatients();
    long getNewPatients(LocalDate startDate, LocalDate endDate);
    long getUniqueVisitedPatients(LocalDate startDate, LocalDate endDate, Long departmentId);
    long getVisits(LocalDate startDate, LocalDate endDate, Long departmentId);
    List<Object[]> getMonthlyNewPatients(LocalDate startDate, LocalDate endDate);
    List<Object[]> getDepartmentPatientCounts(LocalDate startDate, LocalDate endDate, Long departmentId);
}
