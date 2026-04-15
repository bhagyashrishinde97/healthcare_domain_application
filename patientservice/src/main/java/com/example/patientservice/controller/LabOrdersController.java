package com.example.patientservice.controller;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.service.LabOrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lab_orders")
@RequiredArgsConstructor
@Slf4j
public class LabOrdersController {
    private final LabOrdersService labordersService;

    @PostMapping("/{encounterId}")
    public ResponseEntity<LabOrderResponseDto> addLavOrder(@PathVariable Long encounterId ,@Valid @RequestBody LabOrderRequestDto dto) {
        log.info("api call : Add LavOrder for encounterId={}", encounterId);
        LabOrderResponseDto responseDto = labordersService.addLabOrder(encounterId, dto);
        log.info("api Response : LabOrder created with id={}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

}
