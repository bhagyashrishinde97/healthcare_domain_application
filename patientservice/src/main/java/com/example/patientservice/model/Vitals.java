package com.example.patientservice.model;
import com.example.patientservice.dto.VitalsResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="vitals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vitals  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @ManyToOne
    @JoinColumn(name="encounter_id",nullable = false)
    private Encounter encounter;
    private double height;
    private double weight;
    @Column(length = 20)
    private String testName;
    private String testDescription;
    private int pulseRate;
    private Double temperature;
    private String bloodPressure;
    public VitalsResponseDto toDto() {
        return VitalsResponseDto.builder()
                .id(this.id)
                .height(this.height)
                .weight(this.weight)
                .pulseRate(this.pulseRate)
                .temperature(this.temperature)
                .bloodPressure(this.bloodPressure)
                .encounterId(encounter != null ? encounter.getId() : null)
                .build();
    }
}
