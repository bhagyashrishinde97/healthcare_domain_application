package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.AppointmentRequestDto;
import com.example.patientservice.dto.response.AppointmentResponseDto;
import com.example.patientservice.enums.AppointmentStatus;
import com.example.patientservice.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {
        log.info("Fetching all appointments");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByPatient(
            @PathVariable Long patientId) {
        log.info("Fetching appointments for patientId={}", patientId);
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {
        log.info("Fetching appointments for doctorId={}", doctorId);
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByStatus(
            @PathVariable AppointmentStatus status) {
        log.info("Fetching appointments with status={}", status);
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }

    @GetMapping("/doctor/{doctorId}/availability")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getDoctorAvailability(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching doctor availability for doctorId={}", doctorId);
        return ResponseEntity.ok(appointmentService.getDoctorAvailableSlots(doctorId, startDate, endDate));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDto dto) {
        log.info("Updating appointment id={}", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @PatchMapping("/{id}/confirm")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> confirmAppointment(
            @PathVariable Long id) {
        log.info("Confirming appointment id={}", id);
        return ResponseEntity.ok(appointmentService.confirmAppointment(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> cancelAppointment(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        log.info("Cancelling appointment id={}", id);
        return ResponseEntity.ok(appointmentService.cancelAppointment(id, reason));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteAppointment(@PathVariable Long id) {
        log.info("Deleting appointment id={}", id);
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }
}