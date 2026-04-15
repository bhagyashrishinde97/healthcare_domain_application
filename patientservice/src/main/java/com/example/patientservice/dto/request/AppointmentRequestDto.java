package com.example.patientservice.dto.request;

import com.example.patientservice.enums.AppointmentStatus;
import com.example.patientservice.model.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto {

    private Long patientId;
    private Long doctorId;
    private Long clinicId;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private String reason;

    public Appointment toEntity(Patient patient, Doctor doctor, Clinic clinic) {
        return Appointment.builder()
                .patient(patient).doctor(doctor).clinic(clinic)
                .appointmentDate(this.appointmentDate)
                .reason(this.reason).status(this.status)
                .build();
    }
}