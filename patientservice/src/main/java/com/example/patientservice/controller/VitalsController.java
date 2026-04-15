package com.example.patientservice.controller;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.VitalsResponseDto;
import com.example.patientservice.service.VitalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vitals")
@RequiredArgsConstructor
@Slf4j
public class VitalsController {

    private final VitalsService vitalsService;

    @PostMapping("/{encounterId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<VitalsResponseDto>> addVitals(
            @PathVariable Long encounterId,
            @Valid @RequestBody VitalsRequestDto dto) {
        log.info("API: Adding vitals for encounterId={}", encounterId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vitalsService.addVitals(encounterId, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<VitalsResponseDto>> getVitalsById(
            @PathVariable Long id) {
        log.info("API: Fetching vitals id={}", id);
        return ResponseEntity.ok(vitalsService.getVitalsById(id));
    }

    @GetMapping("/encounter/{encounterId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<VitalsResponseDto>>> getVitalsByEncounter(
            @PathVariable Long encounterId) {
        log.info("API: Fetching vitals for encounterId={}", encounterId);
        return ResponseEntity.ok(vitalsService.getVitalsByEncounterId(encounterId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<VitalsResponseDto>>> getAllVitals() {
        log.info("API: Fetching all vitals");
        return ResponseEntity.ok(vitalsService.getAllVitals());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<VitalsResponseDto>> updateVitals(
            @PathVariable Long id,
            @Valid @RequestBody VitalsRequestDto dto) {
        log.info("API: Updating vitals id={}", id);
        return ResponseEntity.ok(vitalsService.updateVitals(id, dto));
    }
}