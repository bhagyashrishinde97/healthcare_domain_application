package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.PrescriptionResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Prescription;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.PrescriptionRepository;
import com.example.patientservice.service.PrescriptionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionsService {

    private final PrescriptionRepository prescriptionRepository;
    private final EncounterRepository encounterRepository;

    @Override
    public PrescriptionResponseDto addPrescription(Long encounterId, PrescriptionRequestDto dto) {

        log.info("Start: Adding prescription for encounterId={}", encounterId);

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> {
                    log.error("Encounter not found with id={}", encounterId);
                    return new ResourceNotFoundException("Encounter not found");
                });
        Prescription prescription = dto.toEntity(encounter);
        Prescription saved = prescriptionRepository.save(prescription);
        log.info("Service Success: Prescription saved with id={}", saved.getId());
        return saved.toDto();
    }
}