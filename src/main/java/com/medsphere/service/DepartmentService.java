package com.medsphere.service;
import com.medsphere.dto.DepartmentForm;
import com.medsphere.entity.Department;
import java.util.List;
public interface DepartmentService {
    List<Department> getAllDepartments(); Department getDepartment(Long id);
    Department create(DepartmentForm form); Department update(Long id, DepartmentForm form);
    DepartmentForm getForm(Long id); boolean isNameAvailable(String name, Long excludedId);
}
