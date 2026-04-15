package com.example.patientservice.service;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.EncounterResponseDto;

public interface EncounterService {
    EncounterResponseDto createEncounter(EncounterRequestDto dto);
}
