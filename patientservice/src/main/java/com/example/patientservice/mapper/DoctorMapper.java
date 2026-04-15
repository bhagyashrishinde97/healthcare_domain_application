package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.DoctorRequestDto;
import com.example.patientservice.dto.response.DoctorResponseDto;
import com.example.patientservice.model.Doctor;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Doctor toEntity(DoctorRequestDto dto);

    DoctorResponseDto toDto(Doctor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    void updateEntity(DoctorRequestDto dto, @MappingTarget Doctor entity);
}