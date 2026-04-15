package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.DoctorRequestDto;
import com.example.patientservice.dto.response.DoctorResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.exception.ValidationException;
import com.example.patientservice.mapper.DoctorMapper;
import com.example.patientservice.model.Doctor;
import com.example.patientservice.repository.DoctorRepository;
import com.example.patientservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public ApiResponse<DoctorResponseDto> createDoctor(DoctorRequestDto dto) {
        log.info("Service: Creating doctor with license={}", dto.getLicenseNumber());

        if (doctorRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {
            throw new ValidationException("Doctor with license number " + dto.getLicenseNumber() + " already exists");
        }

        Doctor doctor = doctorMapper.toEntity(dto);
        if (doctor.getAvailabilityStatus() == null) {
            doctor.setAvailabilityStatus(true);
        }

        Doctor saved = doctorRepository.save(doctor);

        log.info("Service: Doctor created successfully with id={}", saved.getId());
        return ApiResponse.success("Doctor created successfully", doctorMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<DoctorResponseDto> getDoctorById(Long id) {
        log.info("Service: Fetching doctor by id={}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        return ApiResponse.success("Doctor fetched successfully", doctorMapper.toDto(doctor));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<DoctorResponseDto>> getAllDoctors() {
        log.info("Service: Fetching all doctors");

        List<DoctorResponseDto> doctors = doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .toList();

        return ApiResponse.success("Doctors fetched successfully", doctors);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<DoctorResponseDto>> getAvailableDoctors() {
        log.info("Service: Fetching available doctors");

        List<DoctorResponseDto> doctors = doctorRepository.findByAvailabilityStatus(true)
                .stream()
                .map(doctorMapper::toDto)
                .toList();

        return ApiResponse.success("Available doctors fetched successfully", doctors);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<DoctorResponseDto>> getDoctorsBySpecialization(String specialization) {
        log.info("Service: Fetching doctors by specialization={}", specialization);

        List<DoctorResponseDto> doctors = doctorRepository.findBySpecializationIgnoreCase(specialization)
                .stream()
                .map(doctorMapper::toDto)
                .toList();

        return ApiResponse.success("Doctors by specialization fetched successfully", doctors);
    }

    @Override
    public ApiResponse<DoctorResponseDto> updateDoctor(Long id, DoctorRequestDto dto) {
        log.info("Service: Updating doctor with id={}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (!doctor.getLicenseNumber().equals(dto.getLicenseNumber())) {
            if (doctorRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {
                throw new ValidationException("License number " + dto.getLicenseNumber() + " is already in use");
            }
        }

        doctorMapper.updateEntity(dto, doctor);
        Doctor updated = doctorRepository.save(doctor);

        log.info("Service: Doctor updated successfully with id={}", updated.getId());
        return ApiResponse.success("Doctor updated successfully", doctorMapper.toDto(updated));
    }

    @Override
    public ApiResponse<DoctorResponseDto> toggleAvailability(Long id, Boolean available) {
        log.info("Service: Toggling doctor availability id={}, available={}", id, available);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctor.setAvailabilityStatus(available);
        Doctor updated = doctorRepository.save(doctor);

        log.info("Service: Doctor availability updated successfully");
        return ApiResponse.success("Doctor availability updated successfully", doctorMapper.toDto(updated));
    }

    @Override
    public ApiResponse<Object> deleteDoctor(Long id) {
        log.info("Service: Soft deleting doctor with id={}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctor.setAvailabilityStatus(false);
        doctorRepository.save(doctor);

        log.info("Service: Doctor soft deleted successfully with id={}", id);
        return ApiResponse.successMessage("Doctor deactivated successfully");
    }
}