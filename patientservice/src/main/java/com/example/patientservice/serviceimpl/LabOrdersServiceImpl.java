package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.LabOrder;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.LabOrdersRepository;
import com.example.patientservice.service.LabOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LabOrdersServiceImpl implements LabOrdersService {
    private final LabOrdersRepository labOrdersRepository;
    private final EncounterRepository encounterRepository;

    @Override
    public LabOrderResponseDto addLabOrder(Long encounterId, LabOrderRequestDto dto) {
        log.info("adding lab order for encounterId :{}", encounterId);
        Encounter encounter = encounterRepository.findById(encounterId).orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        LabOrder labOrder = dto.toEntity(encounter);
        LabOrder saved = labOrdersRepository.save(labOrder);
        log.info("lab order saved with id :{}", saved.getId());
        return saved.toDto();
    }
}