package com.example.patientservice.service;

import com.example.patientservice.dto.ApiResponse;
import com.example.patientservice.dto.UserRequestDto;
import com.example.patientservice.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResponse<UserResponseDto> createUser(UserRequestDto dto);

    ApiResponse<UserResponseDto> getUserById(UUID id);

    ApiResponse<List<UserResponseDto>> getAllUsers();

    ApiResponse<UserResponseDto> updateUser(UUID id, UserRequestDto dto);

    ApiResponse<Object> deleteUser(UUID id);
}
