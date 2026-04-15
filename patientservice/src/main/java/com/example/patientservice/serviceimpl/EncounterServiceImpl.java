package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.EncounterResponseDto;
import com.example.patientservice.enums.AppointmentStatus;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.exception.ValidationException;
import com.example.patientservice.mapper.EncounterMapper;
import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.repository.AppointmentRepository;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.service.EncounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EncounterServiceImpl implements EncounterService {

    private final EncounterRepository encounterRepository;
    private final AppointmentRepository appointmentRepository;
    private final EncounterMapper encounterMapper;

    @Override
    public ApiResponse<EncounterResponseDto> createEncounter(EncounterRequestDto dto) {
        log.info("Service: Creating encounter for appointmentId={}", dto.getAppointmentId());

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new ValidationException("Can only create encounter for confirmed appointments. Current status: " + appointment.getStatus());
        }

        if (encounterRepository.existsByAppointmentId(dto.getAppointmentId())) {
            throw new ValidationException("Encounter already exists for this appointment");
        }

        Encounter encounter = encounterMapper.toEntity(dto);
        encounter.setAppointment(appointment);
        encounter.setEncounterDate(LocalDateTime.now());

        Encounter saved = encounterRepository.save(encounter);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        log.info("Service: Encounter created successfully with id={}", saved.getId());
        return ApiResponse.success("Encounter created successfully", encounterMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<EncounterResponseDto> getEncounterById(UUID encounterId) {
        log.info("Service: Fetching encounter by UUID={}", encounterId);

        Encounter encounter = encounterRepository.findByEncounterId(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with UUID: " + encounterId));

        return ApiResponse.success("Encounter fetched successfully", encounterMapper.toDto(encounter));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<EncounterResponseDto> getEncounterByAppointmentId(Long appointmentId) {
        log.info("Service: Fetching encounter by appointmentId={}", appointmentId);

        Encounter encounter = encounterRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found for appointment id: " + appointmentId));

        return ApiResponse.success("Encounter fetched successfully", encounterMapper.toDto(encounter));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<EncounterResponseDto>> getAllEncounters() {
        log.info("Service: Fetching all encounters");

        List<EncounterResponseDto> encounters = encounterRepository.findAll()
                .stream()
                .map(encounterMapper::toDto)
                .toList();

        return ApiResponse.success("Encounters fetched successfully", encounters);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<EncounterResponseDto>> getEncountersByPatientId(Long patientId) {
        log.info("Service: Fetching encounters for patientId={}", patientId);

        List<EncounterResponseDto> encounters = encounterRepository.findByAppointmentPatientId(patientId)
                .stream()
                .map(encounterMapper::toDto)
                .toList();

        return ApiResponse.success("Patient encounters fetched successfully", encounters);
    }

    @Override
    public ApiResponse<EncounterResponseDto> updateEncounter(Long id, EncounterRequestDto dto) {
        log.info("Service: Updating encounter with id={}", id);

        Encounter encounter = encounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with id: " + id));

        encounterMapper.updateEntity(dto, encounter);
        Encounter updated = encounterRepository.save(encounter);

        log.info("Service: Encounter updated successfully with id={}", updated.getId());
        return ApiResponse.success("Encounter updated successfully", encounterMapper.toDto(updated));
    }
}