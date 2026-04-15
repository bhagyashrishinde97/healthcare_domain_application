package com.example.patientservice.controller;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.PrescriptionResponseDto;
import com.example.patientservice.service.PrescriptionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
@Slf4j
public class PrescriptionController {

    private final PrescriptionsService service;

    @PostMapping("/{encounterId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> add(
            @PathVariable Long encounterId,
            @Valid @RequestBody PrescriptionRequestDto dto) {
        log.info("API: Adding prescription for encounterId={}, medicine={}",
                encounterId, dto.getMedicineName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addPrescription(encounterId, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> getPrescriptionById(
            @PathVariable Long id) {
        log.info("API: Fetching prescription id={}", id);
        return ResponseEntity.ok(service.getPrescriptionById(id));
    }

    @GetMapping("/encounter/{encounterId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PrescriptionResponseDto>>> getPrescriptionsByEncounter(
            @PathVariable Long encounterId) {
        log.info("API: Fetching prescriptions for encounterId={}", encounterId);
        return ResponseEntity.ok(service.getPrescriptionsByEncounterId(encounterId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PrescriptionResponseDto>>> getAllPrescriptions() {
        log.info("API: Fetching all prescriptions");
        return ResponseEntity.ok(service.getAllPrescriptions());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionRequestDto dto) {
        log.info("API: Updating prescription id={}", id);
        return ResponseEntity.ok(service.updatePrescription(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Object>> deletePrescription(
            @PathVariable Long id) {
        log.info("API: Deleting prescription id={}", id);
        return ResponseEntity.ok(service.deletePrescription(id));
    }
}