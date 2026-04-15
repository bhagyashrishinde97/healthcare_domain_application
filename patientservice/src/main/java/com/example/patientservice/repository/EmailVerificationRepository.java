package com.example.patientservice.repository;

import com.example.patientservice.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByUserIdAndOtp(UUID userId, String otp);
    void deleteByUserId(UUID userId); // To clean up after success
}
