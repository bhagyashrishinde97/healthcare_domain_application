package com.example.patientservice.model;

import com.example.patientservice.dto.response.PrescriptionResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;

    @Column(length = 50)
    private String dosage;

    @Column(length = 50)
    private String frequency;

    private String duration;

    @Column(length = 500)
    private String instructions;

    public PrescriptionResponseDto toDto() {
        return PrescriptionResponseDto.builder()
                .id(this.id).medicineName(this.medicineName)
                .dosage(this.dosage).frequency(this.frequency)
                .duration(this.duration).instructions(this.instructions)
                .encounterId(encounter != null ? encounter.getId() : null)
                .build();
    }
}