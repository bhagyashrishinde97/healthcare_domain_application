package com.example.patientservice.configuration;

import com.example.patientservice.model.Roles;
import com.example.patientservice.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleDataLoader implements CommandLineRunner {

    private final RolesRepository rolesRepository;

    @Override
    public void run(String... args) {
        createRoleIfAbsent("PATIENT", "Patient Role");
        createRoleIfAbsent("DOCTOR",  "Doctor Role");
        createRoleIfAbsent("ADMIN",   "Admin Role");
    }

    private void createRoleIfAbsent(String name, String desc) {
        if (rolesRepository.findByRoleName(name).isEmpty()) {
            rolesRepository.save(
                    Roles.builder()
                            .roleName(name)
                            .description(desc)
                            .isActive(true)
                            .build()
            );
        }
    }
}