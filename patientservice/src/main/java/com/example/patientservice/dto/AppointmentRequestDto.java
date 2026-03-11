package com.example.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto {
 private Long patientId;
 private Long doctorId;
 private Long clinicId;
 private LocalDateTime appointmentTime;
 private String status;
 private String reason;
}
