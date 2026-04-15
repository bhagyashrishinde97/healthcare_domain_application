package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.VitalsRequestDto;
import com.example.patientservice.dto.response.VitalsResponseDto;
import com.example.patientservice.model.Vitals;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VitalsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "encounter", ignore = true)
    Vitals toEntity(VitalsRequestDto dto);

    @Mapping(source = "encounter.id", target = "encounterId")
    VitalsResponseDto toDto(Vitals entity);
}