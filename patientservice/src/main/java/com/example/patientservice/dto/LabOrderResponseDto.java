package com.example.patientservice.dto;

import com.example.patientservice.enums.LabStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabOrderResponseDto {
    private Long id;
    private String testName;
    private String testDescription;
    private String result;
    private Long encounterId;
    private LabStatus labStatus;

}
