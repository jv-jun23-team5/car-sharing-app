package com.project.carsharingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.carsharingapp.dto.car.CarDto;
import com.project.carsharingapp.dto.car.CreateCarRequestDto;
import com.project.carsharingapp.dto.car.UpdateCarRequestDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.CarMapper;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.repository.CarRepository;
import com.project.carsharingapp.service.impl.CarServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    private Car testCar;
    private CarDto testCarDto;
    private CreateCarRequestDto createRequest;
    private UpdateCarRequestDto updateRequest;

    @BeforeEach
    public void setup() {
        testCar = new Car();
        testCar.setId(1L);
        testCar.setModel("ModelX");
        testCar.setBrand("Tesla");
        testCar.setInventory(10);
        testCar.setCarType(Car.CarType.SEDAN);
        testCar.setDailyFee(new BigDecimal("100.00"));

        testCarDto = new CarDto();
        testCarDto.setId(1L);
        testCarDto.setModel("ModelX");
        testCarDto.setBrand("Tesla");
        testCarDto.setInventory(10);
        testCarDto.setCarType(Car.CarType.SEDAN);
        testCarDto.setDailyFee(new BigDecimal("100.00"));

        createRequest = new CreateCarRequestDto();
        createRequest.setModel("ModelX");
        createRequest.setBrand("Tesla");
        createRequest.setInventory(10);
        createRequest.setCarType(Car.CarType.SEDAN);
        createRequest.setDailyFee(new BigDecimal("100.00"));

        updateRequest = new UpdateCarRequestDto();
        updateRequest.setModel("ModelY");
        updateRequest.setBrand("Tesla");
        updateRequest.setInventory(5);
        updateRequest.setCarType(Car.CarType.SUV);
        updateRequest.setDailyFee(new BigDecimal("150.00"));
    }

    @Test
    public void testSave() {
        when(carMapper.toEntity(createRequest)).thenReturn(testCar);
        when(carRepository.save(any())).thenReturn(testCar);
        when(carMapper.toDto(testCar)).thenReturn(testCarDto);

        CarDto result = carService.save(createRequest);

        assertEquals(testCarDto, result);
    }

    @Test
    public void testGet() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(testCar));
        when(carMapper.toDto(testCar)).thenReturn(testCarDto);

        CarDto result = carService.get(1L);

        assertEquals(testCarDto, result);
    }

    @Test
    public void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar);

        Page<Car> page = new PageImpl<>(cars);
        when(carRepository.findAll(pageable)).thenReturn(page);
        when(carMapper.toDto(testCar)).thenReturn(testCarDto);

        List<CarDto> result = carService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCarDto, result.get(0));
    }

    @Test
    public void testUpdate() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(testCar));
        when(carMapper.toDto(any(Car.class))).thenReturn(testCarDto);
        when(carRepository.save(any())).thenReturn(testCar);

        CarDto result = carService.update(1L, updateRequest);

        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).save(testCar);

        assertEquals(testCarDto, result);
    }

    @Test
    public void testDelete() {
        Long carId = 1L;
        doNothing().when(carRepository).deleteById(carId);

        carService.delete(carId);

        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    public void testGetNotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.get(1L));
    }

    @Test
    public void testUpdateWithInvalidId() {
        Long invalidId = 999L;
        when(carRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()
                -> carService.update(invalidId, updateRequest));
    }

    @Test
    public void testGetWithInvalidId() {
        Long invalidId = 999L;
        when(carRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.get(invalidId));
    }
}
