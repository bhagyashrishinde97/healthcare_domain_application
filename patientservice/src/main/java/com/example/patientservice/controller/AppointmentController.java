package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.AppointmentRequestDto;
import com.example.patientservice.dto.response.AppointmentResponseDto;
import com.example.patientservice.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @Valid @RequestBody AppointmentRequestDto dto) {
        log.info("Creating appointment for patientId={}", dto.getPatientId());
        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(
            @PathVariable UUID appointmentId) {
        log.info("Fetching appointment by UUID={}", appointmentId);
        return ResponseEntity.ok(appointmentService.getAppointmentById(appointmentId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {
        log.info("Fetching all appointments");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // FIX: was @PutMapping("." + "2{id}") — completely broken URL
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDto dto) {
        log.info("Updating appointment id={}", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/internal/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteAppointment(
            @PathVariable Long id) {
        log.info("Deleting appointment id={}", id);
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }
}