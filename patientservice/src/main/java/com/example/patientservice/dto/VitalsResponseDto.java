package com.example.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalsResponseDto {
    private Long id;
    private Double height;
    private Double weight;
    private Integer pulseRate;
    private Double temperature;
    private String bloodPressure;
    private Long encounterId;
}
