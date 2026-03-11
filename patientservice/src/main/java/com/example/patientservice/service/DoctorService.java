package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.DoctorRequestDto;
import com.example.patientservice.dto.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    ApiResponse<DoctorResponseDto> createDoctor(DoctorRequestDto dto);

    ApiResponse<DoctorResponseDto> getDoctorById(Long id);

    ApiResponse<List<DoctorResponseDto>> getAllDoctors();

    ApiResponse<DoctorResponseDto> updateDoctor(Long id, DoctorRequestDto dto);

    ApiResponse<Object> deleteDoctor(Long id);
}
