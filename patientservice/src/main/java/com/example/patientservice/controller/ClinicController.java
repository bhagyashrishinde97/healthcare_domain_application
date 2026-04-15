package com.example.patientservice.controller;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import com.example.patientservice.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

        log.info(" create clinic");

        return ResponseEntity.ok(clinicService.createClinic(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> getClinicById(
            @PathVariable Long id) {

        log.info(" get clinic {}", id);

        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClinicResponseDto>>> getAllClinics() {

        log.info(" get all clinics");

        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicResponseDto>> updateClinic(
            @PathVariable Long id,
            @Valid @RequestBody ClinicRequestDto dto) {

        log.info(" update clinic {}", id);

        return ResponseEntity.ok(clinicService.updateClinic(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteClinic(
            @PathVariable Long id) {

        log.info(" delete clinic {}", id);

        return ResponseEntity.ok(clinicService.deleteClinic(id));
    }
}

