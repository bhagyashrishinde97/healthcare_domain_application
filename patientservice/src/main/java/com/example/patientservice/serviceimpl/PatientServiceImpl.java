package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.PatientRequestDto;
import com.example.patientservice.dto.response.PatientResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public ApiResponse<PatientResponseDto> createPatient(PatientRequestDto dto) {

        log.info("Creating patient");

        Patient patient = dto.toEntity();

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient created with id {}", savedPatient.getId());

        return ApiResponse.success(
                "Patient created successfully",
                savedPatient.toDto()
        );
    }

    @Override
    public ApiResponse<PatientResponseDto> getPatientById(Long id) {

        log.info("Fetching patient {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found {}", id);
                    return new ResourceNotFoundException("Patient not found with id " + id);
                });

        return ApiResponse.success(
                "Patient fetched successfully",
                patient.toDto()
        );
    }

    @Override
    public ApiResponse<List<PatientResponseDto>> getAllPatients() {

        log.info("Fetching all patients");

        List<PatientResponseDto> patients = patientRepository.findAll()
                .stream()
                .map(Patient::toDto)
                .toList();

        return ApiResponse.success(
                "All patients fetched successfully",
                patients
        );
    }

    @Override
    public ApiResponse<PatientResponseDto> updatePatient(Long id, PatientRequestDto dto) {

        log.info("Updating patient {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setDob(dto.getDob());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setContactNumber(dto.getContactNumber());

        Patient updatedPatient = patientRepository.save(patient);

        log.info("Patient updated {}", id);

        return ApiResponse.success(
                "Patient updated successfully",
                updatedPatient.toDto()
        );
    }

    @Override
    public ApiResponse<Object> deletePatient(Long id) {

        log.info("Deleting patient {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found for delete {}", id);
                    return new ResourceNotFoundException("Patient not found");
                });

        patientRepository.delete(patient);

        return ApiResponse.success(
                "Patient deleted successfully",
                null
        );
    }
}