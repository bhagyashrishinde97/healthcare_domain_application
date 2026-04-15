package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;

import java.util.List;

public interface ClinicService {

    ApiResponse<ClinicResponseDto> createClinic(ClinicRequestDto dto);

    ApiResponse<ClinicResponseDto> getClinicById(Long id);

    ApiResponse<List<ClinicResponseDto>> getAllClinics();

    ApiResponse<List<ClinicResponseDto>> getActiveClinics();

    ApiResponse<ClinicResponseDto> updateClinic(Long id, ClinicRequestDto dto);

    ApiResponse<ClinicResponseDto> activateClinic(Long id);

    ApiResponse<ClinicResponseDto> deactivateClinic(Long id);

    ApiResponse<Object> deleteClinic(Long id);
}