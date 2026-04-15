package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.AppointmentRequestDto;
import com.example.patientservice.dto.response.AppointmentResponseDto;
import com.example.patientservice.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto);

    ApiResponse<AppointmentResponseDto> getAppointmentById(UUID appointmentId);

    ApiResponse<List<AppointmentResponseDto>> getAllAppointments();

    ApiResponse<List<AppointmentResponseDto>> getAppointmentsByPatientId(Long patientId);

    ApiResponse<List<AppointmentResponseDto>> getAppointmentsByDoctorId(Long doctorId);

    ApiResponse<List<AppointmentResponseDto>> getAppointmentsByStatus(AppointmentStatus status);

    ApiResponse<List<AppointmentResponseDto>> getDoctorAvailableSlots(
            Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto);

    ApiResponse<AppointmentResponseDto> confirmAppointment(Long id);

    ApiResponse<AppointmentResponseDto> cancelAppointment(Long id, String reason);

    ApiResponse<Object> deleteAppointment(Long id);
}