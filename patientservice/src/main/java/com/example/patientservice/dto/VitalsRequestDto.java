package com.example.patientservice.dto;

import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Vitals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VitalsRequestDto {
    private Double height;
    private Double weight;
    private Double temperature;
    private Integer pulseRate;
    private String bloodPressure;

    public Vitals toEntity(Encounter encounter) {
        return Vitals.builder()
                .height(this.height)
                .weight(this.weight)
                .bloodPressure(this.bloodPressure)
                .temperature(this.temperature)
                .pulseRate(this.pulseRate)
                .encounter(encounter)
                .build();
    }
}
