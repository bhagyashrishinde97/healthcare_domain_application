package com.example.patientservice.dto.response;

import com.example.patientservice.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.Set;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponseDto {
    private UUID userId;
    private String username;
    private String email;
    private Set<Roles> role;
}
