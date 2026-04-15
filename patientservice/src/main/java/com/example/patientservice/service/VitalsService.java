package com.example.patientservice.service;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.VitalsResponseDto;

public interface VitalsService {
    VitalsResponseDto addVitals(Long encounterId, VitalsRequestDto dto);
}
