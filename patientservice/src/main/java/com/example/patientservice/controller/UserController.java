package com.example.patientservice.controller;

import com.example.patientservice.dto.request.UserUpdateRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.UserResponseDto;
import com.example.patientservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/users")
@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMe() {
        log.info("API: Fetching current user profile");
        return ResponseEntity.ok(userService.getMe());
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateMe(
            @Valid @RequestBody UserUpdateRequestDto dto) {
        log.info("API: Updating current user profile");
        return ResponseEntity.ok(userService.updateMe(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        log.info("API: Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(
            @PathVariable UUID id) {
        log.info("API: Fetching user by id={}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deactivateUser(
            @PathVariable UUID id) {
        log.info("API: Deactivating user id={}", id);
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> activateUser(
            @PathVariable UUID id) {
        log.info("API: Activating user id={}", id);
        return ResponseEntity.ok(userService.activateUser(id));
    }
}