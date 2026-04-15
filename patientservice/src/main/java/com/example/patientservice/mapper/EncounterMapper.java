package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.EncounterRequestDto;
import com.example.patientservice.dto.response.EncounterResponseDto;
import com.example.patientservice.model.Encounter;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EncounterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "encounterId", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "encounterDate", ignore = true)
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "labOrders", ignore = true)
    @Mapping(target = "vitalsList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Encounter toEntity(EncounterRequestDto dto);

    @Mapping(source = "appointment.id", target = "appointmentId")
    EncounterResponseDto toDto(Encounter entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "encounterId", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "encounterDate", ignore = true)
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "labOrders", ignore = true)
    @Mapping(target = "vitalsList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(EncounterRequestDto dto, @MappingTarget Encounter entity);
}