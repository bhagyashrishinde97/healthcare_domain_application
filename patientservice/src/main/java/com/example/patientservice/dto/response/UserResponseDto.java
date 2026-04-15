package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private UUID id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String bloodGroup;
    private boolean isActive;
    private AddressResponseDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDto toDto() {
        AddressResponseDto addressDto = null;
        if (this.address != null) {
            addressDto = AddressResponseDto.builder()
                    .street(address.getStreet()).city(address.getCity())
                    .state(address.getState()).country(address.getCountry())
                    .zipCode(address.getZipCode()).build();
        }
        return UserResponseDto.builder()
                .id(this.id)
                .userName(this.userName)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .contactNumber(this.contactNumber)
                .bloodGroup(this.bloodGroup)
                .isActive(this.isActive)
                .address(addressDto)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }



}
