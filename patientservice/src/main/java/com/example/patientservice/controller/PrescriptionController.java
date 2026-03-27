package com.example.patientservice.controller;

import com.example.patientservice.dto.PrescriptionRequestDto;
import com.example.patientservice.dto.PrescriptionResponseDto;
import com.example.patientservice.service.PrescriptionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
@Slf4j
public class PrescriptionController {

    private final PrescriptionsService service;

    @PostMapping("/{encounterId}")
    public ResponseEntity<PrescriptionResponseDto> add(
            @PathVariable Long encounterId,
            @RequestBody PrescriptionRequestDto dto) {
        log.info("API Call: Add Prescription | encounterId={} | medicineName={}",
                encounterId, dto.getMedicineName());
        PrescriptionResponseDto responseDto = service.addPrescription(encounterId, dto);
        log.info("api success: Prescription created | id={}|encounterId={}", responseDto.getId(), encounterId);
        return ResponseEntity.ok(responseDto);
    }
}
