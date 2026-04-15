package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.PrescriptionResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.mapper.PrescriptionMapper;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Prescription;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.PrescriptionRepository;
import com.example.patientservice.service.PrescriptionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrescriptionServiceImpl implements PrescriptionsService {

    private final PrescriptionRepository prescriptionRepository;
    private final EncounterRepository encounterRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public ApiResponse<PrescriptionResponseDto> addPrescription(Long encounterId, PrescriptionRequestDto dto) {
        log.info("Service: Adding prescription for encounterId={}, medicine={}", encounterId, dto.getMedicineName());

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with id: " + encounterId));

        Prescription prescription = prescriptionMapper.toEntity(dto);
        prescription.setEncounter(encounter);

        Prescription saved = prescriptionRepository.save(prescription);

        log.info("Service: Prescription saved successfully with id={}", saved.getId());
        return ApiResponse.success("Prescription created successfully", prescriptionMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PrescriptionResponseDto> getPrescriptionById(Long id) {
        log.info("Service: Fetching prescription by id={}", id);

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));

        return ApiResponse.success("Prescription fetched successfully", prescriptionMapper.toDto(prescription));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<PrescriptionResponseDto>> getPrescriptionsByEncounterId(Long encounterId) {
        log.info("Service: Fetching prescriptions for encounterId={}", encounterId);

        if (!encounterRepository.existsById(encounterId)) {
            throw new ResourceNotFoundException("Encounter not found with id: " + encounterId);
        }

        List<PrescriptionResponseDto> prescriptions = prescriptionRepository.findByEncounterId(encounterId)
                .stream()
                .map(prescriptionMapper::toDto)
                .toList();

        return ApiResponse.success("Prescriptions fetched successfully", prescriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<PrescriptionResponseDto>> getAllPrescriptions() {
        log.info("Service: Fetching all prescriptions");

        List<PrescriptionResponseDto> prescriptions = prescriptionRepository.findAll()
                .stream()
                .map(prescriptionMapper::toDto)
                .toList();

        return ApiResponse.success("Prescriptions fetched successfully", prescriptions);
    }

    @Override
    public ApiResponse<PrescriptionResponseDto> updatePrescription(Long id, PrescriptionRequestDto dto) {
        log.info("Service: Updating prescription with id={}", id);

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));

        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setFrequency(dto.getFrequency());
        prescription.setDuration(dto.getDuration());
        prescription.setInstructions(dto.getInstructions());

        Prescription updated = prescriptionRepository.save(prescription);

        log.info("Service: Prescription updated successfully");
        return ApiResponse.success("Prescription updated successfully", prescriptionMapper.toDto(updated));
    }

    @Override
    public ApiResponse<Object> deletePrescription(Long id) {
        log.info("Service: Deleting prescription with id={}", id);

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));

        prescriptionRepository.delete(prescription);

        log.info("Service: Prescription deleted successfully");
        return ApiResponse.successMessage("Prescription deleted successfully");
    }
}