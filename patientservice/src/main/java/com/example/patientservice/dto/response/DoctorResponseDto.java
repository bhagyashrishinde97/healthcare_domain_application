package com.example.patientservice.dto.response;

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
public class DoctorResponseDto {
    private Long id;
    private UUID userId;

    private String firstName;
    private String lastName;
    private String specialization;
    private String licenseNumber;
    private String department;
    private Boolean availabilityStatus;
    private String contactNumber;
    private AddressResponseDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


