package com.example.patientservice.model;

import com.example.patientservice.dto.response.AddressResponseDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clinics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Clinic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @NotNull
    @Column(name = "clinic_name", nullable = false, length = 100)
    private String clinicName;

    @NotNull
    @Column(nullable = false, length = 150)
    private String location;

    @NotNull
    @Email
    @Column(name = "contact_email", nullable = false, unique = true)  // FIX: typo "contact_emial"
    private String contactEmail;

    // FIX: Changed from primitive boolean to Boolean wrapper so Lombok generates setIsActive()
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

}