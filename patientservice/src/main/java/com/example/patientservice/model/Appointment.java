package com.example.patientservice.model;

import com.example.patientservice.dto.AppointmentResponseDto;
import com.example.patientservice.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @NotNull
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(length =255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private AppointmentStatus status;

    public AppointmentResponseDto toDto() {
        return AppointmentResponseDto.builder()
                .id(this.id)
                .patientId(this.patient.getId())
                .doctorId(this.doctor.getId())
                .clinicId(this.clinic.getId())
                .appointmentDate(this.appointmentDate)
                .reason(this.reason)
                .status(this.status)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}