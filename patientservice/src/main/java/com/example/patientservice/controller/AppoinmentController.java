package com.example.patientservice.controller;

import com.example.patientservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointment")
@Slf4j
public class AppoinmentController {
    private final AppointmentService appointmentService;
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @Valid @RequestBody AppointmentRequestDto dto) {

        log.info("API request: create appointment");

        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(
            @PathVariable Long id) {

        log.info("API request: get appointment {}", id);

        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {

        log.info("API request: get all appointments");

        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDto dto) {

        log.info("API request: update appointment {}", id);

        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteAppointment(
            @PathVariable Long id) {

        log.info("API request: delete appointment {}", id);

        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }
}





}
