package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.DoctorRequestDto;
import com.example.patientservice.dto.response.DoctorResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Doctor;
import com.example.patientservice.repository.DoctorRepository;
import com.example.patientservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public ApiResponse<DoctorResponseDto> createDoctor(DoctorRequestDto dto) {
        log.info("Creating doctor");

        if (doctorRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {
            throw new IllegalArgumentException("License number already exists");
        }

        Doctor doctor = dto.toEntity();
        Doctor savedDoctor = doctorRepository.save(doctor);

        log.info("Doctor created with id {}", savedDoctor.getId());

        return ApiResponse.success("Doctor created successfully", savedDoctor.toDto());
    }

    @Override
    public ApiResponse<DoctorResponseDto> getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));

        return ApiResponse.success("Doctor fetched successfully", doctor.toDto());
    }

    @Override
    public ApiResponse<List<DoctorResponseDto>> getAllDoctors() {
        List<DoctorResponseDto> doctors = doctorRepository.findAll()
                .stream()
                .map(Doctor::toDto)
                .toList();

        return ApiResponse.success("All doctors fetched successfully", doctors);
    }

    @Override
    public ApiResponse<DoctorResponseDto> updateDoctor(Long id, DoctorRequestDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));

        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setDepartment(dto.getDepartment());
        doctor.setAvailabilityStatus(dto.getAvailabilityStatus());
        doctor.setContactNumber(dto.getContactNumber());
        Doctor updatedDoctor = doctorRepository.save(doctor);

        return ApiResponse.success("Doctor updated successfully", updatedDoctor.toDto());
    }

    @Override
    public ApiResponse<Object> deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));

        doctorRepository.delete(doctor);

        return ApiResponse.success("Doctor deleted successfully", null);
    }
}