package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.UserUpdateRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.UserResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.mapper.UserMapper;
import com.example.patientservice.model.User;
import com.example.patientservice.repository.UserRepository;
import com.example.patientservice.service.UserService;
import com.example.patientservice.utility.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getOrCreateFromJwt() {
        UUID userId = JwtHelper.getCurrentUserId();
        return userRepository.findById(userId)
                .orElseGet(() -> {
                    User user = User.builder()
                            .id(userId)
                            .email(JwtHelper.getCurrentUserEmail())
                            .userName(JwtHelper.getCurrentUserEmail())
                            .firstName(JwtHelper.getCurrentUserFirstName())
                            .lastName(JwtHelper.getCurrentUserLastName())
                            .isActive(true)
                            .build();
                    log.info("Service: Auto-provisioning user from JWT: userId={}", userId);
                    return userRepository.save(user);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<UserResponseDto> getMe() {
        User user = getOrCreateFromJwt();
        log.info("Service: Fetched current user profile");
        return ApiResponse.success("User profile fetched successfully", userMapper.toDto(user));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<UserResponseDto> getUserById(UUID id) {
        log.info("Service: Fetching user by id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return ApiResponse.success("User fetched successfully", userMapper.toDto(user));
    }

    @Override
    public ApiResponse<String> deactivateUser(UUID id) {
        log.info("Service: Deactivating user with id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setIsActive(false);
        userRepository.save(user);

        log.info("Service: User deactivated successfully");
        return ApiResponse.success("User deactivated successfully", "User has been deactivated");
    }

    @Override
    public ApiResponse<String> activateUser(UUID id) {
        log.info("Service: Activating user with id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setIsActive(true);
        userRepository.save(user);

        log.info("Service: User activated successfully");
        return ApiResponse.success("User activated successfully", "User has been activated");
    }

    @Override
    public ApiResponse<UserResponseDto> updateMe(UserUpdateRequestDto dto) {
        User user = getOrCreateFromJwt();
        log.info("Service: Updating user profile for userId={}", user.getId());

        userMapper.updateEntity(dto, user);
        User updated = userRepository.save(user);

        log.info("Service: User profile updated successfully");
        return ApiResponse.success("Profile updated successfully", userMapper.toDto(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<UserResponseDto>> getAllUsers() {
        log.info("Service: Fetching all users");

        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return ApiResponse.success("Users fetched successfully", users);
    }
}