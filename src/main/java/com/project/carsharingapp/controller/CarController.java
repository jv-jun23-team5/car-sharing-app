package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.car.CarDto;
import com.project.carsharingapp.dto.car.CreateCarRequestDto;
import com.project.carsharingapp.dto.car.UpdateCarRequestDto;
import com.project.carsharingapp.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Car management",
        description = "Endpoints for managing cars")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new car",
            description = "Save new car")
    public CarDto add(@RequestBody @Valid CreateCarRequestDto requestDto) {
        return carService.save(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all cars",
            description = "Get list of all cars")
    public List<CarDto> getAll(Pageable pageable) {
        return carService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the car by id",
            description = "Get car's detailed information by identification number")
    public CarDto getById(@PathVariable Long id) {
        return carService.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the car by id",
            description = "Update the car information by identification number")
    public CarDto update(@PathVariable Long id,
                          @Valid @RequestBody UpdateCarRequestDto requestDto) {
        return carService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete the car by id",
            description = "Delete the car by identification number")
    public void deleteById(@PathVariable Long id) {
        carService.delete(id);
    }
}

