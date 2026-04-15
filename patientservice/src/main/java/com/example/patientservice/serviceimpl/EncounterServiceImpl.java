package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.EncounterResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.repository.AppointmentRepository;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.service.EncounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EncounterServiceImpl implements EncounterService {

    private final EncounterRepository encounterRepository;
    // FIX: inject AppointmentRepository so we fetch the real managed entity
    private final AppointmentRepository appointmentRepository;

    @Override
    public EncounterResponseDto createEncounter(EncounterRequestDto dto) {
        log.info("Creating encounter for appointmentId={}", dto.getAppointmentId());

        // FIX: was `new Appointment(); setId(...)` — causes FK constraint violation
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id=" + dto.getAppointmentId()));

        Encounter encounter = dto.toEntity(appointment);
        Encounter saved = encounterRepository.save(encounter);
        log.info("Encounter created with id={}", saved.getId());
        return saved.toDto();
    }
}