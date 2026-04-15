package com.example.patientservice.controller;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.EncounterResponseDto;
import com.example.patientservice.service.EncounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/encounter")
@RequiredArgsConstructor
@Slf4j
public class EncounterController {

    private final EncounterService encounterService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<EncounterResponseDto>> createEncounter(
            @Valid @RequestBody EncounterRequestDto dto) {
        log.info("API: Creating encounter for appointmentId={}", dto.getAppointmentId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(encounterService.createEncounter(dto));
    }

    @GetMapping("/{encounterId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncounterResponseDto>> getEncounterById(
            @PathVariable UUID encounterId) {
        log.info("API: Fetching encounter by UUID={}", encounterId);
        return ResponseEntity.ok(encounterService.getEncounterById(encounterId));
    }

    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncounterResponseDto>> getEncounterByAppointmentId(
            @PathVariable Long appointmentId) {
        log.info("API: Fetching encounter by appointmentId={}", appointmentId);
        return ResponseEntity.ok(encounterService.getEncounterByAppointmentId(appointmentId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EncounterResponseDto>>> getAllEncounters() {
        log.info("API: Fetching all encounters");
        return ResponseEntity.ok(encounterService.getAllEncounters());
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EncounterResponseDto>>> getEncountersByPatientId(
            @PathVariable Long patientId) {
        log.info("API: Fetching encounters for patientId={}", patientId);
        return ResponseEntity.ok(encounterService.getEncountersByPatientId(patientId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<EncounterResponseDto>> updateEncounter(
            @PathVariable Long id,
            @Valid @RequestBody EncounterRequestDto dto) {
        log.info("API: Updating encounter id={}", id);
        return ResponseEntity.ok(encounterService.updateEncounter(id, dto));
    }
}