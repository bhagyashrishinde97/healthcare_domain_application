package com.example.patientservice.service;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.EncounterResponseDto;

import java.util.List;
import java.util.UUID;

public interface EncounterService {

    ApiResponse<EncounterResponseDto> createEncounter(EncounterRequestDto dto);

    ApiResponse<EncounterResponseDto> getEncounterById(UUID encounterId);

    ApiResponse<EncounterResponseDto> getEncounterByAppointmentId(Long appointmentId);

    ApiResponse<List<EncounterResponseDto>> getAllEncounters();

    ApiResponse<List<EncounterResponseDto>> getEncountersByPatientId(Long patientId);

    ApiResponse<EncounterResponseDto> updateEncounter(Long id, EncounterRequestDto dto);
}