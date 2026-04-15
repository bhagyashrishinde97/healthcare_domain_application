package com.example.patientservice.model;

import com.example.patientservice.dto.response.AddressResponseDto;
import com.example.patientservice.dto.response.DoctorResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "doctors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "license_number")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "specialization", length = 50, nullable = false)
    private String specialization;

    @NotBlank(message = "License number is required")
    @Size(min = 6, max = 6, message = "License number must be 6 characters")
    @Pattern(regexp = "^[A-Z][0-9]{5}$", message = "License must start with a capital letter followed by 5 digits")
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "department", length = 50)
    private String department;

    // FIX: Changed from primitive boolean to Boolean wrapper for null-safety
    @Column(name = "availability_status")
    private Boolean availabilityStatus = true;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();


}