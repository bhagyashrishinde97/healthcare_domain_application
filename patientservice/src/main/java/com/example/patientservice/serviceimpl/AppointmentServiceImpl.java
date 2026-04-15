package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.AppointmentRequestDto;
import com.example.patientservice.dto.response.AppointmentResponseDto;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.model.Appointment;
import com.example.patientservice.model.Clinic;
import com.example.patientservice.model.Doctor;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.AppointmentRepository;
import com.example.patientservice.repository.ClinicRepository;
import com.example.patientservice.repository.DoctorRepository;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;


    @Override
    public ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto) {

        log.info("Creating appointment");

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found"));

        Appointment appointment = dto.toEntity(patient, doctor, clinic);

        Appointment saved = appointmentRepository.save(appointment);

        log.info("Created appointment with UUID={}", saved.getAppointmentId());


        return ApiResponse.success("Created successfully", saved.toDto());
    }


    @Override
    public ApiResponse<AppointmentResponseDto> getAppointmentById(UUID appointmentId) {

        log.info("Fetching appointment {}", appointmentId);

        Appointment appointment = appointmentRepository
                .findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with UUID " + appointmentId));

        return ApiResponse.success("Fetched successfully", appointment.toDto());
    }

    @Override
    public ApiResponse<List<AppointmentResponseDto>> getAllAppointments() {

        log.info("Fetching all appointments");

        List<AppointmentResponseDto> list = appointmentRepository.findAll()
                .stream()
                .map(Appointment::toDto)
                .toList();

        return ApiResponse.success("All appointments", list);
    }


    @Override
    public ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto) {

        log.info("Updating appointment {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found"));


        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setClinic(clinic);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());

        Appointment updated = appointmentRepository.save(appointment);

        return ApiResponse.success("Updated successfully", updated.toDto());
    }


    @Override
    public ApiResponse<Object> deleteAppointment(Long id) {

        log.info("Deleting appointment {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        appointmentRepository.delete(appointment);

        return ApiResponse.success("Deleted successfully", null);
    }
}