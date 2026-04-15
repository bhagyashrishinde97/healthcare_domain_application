package com.example.patientservice.service;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.PrescriptionResponseDto;

import java.util.List;

public interface PrescriptionsService {

    ApiResponse<PrescriptionResponseDto> addPrescription(Long encounterId, PrescriptionRequestDto dto);

    ApiResponse<PrescriptionResponseDto> getPrescriptionById(Long id);

    ApiResponse<List<PrescriptionResponseDto>> getPrescriptionsByEncounterId(Long encounterId);

    ApiResponse<List<PrescriptionResponseDto>> getAllPrescriptions();

    ApiResponse<PrescriptionResponseDto> updatePrescription(Long id, PrescriptionRequestDto dto);

    ApiResponse<Object> deletePrescription(Long id);
}