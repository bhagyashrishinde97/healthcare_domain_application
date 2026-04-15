package com.example.patientservice.service;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.VitalsResponseDto;

import java.util.List;

public interface VitalsService {

    ApiResponse<VitalsResponseDto> addVitals(Long encounterId, VitalsRequestDto dto);

    ApiResponse<VitalsResponseDto> getVitalsById(Long id);

    ApiResponse<List<VitalsResponseDto>> getVitalsByEncounterId(Long encounterId);

    ApiResponse<List<VitalsResponseDto>> getAllVitals();

    ApiResponse<VitalsResponseDto> updateVitals(Long id, VitalsRequestDto dto);
}