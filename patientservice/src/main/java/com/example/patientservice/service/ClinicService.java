package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.ClinicRequestDto;
import com.example.patientservice.dto.ClinicResponseDto;

import java.util.List;

public interface ClinicService {
    ApiResponse<ClinicResponseDto> createClinic(ClinicRequestDto dto);

    ApiResponse<ClinicResponseDto> getClinicById(Long id);

    ApiResponse<List<ClinicResponseDto>> getAllClinics();

    ApiResponse<ClinicResponseDto> updateClinic(Long id, ClinicRequestDto dto);

    ApiResponse<Object> deleteClinic(Long id);
}
