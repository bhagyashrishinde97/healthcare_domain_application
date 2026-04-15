package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.ClinicRequestDto;
import com.example.patientservice.dto.response.ClinicResponseDto;
import com.example.patientservice.model.Clinic;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClinicMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Clinic toEntity(ClinicRequestDto dto);

    ClinicResponseDto toDto(Clinic entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    void updateEntity(ClinicRequestDto dto, @MappingTarget Clinic entity);
}