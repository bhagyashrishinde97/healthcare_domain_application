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
    public void run(String... args) throws Exception {
        if(rolesRepository.findByRoleName("PATIENT").isEmpty()){
            rolesRepository.save(
                    Roles.builder()
                            .roleName("PATIENT")
                            .description("Patient Role")
                            .isActive(true)
                            .build()
            );
        }

        if(rolesRepository.findByRoleName("DOCTOR").isEmpty()){
            rolesRepository.save(
                    Roles.builder()
                            .roleName("DOCTOR")
                            .description("Doctor Role")
                            .isActive(true)
                            .build()
            );
        }

        if(rolesRepository.findByRoleName("ADMIN").isEmpty()){
            rolesRepository.save(
                    Roles.builder()
                            .roleName("ADMIN")
                            .description("Admin Role")
                            .isActive(true)
                            .build()
            );
        }

    }
}


