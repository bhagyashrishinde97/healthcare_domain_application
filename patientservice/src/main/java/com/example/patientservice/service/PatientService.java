package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.PatientRequestDto;
import com.example.patientservice.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {
    ApiResponse<PatientResponseDto> createPatient(PatientRequestDto dto);

    ApiResponse<PatientResponseDto> getPatientById(Long id);

    ApiResponse<List<PatientResponseDto>> getAllPatients();

    ApiResponse<PatientResponseDto> updatePatient(Long id, PatientRequestDto dto);

    ApiResponse<Object> deletePatient(Long id);
}