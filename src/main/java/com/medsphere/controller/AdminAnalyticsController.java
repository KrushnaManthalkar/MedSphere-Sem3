package com.medsphere.controller;

import com.medsphere.entity.Department;
import com.medsphere.service.AnalyticsService;
import com.medsphere.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/analytics")
public class AdminAnalyticsController {
    private final AnalyticsService analyticsService;
    private final DepartmentService departmentService;

    public AdminAnalyticsController(AnalyticsService analyticsService, DepartmentService departmentService) {
        this.analyticsService = analyticsService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String analytics(@RequestParam(required = false) Integer year,
                            @RequestParam(required = false) Integer month,
                            @RequestParam(required = false) Long departmentId,
                            Model model) {
        LocalDate today = LocalDate.now();
        int selectedYear = year != null ? year : today.getYear();
        Integer selectedMonth = month;

        LocalDate startDate;
        LocalDate endDate;

        if (selectedMonth != null && selectedMonth >= 1 && selectedMonth <= 12) {
            YearMonth selected = YearMonth.of(selectedYear, selectedMonth);
            startDate = selected.atDay(1);
            endDate = selected.atEndOfMonth();
        } else {
            selectedMonth = null;
            startDate = LocalDate.of(selectedYear, 1, 1);
            endDate = LocalDate.of(selectedYear, 12, 31);
        }

        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("totalPatients", analyticsService.getTotalPatients());
        model.addAttribute("newPatients", analyticsService.getNewPatients(startDate, endDate));
        model.addAttribute("uniqueVisitedPatients",
                analyticsService.getUniqueVisitedPatients(startDate, endDate, departmentId));
        model.addAttribute("totalVisits",
                analyticsService.getVisits(startDate, endDate, departmentId));
        model.addAttribute("departments", departments);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("monthNames", List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        model.addAttribute("years", Arrays.asList(selectedYear - 2, selectedYear - 1, selectedYear, selectedYear + 1));

        List<MonthStat> monthlyStats = buildMonthlyStats(
                analyticsService.getMonthlyNewPatients(
                        LocalDate.of(selectedYear, 1, 1),
                        LocalDate.of(selectedYear, 12, 31)));
        model.addAttribute("monthlyStats", monthlyStats);

        List<DepartmentStat> departmentStats = new ArrayList<>();
        for (Object[] row : analyticsService.getDepartmentPatientCounts(startDate, endDate, departmentId)) {
            departmentStats.add(new DepartmentStat((String) row[0], ((Number) row[1]).longValue()));
        }
        model.addAttribute("departmentStats", departmentStats);

        return "admin/analytics";
    }

    private List<MonthStat> buildMonthlyStats(List<Object[]> rows) {
        long[] counts = new long[12];
        for (Object[] row : rows) {
            int monthNumber = ((Number) row[0]).intValue();
            counts[monthNumber - 1] = ((Number) row[1]).longValue();
        }

        String[] names = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        List<MonthStat> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) result.add(new MonthStat(names[i], counts[i]));
        return result;
    }

    public record MonthStat(String month, long count) {}
    public record DepartmentStat(String department, long count) {}
}
