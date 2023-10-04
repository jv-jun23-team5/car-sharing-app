package com.project.carsharingapp.mapper;

import com.project.carsharingapp.config.MapperConfig;
import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    Rental toEntity(CreateRentalRequestDto requestDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "car.id", target = "carId")
    RentalDto toDto(Rental rental);
}
