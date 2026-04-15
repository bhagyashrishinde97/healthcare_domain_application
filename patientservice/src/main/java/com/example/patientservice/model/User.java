package com.example.patientservice.model;

import com.example.patientservice.dto.response.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Embedded
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    public UserResponseDto toDto() {
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

        Set<RolesResponseDto> rolesDto = null;
        if (this.roles != null) {
            rolesDto = this.roles.stream().map(role -> {
                Set<PermissionResponseDto> permissionDtos = null;
                if (role.getPermissions() != null) {
                    permissionDtos = role.getPermissions().stream()
                            .map(p -> PermissionResponseDto.builder()
                                    .permissionName(p.getPermissionName())
                                    .permissionDescription(p.getDescription())
                                    .build())
                            .collect(Collectors.toSet());
                }
                return RolesResponseDto.builder()
                        .roleName(role.getRoleName())
                        .description(role.getDescription())
                        .isActive(role.getIsActive())
                        .permissionResponseDtoSet(permissionDtos)
                        .build();
            }).collect(Collectors.toSet());
        }

        return UserResponseDto.builder()
                .userName(this.userName)
                .email(this.email)
                .contactNumber(this.contactNumber != null ? Long.valueOf(this.contactNumber) : null)
                .bloodGroup(this.bloodGroup)
                .isActive(this.isActive)
                .addressDto(addressDto)
                .rolesSet(rolesDto)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}