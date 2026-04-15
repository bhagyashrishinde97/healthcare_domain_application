package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponseDto {
    private String roleName;
    private String description;
    private boolean isActive=true;
    private Set<PermissionResponseDto> permissionResponseDtoSet;
}
