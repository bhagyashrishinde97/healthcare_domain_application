package com.example.patientservice.serviceimpl;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.UserRequestDto;
import com.example.patientservice.dto.response.UserResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Roles;
import com.example.patientservice.model.User;
import com.example.patientservice.repository.RolesRepository;
import com.example.patientservice.repository.UserRepository;
import com.example.patientservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;


    @Override
    public ApiResponse<UserResponseDto> createUser(UserRequestDto dto) {

        log.info("Creating user with email: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = dto.toEntity();

        // Assign Roles
        if (dto.getRolesSet() != null && !dto.getRolesSet().isEmpty()) {

            Set<Roles> roles = dto.getRolesSet()
                    .stream()
                    .map(roleName -> rolesRepository.findByRoleName(roleName)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());

            user.setRoles(roles);
        }

        user = userRepository.save(user);

        log.info("User created successfully with id: {}", user.getId());

        return ApiResponse.success(
                "User created successfully",
                user.toDto()
        );
    }

    @Override
    public ApiResponse<UserResponseDto> getUserById(UUID id) {

        log.info("Fetching user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return ApiResponse.success(
                "User fetched successfully",
                user.toDto()
        );
    }

    @Override
    public ApiResponse<List<UserResponseDto>> getAllUsers() {

        log.info("Fetching all users");

        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(User::toDto)
                .collect(Collectors.toList());

        return ApiResponse.success(
                "Users fetched successfully",
                users
        );
    }

    @Override
    public ApiResponse<UserResponseDto> updateUser(UUID id, UserRequestDto dto) {

        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBloodGroup(dto.getBloodGroup());
        user.setIsActive(dto.isActive());

        if (dto.getContactNumber() != null) {
            user.setContactNumber(String.valueOf(dto.getContactNumber()));
        }

        userRepository.save(user);

        log.info("User updated successfully with id: {}", id);

        return ApiResponse.success(
                "User updated successfully",
                user.toDto()
        );
    }

    @Override
    public ApiResponse<Object> deleteUser(UUID id) {

        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully with id: {}", id);

        return ApiResponse.success(
                "User deleted successfully",
                null
        );
    }
}