package com.example.patientservice.service;

import com.example.patientservice.dto.request.LoginRequestDto;
import com.example.patientservice.dto.request.RegisterRequest;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LoginResponseDto;
import com.example.patientservice.dto.response.RegisterResponseDto;

import java.util.UUID;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
    RegisterResponseDto register(RegisterRequest request);
    String emailVerify(UUID userId, String otp);
}