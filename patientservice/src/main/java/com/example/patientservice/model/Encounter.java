package com.example.patientservice.model;

import com.example.patientservice.dto.response.EncounterResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "encounters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Encounter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_id", nullable = false, unique = true, updatable = false)
    private UUID encounterId;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)  // FIX: typo "appoinment_id"
    private Appointment appointment;

    @Column(length = 500)
    private String diagnosis;   // FIX: was "dignosis"

    @Column(length = 1000)
    private String notes;

    @Column(name = "encounter_date", nullable = false)
    private LocalDateTime encounterDate;

    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabOrder> labOrders;

    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vitals> vitalsList;

    @PrePersist
    public void generateUUID() {
        if (this.encounterId == null) {
            this.encounterId = UUID.randomUUID();
        }
    }

    public EncounterResponseDto toDto() {
        return EncounterResponseDto.builder()
                .id(this.id).encounterId(this.encounterId)
                .appointmentId(this.appointment.getId())
                .diagnosis(this.diagnosis)
                .notes(this.notes).encounterDate(this.encounterDate)
                .createdAt(this.getCreatedAt()).updatedAt(this.getUpdatedAt())
                .build();
    }
}