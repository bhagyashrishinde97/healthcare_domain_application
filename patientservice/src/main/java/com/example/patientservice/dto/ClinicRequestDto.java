package com.example.patientservice.dto;

import com.example.patientservice.model.Address;
import com.example.patientservice.model.Clinic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClinicRequestDto {
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @NotBlank
    private String clinicName;

    @NotBlank
    private String location;

    @NotBlank
    private String contactEmail;

    private Boolean isActive = true;

    private Address address;

    public Clinic toEntity() {

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
        return Clinic.builder()
                .userId(this.userId)
                .clinicName(this.clinicName)
                .location(this.location)
                .contactEmail(this.contactEmail)
                .isActive(this.isActive)
                .address(addressEntity)
                .build();
    }
}