package com.example.patientservice.dto.request;

import com.example.patientservice.model.Address;
import com.example.patientservice.model.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "userName is required")
    private String userName;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Digits(integer = 15, fraction = 0)
    private Long contactNumber;

    private AddressRequestDto address;
    private String bloodGroup;
    private boolean isActive = true;
    private Set<String> rolesSet;

    public User toEntity() {
        Address addressEntity = null;
        if (this.address != null) {
            addressEntity = Address.builder()
                    .street(address.getStreet()).city(address.getCity())
                    .state(address.getState()).country(address.getCountry())
                    .zipCode(address.getZipCode()).build();
        }
        return User.builder()
                .userName(this.userName).email(this.email)
                .password(this.password).bloodGroup(this.bloodGroup)
                .contactNumber(this.contactNumber != null ? String.valueOf(this.contactNumber) : null)
                .isActive(this.isActive).address(addressEntity)
                .build();
    }
}