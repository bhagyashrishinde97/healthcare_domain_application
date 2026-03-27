package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.EncounterRequestDto;
import com.example.patientservice.dto.EncounterResponseDto;
import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Encounter;
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

    @Override
    public EncounterResponseDto createEncounter(EncounterRequestDto dto) {
        log.info("creating encounter for appointmentId:{}", dto.getAppointmentId());
        Appointment appointment = new Appointment();
        appointment.setId(dto.getAppointmentId());
        Encounter encounter = dto.toEntity(appointment);
        Encounter saved = encounterRepository.save(encounter);
        log.info("encounter created with id:{}", saved.getId());
        return saved.toDto();
    }
}
