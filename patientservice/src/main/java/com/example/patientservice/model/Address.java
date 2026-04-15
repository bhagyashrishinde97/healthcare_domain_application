package com.example.patientservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Address {

    @NotBlank(message = "Street is required")
    @Size(max = 200)
    @Column(name = "street", length = 200)
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 100)
    @Column(name = "state", length = 100)
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    @Column(name = "country", length = 100)
    private String country;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Zip code must be 6 digits starting with 1-9")
    @Column(name = "zip_code", length = 6)
    private String zipCode;
}