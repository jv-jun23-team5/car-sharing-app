package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.car.CarDto;
import com.project.carsharingapp.dto.car.CreateCarRequestDto;
import com.project.carsharingapp.dto.car.UpdateCarRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarDto save(CreateCarRequestDto createCarRequestDto);

    CarDto get(Long id);

    List<CarDto> getAll(Pageable pageable);

    CarDto update(Long id, UpdateCarRequestDto updateCarRequestDto);

    void delete(Long id);
}
