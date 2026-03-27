package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.AppointmentRequestDto;
import com.example.patientservice.dto.AppointmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto);

    ApiResponse<AppointmentResponseDto> getAppointmentById(UUID id);

    ApiResponse<List<AppointmentResponseDto>> getAllAppointments();

    ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto);

    ApiResponse<Object> deleteAppointment(Long id);
}
