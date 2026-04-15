package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.PrescriptionRequestDto;
import com.example.patientservice.dto.response.PrescriptionResponseDto;
import com.example.patientservice.model.Prescription;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PrescriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "encounter", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Prescription toEntity(PrescriptionRequestDto dto);

    @Mapping(source = "encounter.id", target = "encounterId")
    PrescriptionResponseDto toDto(Prescription entity);
}