package com.example.patientservice.serviceimpl;

import com.example.patientservice.service.AppointmentService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    @Override
    public ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto) {
        return null;
    }

    @Override
    public ApiResponse<AppointmentResponseDto> getAppointmentById(Long id) {
        return null;
    }

    @Override
    public ApiResponse<List<AppointmentResponseDto>> getAllAppointments() {
        return null;
    }

    @Override
    public ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto) {
        return null;
    }

    @Override
    public ApiResponse<Object> deleteAppointment(Long id) {
        return null;
    }
}
