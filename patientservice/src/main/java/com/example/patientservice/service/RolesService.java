package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.RolesResponseDto;

import java.util.List;

public interface RolesService {
    ApiResponse<List<RolesResponseDto>> getAllRoles();
}
