package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.request.AppointmentRequestDto;
import com.example.patientservice.dto.response.AppointmentResponseDto;
import com.example.patientservice.enums.AppointmentStatus;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.exception.ValidationException;
import com.example.patientservice.mapper.AppointmentMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ApiResponse<AppointmentResponseDto> createAppointment(AppointmentRequestDto dto) {
        log.info("Service: Creating appointment for patientId={}, doctorId={}, clinicId={}",
                dto.getPatientId(), dto.getDoctorId(), dto.getClinicId());

        validateAppointmentRequest(dto);

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + dto.getClinicId()));

        if (Boolean.FALSE.equals(doctor.getAvailabilityStatus())) {
            throw new ValidationException("Doctor is currently unavailable");
        }

        if (Boolean.FALSE.equals(clinic.getIsActive())) {
            throw new ValidationException("Clinic is currently inactive");
        }

        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setClinic(clinic);
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);

        log.info("Service: Appointment created successfully with UUID={}", saved.getAppointmentId());
        return ApiResponse.success("Appointment created successfully", appointmentMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<AppointmentResponseDto> getAppointmentById(UUID appointmentId) {
        log.info("Service: Fetching appointment with UUID={}", appointmentId);

        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with UUID: " + appointmentId));

        return ApiResponse.success("Appointment fetched successfully", appointmentMapper.toDto(appointment));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<AppointmentResponseDto>> getAllAppointments() {
        log.info("Service: Fetching all appointments");

        List<AppointmentResponseDto> appointments = appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

        return ApiResponse.success("Appointments fetched successfully", appointments);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<AppointmentResponseDto>> getAppointmentsByPatientId(Long patientId) {
        log.info("Service: Fetching appointments for patientId={}", patientId);

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }

        List<AppointmentResponseDto> appointments = appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

        return ApiResponse.success("Patient appointments fetched successfully", appointments);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<AppointmentResponseDto>> getAppointmentsByDoctorId(Long doctorId) {
        log.info("Service: Fetching appointments for doctorId={}", doctorId);

        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }

        List<AppointmentResponseDto> appointments = appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

        return ApiResponse.success("Doctor appointments fetched successfully", appointments);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<AppointmentResponseDto>> getAppointmentsByStatus(AppointmentStatus status) {
        log.info("Service: Fetching appointments with status={}", status);

        List<AppointmentResponseDto> appointments = appointmentRepository.findByStatus(status)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

        return ApiResponse.success("Appointments by status fetched successfully", appointments);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<AppointmentResponseDto>> getDoctorAvailableSlots(
            Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Service: Fetching available slots for doctorId={} between {} and {}",
                doctorId, startDate, endDate);

        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }

        List<AppointmentResponseDto> bookedSlots = appointmentRepository
                .findDoctorAppointmentsBetween(doctorId, startDate, endDate)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

        return ApiResponse.success("Doctor schedule fetched successfully", bookedSlots);
    }

    @Override
    public ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentRequestDto dto) {
        log.info("Service: Updating appointment with id={}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED ||
                appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new ValidationException("Cannot update " + appointment.getStatus() + " appointment");
        }

        if (dto.getPatientId() != null && !dto.getPatientId().equals(appointment.getPatient().getId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
            appointment.setPatient(patient);
        }

        if (dto.getDoctorId() != null && !dto.getDoctorId().equals(appointment.getDoctor().getId())) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
            appointment.setDoctor(doctor);
        }

        if (dto.getClinicId() != null && !dto.getClinicId().equals(appointment.getClinic().getId())) {
            Clinic clinic = clinicRepository.findById(dto.getClinicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clinic not found"));
            appointment.setClinic(clinic);
        }

        appointmentMapper.updateEntity(dto, appointment);
        Appointment updated = appointmentRepository.save(appointment);

        log.info("Service: Appointment updated successfully with id={}", updated.getId());
        return ApiResponse.success("Appointment updated successfully", appointmentMapper.toDto(updated));
    }

    @Override
    public ApiResponse<AppointmentResponseDto> confirmAppointment(Long id) {
        log.info("Service: Confirming appointment with id={}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new ValidationException("Cannot confirm a cancelled appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new ValidationException("Appointment is already completed");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        Appointment updated = appointmentRepository.save(appointment);

        log.info("Service: Appointment confirmed successfully");
        return ApiResponse.success("Appointment confirmed successfully", appointmentMapper.toDto(updated));
    }

    @Override
    public ApiResponse<AppointmentResponseDto> cancelAppointment(Long id, String reason) {
        log.info("Service: Cancelling appointment with id={}, reason={}", id, reason);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new ValidationException("Cannot cancel a completed appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        String cancelReason = (appointment.getReason() != null ? appointment.getReason() + " | " : "") +
                "Cancelled: " + reason;
        appointment.setReason(cancelReason);

        Appointment updated = appointmentRepository.save(appointment);

        log.info("Service: Appointment cancelled successfully");
        return ApiResponse.success("Appointment cancelled successfully", appointmentMapper.toDto(updated));
    }

    @Override
    public ApiResponse<Object> deleteAppointment(Long id) {
        log.info("Service: Deleting appointment with id={}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        log.info("Service: Appointment soft deleted successfully with id={}", id);
        return ApiResponse.successMessage("Appointment deleted successfully");
    }

    private void validateAppointmentRequest(AppointmentRequestDto dto) {
        if (dto.getAppointmentDate() == null) {
            throw new ValidationException("Appointment date is required");
        }

        if (dto.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Appointment date cannot be in the past");
        }

        if (dto.getPatientId() == null) {
            throw new ValidationException("Patient ID is required");
        }

        if (dto.getDoctorId() == null) {
            throw new ValidationException("Doctor ID is required");
        }

        if (dto.getClinicId() == null) {
            throw new ValidationException("Clinic ID is required");
        }
    }
}