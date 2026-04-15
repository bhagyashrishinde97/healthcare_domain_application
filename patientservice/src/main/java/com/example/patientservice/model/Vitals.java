package com.example.patientservice.model;

import com.example.patientservice.dto.response.VitalsResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vitals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    private Double height;
    private Double weight;
    private Integer pulseRate;
    private Double temperature;

    @Column(length = 20)
    private String bloodPressure;

    public VitalsResponseDto toDto() {
        return VitalsResponseDto.builder()
                .id(this.id).height(this.height).weight(this.weight)
                .pulseRate(this.pulseRate).temperature(this.temperature)
                .bloodPressure(this.bloodPressure)
                .encounterId(encounter != null ? encounter.getId() : null)
                .build();
    }
}