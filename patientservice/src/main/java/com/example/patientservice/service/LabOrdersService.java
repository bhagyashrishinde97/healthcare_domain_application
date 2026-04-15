package com.example.patientservice.service;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.enums.LabStatus;

import java.util.List;

public interface LabOrdersService {

    ApiResponse<LabOrderResponseDto> addLabOrder(Long encounterId, LabOrderRequestDto dto);

    ApiResponse<LabOrderResponseDto> getLabOrderById(Long id);

    ApiResponse<List<LabOrderResponseDto>> getLabOrdersByEncounterId(Long encounterId);

    ApiResponse<List<LabOrderResponseDto>> getAllLabOrders();

    ApiResponse<LabOrderResponseDto> updateLabOrderStatus(Long id, LabStatus status);

    ApiResponse<LabOrderResponseDto> updateLabOrderResult(Long id, String result);
}