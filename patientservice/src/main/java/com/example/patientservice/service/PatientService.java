package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.PatientRequestDto;
import com.example.patientservice.dto.response.PatientResponseDto;

import java.util.List;

public interface PatientService {

    ApiResponse<PatientResponseDto> createOrUpdateMyProfile(PatientRequestDto dto);

    ApiResponse<PatientResponseDto> getMyProfile();

    ApiResponse<PatientResponseDto> getPatientById(Long id);

    ApiResponse<List<PatientResponseDto>> getAllPatients();

    ApiResponse<List<PatientResponseDto>> searchPatients(String firstName, String lastName, String email);
}