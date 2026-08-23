package com.medsphere.service.impl;

import com.medsphere.dto.DepartmentForm;
import com.medsphere.entity.Department;
import com.medsphere.exception.ResourceNotFoundException;
import com.medsphere.repository.DepartmentRepository;
import com.medsphere.service.DepartmentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    public DepartmentServiceImpl(DepartmentRepository repository) { this.repository = repository; }
    @Override @Transactional(readOnly = true) public List<Department> getAllDepartments() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Department getDepartment(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department")); }
    @Override public Department create(DepartmentForm form) { Department department = new Department(); copy(form, department); return repository.save(department); }
    @Override public Department update(Long id, DepartmentForm form) { Department department = getDepartment(id); copy(form, department); return repository.save(department); }
    @Override @Transactional(readOnly = true) public DepartmentForm getForm(Long id) { Department d=getDepartment(id); DepartmentForm f=new DepartmentForm(); f.setName(d.getName()); f.setDescription(d.getDescription()); return f; }
    @Override @Transactional(readOnly = true) public boolean isNameAvailable(String name, Long excludedId) { return excludedId == null ? !repository.existsByName(name) : !repository.existsByNameAndIdNot(name, excludedId); }
    private void copy(DepartmentForm form, Department department) { department.setName(form.getName().trim()); department.setDescription(form.getDescription()==null || form.getDescription().isBlank() ? null : form.getDescription().trim()); }
}
