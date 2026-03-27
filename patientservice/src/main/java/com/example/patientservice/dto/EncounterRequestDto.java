package com.example.patientservice.dto;

import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Encounter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
                .dignosis(this.diagnosis)
                .notes(this.notes)
                .encounterDate(LocalDateTime.now())
                .build();
    }
}
