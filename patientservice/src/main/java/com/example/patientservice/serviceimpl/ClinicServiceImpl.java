package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.exception.ValidationException;
import com.example.patientservice.mapper.ClinicMapper;
import com.example.patientservice.model.Clinic;
import com.example.patientservice.repository.ClinicRepository;
import com.example.patientservice.service.ClinicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    @Override
    public ApiResponse<ClinicResponseDto> createClinic(ClinicRequestDto dto) {
        log.info("Service: Creating clinic with email={}", dto.getContactEmail());

        if (clinicRepository.findByContactEmail(dto.getContactEmail()).isPresent()) {
            throw new ValidationException("Clinic with email " + dto.getContactEmail() + " already exists");
        }

        Clinic clinic = clinicMapper.toEntity(dto);
        if (clinic.getIsActive() == null) {
            clinic.setIsActive(true);
        }

        Clinic saved = clinicRepository.save(clinic);

        log.info("Service: Clinic created successfully with id={}", saved.getId());
        return ApiResponse.success("Clinic created successfully", clinicMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<ClinicResponseDto> getClinicById(Long id) {
        log.info("Service: Fetching clinic by id={}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));

        return ApiResponse.success("Clinic fetched successfully", clinicMapper.toDto(clinic));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<ClinicResponseDto>> getAllClinics() {
        log.info("Service: Fetching all clinics");

        List<ClinicResponseDto> clinics = clinicRepository.findAll()
                .stream()
                .map(clinicMapper::toDto)
                .toList();

        return ApiResponse.success("Clinics fetched successfully", clinics);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<ClinicResponseDto>> getActiveClinics() {
        log.info("Service: Fetching active clinics");

        List<ClinicResponseDto> clinics = clinicRepository.findByIsActive(true)
                .stream()
                .map(clinicMapper::toDto)
                .toList();

        return ApiResponse.success("Active clinics fetched successfully", clinics);
    }

    @Override
    public ApiResponse<ClinicResponseDto> updateClinic(Long id, ClinicRequestDto dto) {
        log.info("Service: Updating clinic with id={}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));

        if (!clinic.getContactEmail().equals(dto.getContactEmail())) {
            if (clinicRepository.findByContactEmail(dto.getContactEmail()).isPresent()) {
                throw new ValidationException("Email " + dto.getContactEmail() + " is already in use");
            }
        }

        clinicMapper.updateEntity(dto, clinic);
        Clinic updated = clinicRepository.save(clinic);

        log.info("Service: Clinic updated successfully with id={}", updated.getId());
        return ApiResponse.success("Clinic updated successfully", clinicMapper.toDto(updated));
    }

    @Override
    public ApiResponse<ClinicResponseDto> activateClinic(Long id) {
        log.info("Service: Activating clinic with id={}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));

        clinic.setIsActive(true);
        Clinic updated = clinicRepository.save(clinic);

        log.info("Service: Clinic activated successfully");
        return ApiResponse.success("Clinic activated successfully", clinicMapper.toDto(updated));
    }

    @Override
    public ApiResponse<ClinicResponseDto> deactivateClinic(Long id) {
        log.info("Service: Deactivating clinic with id={}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));

        clinic.setIsActive(false);
        Clinic updated = clinicRepository.save(clinic);

        log.info("Service: Clinic deactivated successfully");
        return ApiResponse.success("Clinic deactivated successfully", clinicMapper.toDto(updated));
    }

    @Override
    public ApiResponse<Object> deleteClinic(Long id) {
        log.info("Service: Soft deleting clinic with id={}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));

        clinic.setIsActive(false);
        clinicRepository.save(clinic);

        log.info("Service: Clinic soft deleted successfully with id={}", id);
        return ApiResponse.successMessage("Clinic deleted successfully");
    }
}