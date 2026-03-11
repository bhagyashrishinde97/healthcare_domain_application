package com.example.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicResponseDto {
    private Long id;
    private UUID userId;
    private String clinicName;
    private String location;
    private String contactEmail;
    private Boolean isActive;

    private AddressResponseDto address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
