package com.example.patientservice.service;

public interface AppointmentService  {

    ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto);

    ApiResponse<AppointmentResponseDto> getAppointmentById(Long id);

    ApiResponse<List<AppointmentResponseDto>> getAllAppointments();

    ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto);

    ApiResponse<Object> deleteAppointment(Long id);

}
