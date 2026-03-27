package com.example.patientservice.dto;

import com.example.patientservice.enums.LabStatus;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.LabOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabOrderRequestDto {

    @NotBlank(message = "Test name is required")
    private String testName;

    @NotBlank(message = "Test description is required")
    private String testDescription;

    @NotNull(message = "Lab status is required")
    private LabStatus labStatus;

    @NotBlank(message = "Result is required")
    private String result;

    public LabOrder toEntity(Encounter encounter) {
        return LabOrder.builder()
                .testName(this.testName)
                .testDescription(this.testDescription)
                .labStatus(this.labStatus)
                .result(this.result)
                .encounter(encounter)
                .build();
    }
}