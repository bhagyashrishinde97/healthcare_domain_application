package com.example.patientservice.model;
import com.example.patientservice.dto.AppointmentResponseDto;
import com.example.patientservice.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;
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
    @Column(name="appointment_id",unique = true,nullable = false,updatable = false)
    private UUID appointmentId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
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

    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;
  @PrePersist
  public void generateUUID()
  {
      if(this.appointmentId==null)
      {
          this.appointmentId= UUID.randomUUID();
      }
  }
    public AppointmentResponseDto toDto() {
        return AppointmentResponseDto.builder()
                .id(this.id)
                .appointmentId(this.appointmentId)
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