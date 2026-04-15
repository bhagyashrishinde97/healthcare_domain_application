package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.PatientRequestDto;
import com.example.patientservice.dto.response.PatientResponseDto;

import java.util.List;

public interface PatientService {
    ApiResponse<PatientResponseDto> createPatient(PatientRequestDto dto);

    ApiResponse<PatientResponseDto> getPatientById(Long id);

    ApiResponse<List<PatientResponseDto>> getAllPatients();

    ApiResponse<PatientResponseDto> updatePatient(Long id, PatientRequestDto dto);

    ApiResponse<Object> deletePatient(Long id);
}