package com.example.patientservice.dto.request;

import com.example.patientservice.enums.Gender;
import com.example.patientservice.model.Address;
import com.example.patientservice.model.Patient;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequestDto {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Past
    private LocalDate dob;

    private String gender;
    private String bloodGroup;

    @Pattern(regexp = "^[0-9]{10}$")
    private String contactNumber;

    private Address address;

    public Patient toEntity() {
        Address addressEntity = null;
        if (this.address != null) {
            addressEntity = Address.builder()
                    .street(address.getStreet()).city(address.getCity())
                    .state(address.getState()).country(address.getCountry())
                    .zipCode(address.getZipCode()).build();
        }

        // FIX: null-safe gender parsing
        Gender genderEnum = (this.gender != null && !this.gender.isBlank())
                ? Gender.valueOf(this.gender.toUpperCase())
                : Gender.OTHER;

        return Patient.builder()
                .userId(this.userId).firstName(this.firstName)
                .lastName(this.lastName).email(this.email)
                .dob(this.dob).gender(genderEnum)
                .bloodGroup(this.bloodGroup).contactNumber(this.contactNumber)
                .address(addressEntity).build();
    }
}