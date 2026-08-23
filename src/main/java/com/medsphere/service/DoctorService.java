package com.medsphere.service;
import com.medsphere.dto.DoctorForm;
import com.medsphere.entity.Doctor;
import com.medsphere.entity.User;
import java.util.List;
public interface DoctorService {
    List<Doctor> getAllDoctors(); Doctor getDoctor(Long id); Doctor create(DoctorForm form); Doctor update(Long id, DoctorForm form);
    DoctorForm getForm(Long id); List<User> getAvailableDoctorUsers(Long currentDoctorId);
}
