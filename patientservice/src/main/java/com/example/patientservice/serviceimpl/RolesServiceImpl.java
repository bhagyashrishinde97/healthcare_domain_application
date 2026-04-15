package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.RolesResponseDto;
import com.example.patientservice.model.Roles;
import com.example.patientservice.repository.RolesRepository;
import com.example.patientservice.service.RolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    @Override
    public ApiResponse<List<RolesResponseDto>> getAllRoles() {

        log.info("Fetching all roles from database");

        List<Roles> roles = rolesRepository.findAll();

        if (roles.isEmpty()) {
            log.warn("No roles found in database");
            return ApiResponse.success("No roles available", List.of());
        }

        List<RolesResponseDto> rolesList = roles
                .stream()
                .map(this::convertToDto)
                .toList();

        log.info("Total roles fetched: {}", rolesList.size());

        return ApiResponse.success(
                "Roles fetched successfully",
                rolesList
        );
    }

    private RolesResponseDto convertToDto(Roles role) {
        return RolesResponseDto.builder()
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .isActive(role.getIsActive())
                .build();
    }
}