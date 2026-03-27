package com.example.patientservice.model;

import com.example.patientservice.dto.AddressResponseDto;
import com.example.patientservice.dto.PatientResponseDto;
import com.example.patientservice.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;
    @NotNull
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @NotNull
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @NotNull
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dob;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    public PatientResponseDto toDto() {

        AddressResponseDto addressDto = null;

        if (this.address != null) {
            addressDto = AddressResponseDto.builder()
                    .street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .country(address.getCountry())
                    .zipCode(address.getZipCode())
                    .build();
        }

        return PatientResponseDto.builder()
                .id(this.id)
                .userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .dob(this.dob)
                .gender(this.gender != null ? this.gender.name() : null)
                .bloodGroup(this.bloodGroup)
                .contactNumber(this.contactNumber)
                .address(addressDto)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}