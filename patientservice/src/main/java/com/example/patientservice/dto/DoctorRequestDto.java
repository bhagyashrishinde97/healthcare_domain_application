package com.example.patientservice.dto;

import com.example.patientservice.model.Address;
import com.example.patientservice.model.Doctor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRequestDto {
    @Column(name = "userId", nullable = false, unique = true)
    private UUID userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String specialization;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    private String department;

    private Boolean availabilityStatus = true;

    private String contactNumber;
    private Address address;

    public Doctor toEntity() {
        Address addressEntity = null;
        if (this.address != null) {
            addressEntity = Address.builder()
                    .street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .country(address.getCountry())
                    .zipCode(address.getZipCode())
                    .build();
        }
        return Doctor.builder().userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .specialization(this.specialization)
                .licenseNumber(this.licenseNumber)
                .department(this.department)
                .availabilityStatus(this.availabilityStatus)
                .contactNumber(this.contactNumber)
                .address(addressEntity)
                .build();
    }
}
