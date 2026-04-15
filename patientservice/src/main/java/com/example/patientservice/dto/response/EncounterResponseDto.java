package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncounterResponseDto {
    private Long id;
    private UUID encounterId;
    private Long appointmentId;
    private String diagnosis;
    private String notes;
    private LocalDateTime encounterDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
