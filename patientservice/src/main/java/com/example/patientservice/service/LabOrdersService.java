package com.example.patientservice.service;

import com.example.patientservice.dto.LabOrderRequestDto;
import com.example.patientservice.dto.LabOrderResponseDto;

public interface LabOrdersService {
    LabOrderResponseDto addLabOrder(Long encounterId, LabOrderRequestDto dto);
}
