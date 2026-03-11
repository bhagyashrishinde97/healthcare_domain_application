package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.ClinicRequestDto;
import com.example.patientservice.dto.ClinicResponseDto;
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
            log.error("Clinic already exists with email: {}", dto.getContactEmail());
            throw new IllegalArgumentException("Clinic email already exists");
        }

        Clinic clinic = dto.toEntity();

        Clinic savedClinic = clinicRepository.save(clinic);

        log.info("Clinic created successfully with id: {}", savedClinic.getId());

        return ApiResponse.success("Clinic created successfully", savedClinic.toDto());
    }

    @Override
    public ApiResponse<ClinicResponseDto> getClinicById(Long id) {

        log.info("Fetching clinic with id: {}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Clinic not found with id: {}", id);
                    return new ResourceNotFoundException("Clinic not found with id " + id);
                });

        return ApiResponse.success("Clinic fetched successfully", clinic.toDto());
    }

    @Override
    public ApiResponse<List<ClinicResponseDto>> getAllClinics() {

        log.info("Fetching all clinics");

        List<ClinicResponseDto> clinics = clinicRepository.findAll()
                .stream()
                .map(Clinic::toDto)
                .toList();

        log.info("Total clinics found: {}", clinics.size());

        return ApiResponse.success("All clinics fetched successfully", clinics);
    }

    @Override
    public ApiResponse<ClinicResponseDto> updateClinic(Long id, ClinicRequestDto dto) {

        log.info("Updating clinic with id: {}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Clinic not found for update with id: {}", id);
                    return new ResourceNotFoundException("Clinic not found with id " + id);
                });

        clinic.setClinicName(dto.getClinicName());
        clinic.setLocation(dto.getLocation());
       // clinic.setCity(dto.getCity());
        clinic.setContactEmail(dto.getContactEmail());
        clinic.setActive(dto.getIsActive());

        Clinic updatedClinic = clinicRepository.save(clinic);

        log.info("Clinic updated successfully with id: {}", updatedClinic.getId());

        return ApiResponse.success("Clinic updated successfully", updatedClinic.toDto());
    }

    @Override
    public ApiResponse<Object> deleteClinic(Long id) {

        log.info("Deleting clinic with id: {}", id);

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Clinic not found for deletion with id: {}", id);
                    return new ResourceNotFoundException("Clinic not found with id " + id);
                });

        clinicRepository.delete(clinic);

        log.info("Clinic deleted successfully with id: {}", id);

        return ApiResponse.success("Clinic deleted successfully", null);
    }
}