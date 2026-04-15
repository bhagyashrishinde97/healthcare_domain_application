package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.PatientRequestDto;
import com.example.patientservice.dto.response.PatientResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import com.example.patientservice.utility.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public ApiResponse<PatientResponseDto> createOrUpdateMyProfile(PatientRequestDto dto) {
        UUID userId = JwtHelper.getCurrentUserId();
        log.info("Service: Creating/updating patient profile for userId={}", userId);

        Patient patient = patientRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Patient newPatient = patientMapper.toEntity(dto);
                    newPatient.setUserId(userId);
                    log.info("Creating new patient profile");
                    return newPatient;
                });

        if (patient.getId() != null) {
            log.info("Updating existing patient profile id={}", patient.getId());
            patientMapper.updateEntity(dto, patient);
        }

        Patient saved = patientRepository.save(patient);

        log.info("Service: Patient profile saved successfully");
        return ApiResponse.success("Patient profile saved successfully", patientMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PatientResponseDto> getMyProfile() {
        UUID userId = JwtHelper.getCurrentUserId();
        log.info("Service: Fetching patient profile for userId={}", userId);

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found. Please create one first."));

        return ApiResponse.success("Patient profile fetched successfully", patientMapper.toDto(patient));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PatientResponseDto> getPatientById(Long id) {
        log.info("Service: Fetching patient by id={}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        return ApiResponse.success("Patient fetched successfully", patientMapper.toDto(patient));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<PatientResponseDto>> getAllPatients() {
        log.info("Service: Fetching all patients");

        List<PatientResponseDto> patients = patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .toList();

        return ApiResponse.success("Patients fetched successfully", patients);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<PatientResponseDto>> searchPatients(String firstName, String lastName, String email) {
        log.info("Service: Searching patients - firstName={}, lastName={}, email={}", firstName, lastName, email);

        List<Patient> patients;

        if (email != null && !email.isBlank()) {
            patients = patientRepository.findByEmailContainingIgnoreCase(email);
        } else if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank()) {
            patients = patientRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName);
        } else if (firstName != null && !firstName.isBlank()) {
            patients = patientRepository.findByFirstNameContainingIgnoreCase(firstName);
        } else if (lastName != null && !lastName.isBlank()) {
            patients = patientRepository.findByLastNameContainingIgnoreCase(lastName);
        } else {
            patients = patientRepository.findAll();
        }

        List<PatientResponseDto> result = patients.stream()
                .map(patientMapper::toDto)
                .toList();

        return ApiResponse.success("Patient search completed successfully", result);
    }
}