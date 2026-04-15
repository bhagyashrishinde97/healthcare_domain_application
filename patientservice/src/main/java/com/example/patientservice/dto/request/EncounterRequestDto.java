package com.example.patientservice.dto.request;

import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Encounter;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncounterRequestDto {

    private Long appointmentId;
    private String diagnosis;
    private String notes;

    public Encounter toEntity(Appointment appointment) {
        return Encounter.builder()
                .appointment(appointment)
                .diagnosis(this.diagnosis)   // FIX: aligned with fixed entity field name
                .notes(this.notes)
                .encounterDate(LocalDateTime.now())
                .build();
    }
}