package com.example.patientservice.controller;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.DoctorRequestDto;
import com.example.patientservice.dto.DoctorResponseDto;
import com.example.patientservice.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        log.info("API request: create doctor");
        return ResponseEntity.ok(doctorService.createDoctor(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> getDoctorById(
            @PathVariable Long id) {
        log.info("API request: get doctor {}", id);
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDto>>> getAllDoctors() {
        log.info("API request: get all doctors");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDto dto) {
        log.info("API request: update doctor {}", id);
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteDoctor(
            @PathVariable Long id)

    {
        log.info("API request: delete doctor {}", id);
        return ResponseEntity.ok(doctorService.deleteDoctor(id));
    }
}

