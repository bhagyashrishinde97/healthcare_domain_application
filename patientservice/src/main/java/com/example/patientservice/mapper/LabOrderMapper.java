package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.LabOrderRequestDto;
import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.model.LabOrder;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LabOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "encounter", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    LabOrder toEntity(LabOrderRequestDto dto);

    @Mapping(source = "encounter.id", target = "encounterId")
    LabOrderResponseDto toDto(LabOrder entity);
}