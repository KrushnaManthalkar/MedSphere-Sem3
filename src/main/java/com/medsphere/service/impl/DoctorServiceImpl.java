package com.medsphere.service.impl;

import com.medsphere.dto.DoctorCreateForm;
import com.medsphere.dto.DoctorForm;
import com.medsphere.entity.Department;
import com.medsphere.entity.Doctor;
import com.medsphere.entity.Role;
import com.medsphere.entity.User;
import com.medsphere.enums.RoleType;
import com.medsphere.exception.ResourceNotFoundException;
import com.medsphere.repository.DepartmentRepository;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.RoleRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.DoctorService;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor"));
    }

    @Override
    public Doctor create(DoctorForm form) {
        Doctor doctor = new Doctor();
        copy(form, doctor, null);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor createNewDoctor(DoctorCreateForm form) {

        String username = form.getUsername().trim();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username already exists. Please choose another username.");
        }

        Role doctorRole = roleRepository.findByRoleName(RoleType.DOCTOR)
                .orElseThrow(() ->
                        new IllegalStateException("DOCTOR role not found."));

        Department department = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFullName(form.getFullName().trim());
        user.setEmail(blankToNull(form.getEmail()));
        user.setActive(true);
        user.setRole(doctorRole);

        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setDepartment(department);
        doctor.setSpecialization(form.getSpecialization().trim());
        doctor.setPhone(form.getPhone().trim());

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Long id, DoctorForm form) {
        Doctor doctor = getDoctor(id);
        copy(form, doctor, id);
        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorForm getForm(Long id) {

        Doctor doctor = getDoctor(id);

        DoctorForm form = new DoctorForm();

        form.setUserId(doctor.getUser().getId());
        form.setDepartmentId(doctor.getDepartment().getId());
        form.setSpecialization(doctor.getSpecialization());
        form.setPhone(doctor.getPhone());

        return form;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAvailableDoctorUsers(Long currentDoctorId) {

        return userRepository
                .findByRoleRoleName(RoleType.DOCTOR)
                .stream()
                .filter(user ->
                        !doctorRepository.existsByUserId(user.getId())
                                || (currentDoctorId != null
                                && doctorRepository.findByUserId(user.getId())
                                .map(doctor ->
                                        doctor.getId().equals(currentDoctorId))
                                .orElse(false)))
                .toList();
    }

    private void copy(
            DoctorForm form,
            Doctor doctor,
            Long currentDoctorId) {

        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User"));

        if (user.getRole().getRoleName() != RoleType.DOCTOR) {
            throw new IllegalArgumentException(
                    "Only ROLE_DOCTOR accounts can be linked to a doctor profile.");
        }

        Doctor existing = doctorRepository
                .findByUserId(user.getId())
                .orElse(null);

        if (existing != null
                && !existing.getId().equals(currentDoctorId)) {

            throw new IllegalArgumentException(
                    "This user already has a doctor profile.");
        }

        Department department = departmentRepository
                .findById(form.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department"));

        doctor.setUser(user);
        doctor.setDepartment(department);
        doctor.setSpecialization(form.getSpecialization().trim());
        doctor.setPhone(form.getPhone().trim());
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim();
    }
}