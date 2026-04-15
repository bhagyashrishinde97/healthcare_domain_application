package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.DoctorRequestDto;
import com.example.patientservice.dto.response.DoctorResponseDto;
import com.example.patientservice.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDto>> createDoctor(
            @Valid @RequestBody DoctorRequestDto dto) {
        log.info("API: Creating doctor with license={}", dto.getLicenseNumber());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.createDoctor(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> getDoctorById(
            @PathVariable Long id) {
        log.info("API: Fetching doctor id={}", id);
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDto>>> getAllDoctors() {
        log.info("API: Fetching all doctors");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DoctorResponseDto>>> getAvailableDoctors() {
        log.info("API: Fetching available doctors");
        return ResponseEntity.ok(doctorService.getAvailableDoctors());
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<ApiResponse<List<DoctorResponseDto>>> getDoctorsBySpecialization(
            @PathVariable String specialization) {
        log.info("API: Fetching doctors by specialization={}", specialization);
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDto dto) {
        log.info("API: Updating doctor id={}", id);
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto));
    }

    @PatchMapping("/{id}/availability")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> toggleAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        log.info("API: Toggling doctor availability id={}, available={}", id, available);
        return ResponseEntity.ok(doctorService.toggleAvailability(id, available));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteDoctor(
            @PathVariable Long id) {
        log.info("API: Deleting doctor id={}", id);
        return ResponseEntity.ok(doctorService.deleteDoctor(id));
    }
}