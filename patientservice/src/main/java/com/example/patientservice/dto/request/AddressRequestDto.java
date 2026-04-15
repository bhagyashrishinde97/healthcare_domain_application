package com.example.patientservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDto {

    @NotBlank @Size(max = 200)
    private String street;

    @NotBlank @Size(max = 100)
    private String city;

    @NotBlank @Size(max = 100)
    private String state;

    @NotBlank @Size(max = 100)
    private String country;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String zipCode;
}