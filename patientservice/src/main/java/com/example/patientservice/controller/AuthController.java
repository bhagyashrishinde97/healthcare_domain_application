package com.example.patientservice.controller;

import com.example.patientservice.dto.request.LoginRequestDto;
import com.example.patientservice.dto.request.RegisterRequest;   // ← correct import
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LoginResponseDto;
import com.example.patientservice.dto.response.RegisterResponseDto;
import com.example.patientservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("User Registration Success", authService.register(request))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", authService.login(request))
        );
    }

    @PostMapping("/{userId}/verify")
    public ResponseEntity<ApiResponse<String>> emailVerify(
            @PathVariable UUID userId,
            @RequestParam String otp) {
        String message = authService.emailVerify(userId, otp);
        return ResponseEntity.ok(ApiResponse.success(message, null));
    }
}