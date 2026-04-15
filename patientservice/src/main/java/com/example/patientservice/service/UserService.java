package com.example.patientservice.service;

import com.example.patientservice.dto.request.UserUpdateRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.UserResponseDto;
import com.example.patientservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

 User getOrCreateFromJwt();

 ApiResponse<UserResponseDto> getMe();

 ApiResponse<UserResponseDto> getUserById(UUID id);

 ApiResponse<String> deactivateUser(UUID id);

 ApiResponse<String> activateUser(UUID id);

 ApiResponse<UserResponseDto> updateMe(UserUpdateRequestDto dto);

 ApiResponse<List<UserResponseDto>> getAllUsers();
}