package com.medsphere.service.impl;

import com.medsphere.dto.DoctorForm;
import com.medsphere.entity.Department;
import com.medsphere.entity.Doctor;
import com.medsphere.entity.User;
import com.medsphere.enums.RoleType;
import com.medsphere.exception.ResourceNotFoundException;
import com.medsphere.repository.DepartmentRepository;
import com.medsphere.repository.DoctorRepository;
import com.medsphere.repository.UserRepository;
import com.medsphere.service.DoctorService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository; private final UserRepository userRepository; private final DepartmentRepository departmentRepository;
    public DoctorServiceImpl(DoctorRepository d, UserRepository u, DepartmentRepository dep) { doctorRepository=d; userRepository=u; departmentRepository=dep; }
    @Override @Transactional(readOnly = true) public List<Doctor> getAllDoctors() { return doctorRepository.findAll(); }
    @Override @Transactional(readOnly = true) public Doctor getDoctor(Long id) { return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor")); }
    @Override public Doctor create(DoctorForm form) { Doctor doctor = new Doctor(); copy(form, doctor, null); return doctorRepository.save(doctor); }
    @Override public Doctor update(Long id, DoctorForm form) { Doctor doctor=getDoctor(id); copy(form, doctor, id); return doctorRepository.save(doctor); }
    @Override @Transactional(readOnly = true) public DoctorForm getForm(Long id) { Doctor d=getDoctor(id); DoctorForm f=new DoctorForm(); f.setUserId(d.getUser().getId()); f.setDepartmentId(d.getDepartment().getId()); f.setSpecialization(d.getSpecialization()); f.setPhone(d.getPhone()); return f; }
    @Override @Transactional(readOnly = true) public List<User> getAvailableDoctorUsers(Long currentDoctorId) { return userRepository.findByRoleRoleName(RoleType.DOCTOR).stream().filter(u -> !doctorRepository.existsByUserId(u.getId()) || (currentDoctorId != null && doctorRepository.findByUserId(u.getId()).map(d -> d.getId().equals(currentDoctorId)).orElse(false))).toList(); }
    private void copy(DoctorForm form, Doctor doctor, Long currentDoctorId) { User user=userRepository.findById(form.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User")); if (user.getRole().getRoleName()!=RoleType.DOCTOR) throw new IllegalArgumentException("Only ROLE_DOCTOR accounts can be linked to a doctor profile."); Doctor existing=doctorRepository.findByUserId(user.getId()).orElse(null); if(existing!=null && !existing.getId().equals(currentDoctorId)) throw new IllegalArgumentException("This user already has a doctor profile."); Department department=departmentRepository.findById(form.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department")); doctor.setUser(user); doctor.setDepartment(department); doctor.setSpecialization(form.getSpecialization().trim()); doctor.setPhone(form.getPhone().trim()); }
}
