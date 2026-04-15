package com.example.patientservice.controller;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.enums.LabStatus;
import com.example.patientservice.service.LabOrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lab_orders")
@RequiredArgsConstructor
@Slf4j
public class LabOrdersController {

    private final LabOrdersService labOrdersService;

    @PostMapping("/{encounterId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<LabOrderResponseDto>> addLabOrder(
            @PathVariable Long encounterId,
            @Valid @RequestBody LabOrderRequestDto dto) {
        log.info("API: Adding lab order for encounterId={}, test={}", encounterId, dto.getTestName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labOrdersService.addLabOrder(encounterId, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<LabOrderResponseDto>> getLabOrderById(
            @PathVariable Long id) {
        log.info("API: Fetching lab order id={}", id);
        return ResponseEntity.ok(labOrdersService.getLabOrderById(id));
    }

    @GetMapping("/encounter/{encounterId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<LabOrderResponseDto>>> getLabOrdersByEncounter(
            @PathVariable Long encounterId) {
        log.info("API: Fetching lab orders for encounterId={}", encounterId);
        return ResponseEntity.ok(labOrdersService.getLabOrdersByEncounterId(encounterId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<LabOrderResponseDto>>> getAllLabOrders() {
        log.info("API: Fetching all lab orders");
        return ResponseEntity.ok(labOrdersService.getAllLabOrders());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<LabOrderResponseDto>> updateLabOrderStatus(
            @PathVariable Long id,
            @RequestParam LabStatus status) {
        log.info("API: Updating lab order status id={}, status={}", id, status);
        return ResponseEntity.ok(labOrdersService.updateLabOrderStatus(id, status));
    }

    @PatchMapping("/{id}/result")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<LabOrderResponseDto>> updateLabOrderResult(
            @PathVariable Long id,
            @RequestParam String result) {
        log.info("API: Updating lab order result id={}", id);
        return ResponseEntity.ok(labOrdersService.updateLabOrderResult(id, result));
    }
}