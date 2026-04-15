package com.example.patientservice.controller;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.VitalsResponseDto;
import com.example.patientservice.service.VitalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vitals")
@RequiredArgsConstructor
@Slf4j
public class VitalsController {

    private final VitalsService vitalsService;

    @PostMapping("/{encounterId}")
    public ResponseEntity<VitalsResponseDto> addVitals(
            @PathVariable Long encounterId,
            @Valid @RequestBody VitalsRequestDto dto) {
        log.info("Adding vitals for encounterId={}", encounterId);
        VitalsResponseDto responseDto = vitalsService.addVitals(encounterId, dto);
        log.info("Vitals created with id={}", responseDto.getId());   // FIX: typos fixed
        return ResponseEntity.ok(responseDto);
    }
}