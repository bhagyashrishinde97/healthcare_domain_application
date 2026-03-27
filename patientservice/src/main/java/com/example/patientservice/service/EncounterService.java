package com.example.patientservice.service;

import com.example.patientservice.dto.EncounterRequestDto;
import com.example.patientservice.dto.EncounterResponseDto;

public interface EncounterService {
    EncounterResponseDto createEncounter(EncounterRequestDto dto);
}
