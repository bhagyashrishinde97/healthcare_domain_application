package com.example.patientservice.service;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.LabOrderResponseDto;

public interface LabOrdersService {
    LabOrderResponseDto addLabOrder(Long encounterId, LabOrderRequestDto dto);
}
