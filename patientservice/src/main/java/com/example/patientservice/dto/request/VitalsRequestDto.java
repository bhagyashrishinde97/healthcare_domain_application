package com.example.patientservice.dto.request;

import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.Vitals;
import lombok.*;

@Data
@Builder
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
                .height(this.height).weight(this.weight)
                .temperature(this.temperature).pulseRate(this.pulseRate)
                .bloodPressure(this.bloodPressure).encounter(encounter)
                .build();
    }
}