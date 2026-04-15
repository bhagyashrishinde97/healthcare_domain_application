package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.RolesResponseDto;

import java.util.List;

public interface RolesService {
    ApiResponse<List<RolesResponseDto>> getAllRoles();
}
