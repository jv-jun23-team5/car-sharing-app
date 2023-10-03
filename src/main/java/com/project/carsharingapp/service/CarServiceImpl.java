package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.car.CarDto;
import com.project.carsharingapp.dto.car.CreateCarRequestDto;
import com.project.carsharingapp.dto.car.UpdateCarRequestDto;
import com.project.carsharingapp.mapper.CarMapper;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Transactional
    @Override
    public CarDto save(CreateCarRequestDto createCarRequestDto) {
        Car car = carMapper.toEntity(createCarRequestDto);
        car = carRepository.save(car);
        return carMapper.toDto(car);

    }

    @Override
    public CarDto get(Long id) {
        Car car = carRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Car not found with id: " + id));
        return carMapper.toDto(car);

    }

    @Override
    public List<CarDto> getAll(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);
        return carMapper.toDtoList(cars.getContent());

    }

    @Transactional
    @Override
    public CarDto update(Long id, UpdateCarRequestDto updateCarRequestDto) {
        Car car = carRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Car not found"));
        car.setModel(updateCarRequestDto.getModel());
        car.setBrand(updateCarRequestDto.getBrand());
        car.setInventory(updateCarRequestDto.getInventory());
        car.setCarType(updateCarRequestDto.getCarType());
        car.setDailyFee(updateCarRequestDto.getDailyFee());
        car = carRepository.save(car);
        return carMapper.toDto(car);

    }

    @Override
    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }
}
