package com.example.patientservice.dto.request;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {

    private String userName;

    @Digits(integer = 15, fraction = 0)
    private String contactNumber;

    private String bloodGroup;

    private AddressRequestDto address;

}
