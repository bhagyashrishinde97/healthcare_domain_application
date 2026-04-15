package com.example.patientservice.controller;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.UserRequestDto;
import com.example.patientservice.dto.response.UserResponseDto;
import com.example.patientservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RequestMapping("/api/v1/users")
@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @RequestBody @Valid UserRequestDto dto) {

        log.info("Received request to create user with email: {}", dto.getEmail());

        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable UUID id) {

        log.info("Received request to fetch user with id: {}", id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {

        log.info("Received request to fetch all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequestDto dto) {

        log.info("Received request to update user with id: {}", id);

        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable UUID id) {

        log.info("Received request to delete user with id: {}", id);

        return ResponseEntity.ok(userService.deleteUser(id));
    }
}