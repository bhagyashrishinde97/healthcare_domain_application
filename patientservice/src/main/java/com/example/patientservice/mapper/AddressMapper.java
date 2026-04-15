package com.example.patientservice.mapper;

import com.example.patientservice.dto.request.AddressRequestDto;
import com.example.patientservice.dto.response.AddressResponseDto;
import com.example.patientservice.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    Address toEntity(AddressRequestDto dto);

    AddressResponseDto toDto(Address entity);
}