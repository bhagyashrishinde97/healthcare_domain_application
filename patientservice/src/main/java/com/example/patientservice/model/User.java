package com.example.patientservice.model;

import com.example.patientservice.dto.response.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String firstName;

    private String lastName;

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


}