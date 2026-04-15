package com.example.patientservice.serviceimpl;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.ApiResponse;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.enums.LabStatus;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.mapper.LabOrderMapper;
import com.example.patientservice.model.Encounter;
import com.example.patientservice.model.LabOrder;
import com.example.patientservice.repository.EncounterRepository;
import com.example.patientservice.repository.LabOrdersRepository;
import com.example.patientservice.service.LabOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LabOrdersServiceImpl implements LabOrdersService {

    private final LabOrdersRepository labOrdersRepository;
    private final EncounterRepository encounterRepository;
    private final LabOrderMapper labOrderMapper;

    @Override
    public ApiResponse<LabOrderResponseDto> addLabOrder(Long encounterId, LabOrderRequestDto dto) {
        log.info("Service: Adding lab order for encounterId={}, test={}", encounterId, dto.getTestName());

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with id: " + encounterId));

        LabOrder labOrder = labOrderMapper.toEntity(dto);
        labOrder.setEncounter(encounter);

        if (labOrder.getLabStatus() == null) {
            labOrder.setLabStatus(LabStatus.ORDERED);
        }

        LabOrder saved = labOrdersRepository.save(labOrder);

        log.info("Service: Lab order saved successfully with id={}", saved.getId());
        return ApiResponse.success("Lab order created successfully", labOrderMapper.toDto(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<LabOrderResponseDto> getLabOrderById(Long id) {
        log.info("Service: Fetching lab order by id={}", id);

        LabOrder labOrder = labOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab order not found with id: " + id));

        return ApiResponse.success("Lab order fetched successfully", labOrderMapper.toDto(labOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<LabOrderResponseDto>> getLabOrdersByEncounterId(Long encounterId) {
        log.info("Service: Fetching lab orders for encounterId={}", encounterId);

        if (!encounterRepository.existsById(encounterId)) {
            throw new ResourceNotFoundException("Encounter not found with id: " + encounterId);
        }

        List<LabOrderResponseDto> labOrders = labOrdersRepository.findByEncounterId(encounterId)
                .stream()
                .map(labOrderMapper::toDto)
                .toList();

        return ApiResponse.success("Lab orders fetched successfully", labOrders);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<LabOrderResponseDto>> getAllLabOrders() {
        log.info("Service: Fetching all lab orders");

        List<LabOrderResponseDto> labOrders = labOrdersRepository.findAll()
                .stream()
                .map(labOrderMapper::toDto)
                .toList();

        return ApiResponse.success("Lab orders fetched successfully", labOrders);
    }

    @Override
    public ApiResponse<LabOrderResponseDto> updateLabOrderStatus(Long id, LabStatus status) {
        log.info("Service: Updating lab order status id={}, status={}", id, status);

        LabOrder labOrder = labOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab order not found with id: " + id));

        labOrder.setLabStatus(status);
        LabOrder updated = labOrdersRepository.save(labOrder);

        log.info("Service: Lab order status updated successfully");
        return ApiResponse.success("Lab order status updated successfully", labOrderMapper.toDto(updated));
    }

    @Override
    public ApiResponse<LabOrderResponseDto> updateLabOrderResult(Long id, String result) {
        log.info("Service: Updating lab order result id={}", id);

        LabOrder labOrder = labOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab order not found with id: " + id));

        labOrder.setResult(result);
        labOrder.setLabStatus(LabStatus.COMPLETED);
        LabOrder updated = labOrdersRepository.save(labOrder);

        log.info("Service: Lab order result updated successfully");
        return ApiResponse.success("Lab order result updated successfully", labOrderMapper.toDto(updated));
    }
}