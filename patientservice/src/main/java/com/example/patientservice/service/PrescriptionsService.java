package com.example.patientservice.service;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.PrescriptionResponseDto;

public interface PrescriptionsService {
    PrescriptionResponseDto addPrescription(Long encounterId, PrescriptionRequestDto dto);
}
