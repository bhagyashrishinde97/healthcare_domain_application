package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Clinic;
import com.example.patientservice.repository.ClinicRepository;
import com.example.patientservice.service.ClinicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;

    @Override
    public ApiResponse<ClinicResponseDto> createClinic(ClinicRequestDto dto) {
        log.info("Creating clinic with email: {}", dto.getContactEmail());
        if (clinicRepository.findByContactEmail(dto.getContactEmail()).isPresent()) {
            throw new IllegalArgumentException("Clinic email already exists");
        }
        Clinic saved = clinicRepository.save(dto.toEntity());
        log.info("Clinic created with id={}", saved.getId());
        return ApiResponse.success("Clinic created successfully", saved.toDto());
    }

    @Override
    public ApiResponse<ClinicResponseDto> getClinicById(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id " + id));
        return ApiResponse.success("Clinic fetched successfully", clinic.toDto());
    }

    @Override
    public ApiResponse<List<ClinicResponseDto>> getAllClinics() {
        List<ClinicResponseDto> list = clinicRepository.findAll()
                .stream().map(Clinic::toDto).toList();
        return ApiResponse.success("All clinics fetched successfully", list);
    }

    @Override
    public ApiResponse<ClinicResponseDto> updateClinic(Long id, ClinicRequestDto dto) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id " + id));
        clinic.setClinicName(dto.getClinicName());
        clinic.setLocation(dto.getLocation());
        clinic.setContactEmail(dto.getContactEmail());
        clinic.setIsActive(dto.getIsActive());    // FIX: was setActive() — wrong for Boolean wrapper
        Clinic updated = clinicRepository.save(clinic);
        return ApiResponse.success("Clinic updated successfully", updated.toDto());
    }

    @Override
    public ApiResponse<Object> deleteClinic(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id " + id));
        clinicRepository.delete(clinic);
        return ApiResponse.success("Clinic deleted successfully", null);
    }
}