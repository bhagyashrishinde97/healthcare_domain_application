package com.example.patientservice.service;

import com.example.patientservice.dto.PrescriptionRequestDto;
import com.example.patientservice.dto.PrescriptionResponseDto;

public interface PrescriptionsService {
    PrescriptionResponseDto addPrescription(Long encounterId, PrescriptionRequestDto dto);
}
