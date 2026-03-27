package com.example.patientservice.service;

import com.example.patientservice.dto.VitalsRequestDto;
import com.example.patientservice.dto.VitalsResponseDto;

public interface VitalsService {
    VitalsResponseDto addVitals(Long encounterId, VitalsRequestDto dto);
}
