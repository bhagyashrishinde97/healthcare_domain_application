package com.example.patientservice.model;
import com.example.patientservice.dto.AddressResponseDto;
import com.example.patientservice.dto.DoctorResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Data
@Table(name = "doctors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "license_number") // use "license_number" NOT "licence_number"
})
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name="first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name="specialization", length = 50, nullable = false)
    private String specialization;

    @NotBlank(message = "License number is required")
    @Size(min = 6, max = 6, message = "California license number must be 6 characters")
    @Pattern(regexp = "^[A-Z][0-9]{5}$", message = "California license must start with a capital letter followed by 5 digits")
    @Column(name="license_number", nullable = false, unique = true)
    private String licenseNumber;  // American spelling "license"

    @Column(name="department", length=50)
    private String department;

    @Column(name="availability_status")
    private boolean availabilityStatus = true;

    @Column(name="contact_number", length = 15)
    private String contactNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    public DoctorResponseDto toDto() {

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

        return DoctorResponseDto.builder()
                .id(this.id)
                .userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .specialization(this.specialization)
                .licenseNumber(this.licenseNumber)
                .department(this.department)
                .availabilityStatus(this.availabilityStatus)
              //  .bloodGroup(this.bloodGroup)
                .contactNumber(this.contactNumber)
                .address(addressDto)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}

