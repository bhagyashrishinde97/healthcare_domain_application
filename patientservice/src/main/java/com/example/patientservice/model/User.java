package com.example.patientservice.model;
import com.example.patientservice.dto.AddressResponseDto;
import com.example.patientservice.dto.PermissionResponseDto;
import com.example.patientservice.dto.RolesResponseDto;
import com.example.patientservice.dto.UserResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keycloak_user_id", unique = true, nullable = false)
    private UUID keycloakUserId;
    @Column(nullable = false, unique = true)
    private String userName;
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
                            .map(permission -> PermissionResponseDto.builder()
                                    .permissionName(permission.getPermissionName())
                                    //   .description(permission.getDescription())
                                    .permissionDescription(permission.getDescription())
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
                .contactNumber(
                        this.contactNumber != null ? Long.valueOf(this.contactNumber) : null
                )
                .bloodGroup(this.bloodGroup)
                .isActive(this.isActive)
                .addressDto(addressDto)
                .rolesSet(rolesDto)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}