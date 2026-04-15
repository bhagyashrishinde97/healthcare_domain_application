package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.VitalsResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.mapper.VitalsMapper;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Vitals;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.VitalsRepository;
import com.example.patientservice.service.VitalsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VitalsServiceImpl implements VitalsService {

    private final VitalsRepository vitalsRepository;
    private final EncounterRepository encounterRepository;
    private final VitalsMapper vitalsMapper;

    @Override
    public ApiResponse<VitalsResponseDto> addVitals(Long encounterId, VitalsRequestDto dto) {
        log.info("Service: Adding vitals for encounterId={}", encounterId);

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with id: " + encounterId));

        Vitals vitals = vitalsMapper.toEntity(dto);
        vitals.setEncounter(encounter);

        Vitals saved = vitalsRepository.save(vitals);

        log.info("Service: Vitals saved successfully with id={}", saved.getId());
        return ApiResponse.success("Vitals created successfully", vitalsMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VitalsResponseDto> getVitalsById(Long id) {
        log.info("Service: Fetching vitals by id={}", id);

        Vitals vitals = vitalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found with id: " + id));

        return ApiResponse.success("Vitals fetched successfully", vitalsMapper.toDto(vitals));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<VitalsResponseDto>> getVitalsByEncounterId(Long encounterId) {
        log.info("Service: Fetching vitals for encounterId={}", encounterId);

        if (!encounterRepository.existsById(encounterId)) {
            throw new ResourceNotFoundException("Encounter not found with id: " + encounterId);
        }

        List<VitalsResponseDto> vitalsList = vitalsRepository.findByEncounterId(encounterId)
                .stream()
                .map(vitalsMapper::toDto)
                .toList();

        return ApiResponse.success("Vitals fetched successfully", vitalsList);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<VitalsResponseDto>> getAllVitals() {
        log.info("Service: Fetching all vitals");

        List<VitalsResponseDto> vitalsList = vitalsRepository.findAll()
                .stream()
                .map(vitalsMapper::toDto)
                .toList();

        return ApiResponse.success("All vitals fetched successfully", vitalsList);
    }

    @Override
    public ApiResponse<VitalsResponseDto> updateVitals(Long id, VitalsRequestDto dto) {
        log.info("Service: Updating vitals with id={}", id);

        Vitals vitals = vitalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found with id: " + id));

        vitals.setHeight(dto.getHeight());
        vitals.setWeight(dto.getWeight());
        vitals.setPulseRate(dto.getPulseRate());
        vitals.setTemperature(dto.getTemperature());
        vitals.setBloodPressure(dto.getBloodPressure());

        Vitals updated = vitalsRepository.save(vitals);

        log.info("Service: Vitals updated successfully");
        return ApiResponse.success("Vitals updated successfully", vitalsMapper.toDto(updated));
    }
}