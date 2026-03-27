package com.example.patientservice.model;

import com.example.patientservice.dto.EncounterResponseDto;
import com.example.patientservice.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="encounters")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Encounter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "encounter_id"
            , nullable = false, unique = true, updatable = false)
    private UUID encounterId;
    @OneToOne
    @JoinColumn(name = "appoinment_id", nullable = false, unique = true)
    private Appointment appointment;
    @Column(length = 500)
    private String dignosis;
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
                .id(this.id)
                .encounterId(this.encounterId)
                .appointmentId(this.appointment.getId())
                .diagnosis(this.dignosis)
                .notes(this.notes)
                .encounterDate(this.encounterDate)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}