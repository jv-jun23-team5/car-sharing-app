package com.project.carsharingapp.mapper;

import com.project.carsharingapp.config.MapperConfig;
import com.project.carsharingapp.dto.car.CarDto;
import com.project.carsharingapp.dto.car.CreateCarRequestDto;
import com.project.carsharingapp.model.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    Car toEntity(CreateCarRequestDto createCarRequestDto);

    CarDto toDto(Car car);
}
