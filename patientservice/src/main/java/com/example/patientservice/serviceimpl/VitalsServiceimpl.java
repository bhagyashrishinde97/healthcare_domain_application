package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.VitalsResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Vitals;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.VitalsRepository;
import com.example.patientservice.service.VitalsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VitalsServiceimpl implements VitalsService {
    private final VitalsRepository vitalsRepository;
    private final EncounterRepository encounterRepository;

    @Override
    public VitalsResponseDto addVitals(Long encounterId, VitalsRequestDto dto) {
        log.info("adding vitals for encounterId={}", encounterId);
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> {
                    log.error("Encounter not found with id={}", encounterId);
                    return new ResourceNotFoundException("Encounter not found");
                });
        Vitals vitals = dto.toEntity(encounter);
        Vitals saved = vitalsRepository.save(vitals);
        log.info("Service Success:Vitals saved with id={}", saved.getId());
        return saved.toDto();
    }
}
