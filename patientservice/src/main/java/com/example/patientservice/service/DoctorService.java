package com.example.patientservice.service;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.DoctorRequestDto;
import com.example.patientservice.dto.response.DoctorResponseDto;

import java.util.List;

public interface DoctorService {

    ApiResponse<DoctorResponseDto> createDoctor(DoctorRequestDto dto);

    ApiResponse<DoctorResponseDto> getDoctorById(Long id);

    ApiResponse<List<DoctorResponseDto>> getAllDoctors();

    ApiResponse<List<DoctorResponseDto>> getAvailableDoctors();

    ApiResponse<List<DoctorResponseDto>> getDoctorsBySpecialization(String specialization);

    ApiResponse<DoctorResponseDto> updateDoctor(Long id, DoctorRequestDto dto);

    ApiResponse<DoctorResponseDto> toggleAvailability(Long id, Boolean available);

    ApiResponse<Object> deleteDoctor(Long id);
}