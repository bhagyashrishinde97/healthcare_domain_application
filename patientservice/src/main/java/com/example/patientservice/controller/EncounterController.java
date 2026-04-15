package com.example.patientservice.controller;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.EncounterResponseDto;
import com.example.patientservice.service.EncounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/encounter")
@RequiredArgsConstructor
@Slf4j
public class EncounterController {
    private final EncounterService encounterService;

    @PostMapping
    public ResponseEntity<EncounterResponseDto> createEncounter(@RequestBody EncounterRequestDto dto) {
        log.info("API Call : Create Encounter for appointmentId={}", dto.getAppointmentId());
        EncounterResponseDto response = encounterService.createEncounter(dto);
        log.info("api response: Encounter Created with Id={}", response.getEncounterId());
        return ResponseEntity.ok(response);
    }
}
