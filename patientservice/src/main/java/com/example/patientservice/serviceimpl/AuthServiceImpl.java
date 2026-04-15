package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.LoginRequestDto;
import com.example.patientservice.dto.request.RegisterRequest;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LoginResponseDto;
import com.example.patientservice.dto.response.RegisterResponseDto;
import com.example.patientservice.model.EmailVerification;
import com.example.patientservice.model.Roles;
import com.example.patientservice.model.User;
import com.example.patientservice.repository.EmailVerificationRepository;
import com.example.patientservice.repository.RolesRepository;
import com.example.patientservice.repository.UserRepository;
import com.example.patientservice.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final EmailVerificationRepository verificationRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        log.info("Login attempt for username: {}", request.getUsername());
        LoginResponseDto response = keycloakService.login(
                request.getUsername(), request.getPassword()
        );
        log.info("Login successful for username: {}", request.getUsername());
        return response;
    }

    @Override
    public RegisterResponseDto register(RegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        String token = keycloakService.getAdminToken();

        // 1. Create in Keycloak and get the ID
        String keycloakIdStr = keycloakService.createUser(
                token, request.getFirstname(),request.getLastname(),request.getUsername(), request.getPassword(), request.getEmail()
        );

        // 2. Assign role in Keycloak
        keycloakService.assignRole(token, keycloakIdStr, request.getRole());

        Roles role = rolesRepository.findByRoleName(request.getRole().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // 3. Manually set the ID to match Keycloak
        User user = User.builder()
                .id(UUID.fromString(keycloakIdStr))
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .userName(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .isActive(true)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);

        // 2. Generate 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // 3. Store OTP in DB (valid for 15 minutes)
        EmailVerification verification = EmailVerification.builder()
                .otp(otp)
                .userId(user.getId())
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        verificationRepository.save(verification);

        log.info("OTP generated for user {}: {}", user.getUserName(), otp);
        // In a real app, you'd call an EmailService here to send the OTP to the user.

        return RegisterResponseDto.builder()
                .userId(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public String emailVerify(UUID userId, String otp) {
        // 1. Find the OTP record
        EmailVerification verification = verificationRepository.findByUserIdAndOtp(userId, otp)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP or User ID"));

        // 2. Check Expiration
        if (verification.isExpired()) {
            verificationRepository.delete(verification);
            throw new IllegalArgumentException("OTP has expired. Please request a new one.");
        }

        // 3. Update Keycloak
        String token = keycloakService.getAdminToken();
        keycloakService.verifyUserEmail(token, userId);

        // 4. Cleanup: Delete the used OTP and update local user status if needed
        verificationRepository.deleteByUserId(userId);

        User user = userRepository.findById(userId).get();
        user.setIsActive(true); // Ensure they are active locally
        userRepository.save(user);
        return "Email successfully verified in Keycloak and System.";
    }


}