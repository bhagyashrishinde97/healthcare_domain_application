package com.example.patientservice.controller;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.AppointmentRequestDto;
import com.example.patientservice.dto.AppointmentResponseDto;
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

        ApiResponse<AppointmentResponseDto> response =
                appointmentService.createAppointment(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(
            @PathVariable UUID appointmentId) {

        log.info("Fetching appointment by UUID={}", appointmentId);

        ApiResponse<AppointmentResponseDto> response =
                appointmentService.getAppointmentById(appointmentId);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {

        log.info("Fetching all appointments");

        ApiResponse<List<AppointmentResponseDto>> response =
                appointmentService.getAllAppointments();

        return ResponseEntity.ok(response);
    }


    @PutMapping("." +
            "2{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDto dto) {

        log.info("Updating appointment with DB id={}", id);

        ApiResponse<AppointmentResponseDto> response =
                appointmentService.updateAppointment(id, dto);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/internal/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteAppointment(
            @PathVariable Long id) {

        log.info("Deleting appointment with DB id={}", id);

        ApiResponse<Object> response =
                appointmentService.deleteAppointment(id);

        return ResponseEntity.ok(response);
    }
}