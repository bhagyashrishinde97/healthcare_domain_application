package com.example.patientservice.dto;

import com.example.patientservice.dto.AddressResponseDto;
import com.example.patientservice.dto.RolesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String userName;
    private String email;
    private Long contactNumber;
    private String bloodGroup;
    private boolean isActive;
    private AddressResponseDto addressDto;
    private Set<RolesResponseDto> rolesSet;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
