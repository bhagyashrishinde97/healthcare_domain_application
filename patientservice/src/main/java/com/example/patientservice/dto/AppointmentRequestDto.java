package com.example.patientservice.dto;

import com.example.patientservice.enums.AppointmentStatus;
import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Clinic;
import com.example.patientservice.model.Doctor;
import com.example.patientservice.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
                .patient(patient)
                .doctor(doctor)
                .clinic(clinic)
                .appointmentDate(this.appointmentDate)
                .reason(this.reason)
                .status(this.status)
                .build();
    }
}

