package com.example.patientservice.dto;
import com.example.patientservice.model.Address;
import com.example.patientservice.model.User;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto  {

    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "userName is required")
    private UUID keycloakUserId;
    @Email(message = "email is not valild")
    @NotBlank(message = "email is requried")
    private String email;
//    @NotBlank(message = "password is required")
//    @Size(min=8, message = "size of the password minimum 8 ")
//    private String password;
    @Digits(integer = 15, fraction = 0)
    private Long contactNumber;
    private AddressRequestDto address;
    private String bloodGroup;
    private boolean isActive=true;
    private Set<String> rolesSet;
    public User toEntity() {

        Address addressEntity = null;

        if (this.address != null) {
            addressEntity = Address.builder()
                    .street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .country(address.getCountry())
                    .zipCode(address.getZipCode())
                    .build();
        }

        return User.builder()
                .userName(this.userName)
                .email(this.email)
                // .password(this.password)
                .bloodGroup(this.bloodGroup)
                .contactNumber(
                        this.contactNumber != null ? String.valueOf(this.contactNumber) : null
                )
                .isActive(this.isActive)
                .address(addressEntity)
                .build();
    }
    }