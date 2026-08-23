package com.medsphere.config;

import com.medsphere.entity.Role;
import com.medsphere.entity.User;
import com.medsphere.enums.RoleType;
import com.medsphere.repository.RoleRepository;
import com.medsphere.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Seeds only missing development roles and users; existing data is never overwritten. */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDevelopmentUsers(RoleRepository roleRepository,
                                                  UserRepository userRepository,
                                                  PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = getOrCreateRole(roleRepository, RoleType.ADMIN);
            Role receptionistRole = getOrCreateRole(roleRepository, RoleType.RECEPTIONIST);
            Role doctorRole = getOrCreateRole(roleRepository, RoleType.DOCTOR);

            createUserIfMissing(userRepository, passwordEncoder, "admin", "admin123", "System Administrator", "admin@medsphere.local", adminRole);
            createUserIfMissing(userRepository, passwordEncoder, "receptionist", "receptionist123", "Reception Desk", "receptionist@medsphere.local", receptionistRole);
            createUserIfMissing(userRepository, passwordEncoder, "doctor", "doctor123", "Development Doctor", "doctor@medsphere.local", doctorRole);
        };
    }

    private Role getOrCreateRole(RoleRepository roleRepository, RoleType roleType) {
        return roleRepository.findByRoleName(roleType).orElseGet(() -> {
            Role role = new Role();
            role.setRoleName(roleType);
            return roleRepository.save(role);
        });
    }

    private void createUserIfMissing(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                     String username, String rawPassword, String fullName, String email, Role role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setActive(true);
        user.setRole(role);
        userRepository.save(user);
    }
}
