package com.medsphere.service;

import com.medsphere.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final AnalyticsRepository repository;

    public AnalyticsServiceImpl(AnalyticsRepository repository) {
        this.repository = repository;
    }

    public long getTotalPatients() { return repository.count(); }

    public long getNewPatients(LocalDate startDate, LocalDate endDate) {
        return repository.countByRegistrationDateBetween(startDate, endDate);
    }

    public long getUniqueVisitedPatients(LocalDate startDate, LocalDate endDate, Long departmentId) {
        return repository.countUniqueVisitedPatients(startDate, endDate, departmentId);
    }

    public long getVisits(LocalDate startDate, LocalDate endDate, Long departmentId) {
        return repository.countVisits(startDate, endDate, departmentId);
    }

    public List<Object[]> getMonthlyNewPatients(LocalDate startDate, LocalDate endDate) {
        return repository.countNewPatientsByMonth(startDate, endDate);
    }

    public List<Object[]> getDepartmentPatientCounts(LocalDate startDate, LocalDate endDate, Long departmentId) {
        return repository.countUniquePatientsByDepartment(startDate, endDate, departmentId);
    }
}
