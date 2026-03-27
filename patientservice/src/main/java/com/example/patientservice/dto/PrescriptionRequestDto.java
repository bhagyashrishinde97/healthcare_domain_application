package com.example.patientservice.dto;

import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionRequestDto {

    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;

    public Prescription toEntity(Encounter encounter) {
        return Prescription.builder()
                .medicineName(this.medicineName)
                .dosage(this.dosage)
                .frequency(this.frequency)
                .duration(this.duration)
                .instructions(this.instructions)
                .encounter(encounter)
                .build();
    }
}