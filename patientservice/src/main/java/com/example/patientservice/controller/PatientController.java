package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.PatientRequestDto;
import com.example.patientservice.dto.response.PatientResponseDto;
import com.example.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> saveMyProfile(
            @Valid @RequestBody PatientRequestDto dto) {
        log.info("API: Creating/updating patient profile");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createOrUpdateMyProfile(dto));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getMyProfile() {
        log.info("API: Fetching my patient profile");
        return ResponseEntity.ok(patientService.getMyProfile());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getById(
            @PathVariable Long id) {
        log.info("API: Fetching patient by id={}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> getAll() {
        log.info("API: Fetching all patients");
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> searchPatients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        log.info("API: Searching patients with firstName={}, lastName={}, email={}",
                firstName, lastName, email);
        return ResponseEntity.ok(patientService.searchPatients(firstName, lastName, email));
    }
}