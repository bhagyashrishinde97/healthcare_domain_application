package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDto {
    private Long id;
    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;

    private LocalDate dob;
    private String gender;

    private String bloodGroup;
    private String contactNumber;

    private AddressResponseDto address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
