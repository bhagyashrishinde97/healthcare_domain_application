package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import com.example.patientservice.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic")
@Slf4j
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClinicResponseDto>> createClinic(
            @Valid @RequestBody ClinicRequestDto dto) {
        log.info("API: Creating clinic with name={}", dto.getClinicName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.createClinic(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> getClinicById(
            @PathVariable Long id) {
        log.info("API: Fetching clinic id={}", id);
        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClinicResponseDto>>> getAllClinics() {
        log.info("API: Fetching all clinics");
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ClinicResponseDto>>> getActiveClinics() {
        log.info("API: Fetching active clinics");
        return ResponseEntity.ok(clinicService.getActiveClinics());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> updateClinic(
            @PathVariable Long id,
            @Valid @RequestBody ClinicRequestDto dto) {
        log.info("API: Updating clinic id={}", id);
        return ResponseEntity.ok(clinicService.updateClinic(id, dto));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> activateClinic(
            @PathVariable Long id) {
        log.info("API: Activating clinic id={}", id);
        return ResponseEntity.ok(clinicService.activateClinic(id));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> deactivateClinic(
            @PathVariable Long id) {
        log.info("API: Deactivating clinic id={}", id);
        return ResponseEntity.ok(clinicService.deactivateClinic(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteClinic(
            @PathVariable Long id) {
        log.info("API: Deleting clinic id={}", id);
        return ResponseEntity.ok(clinicService.deleteClinic(id));
    }
}