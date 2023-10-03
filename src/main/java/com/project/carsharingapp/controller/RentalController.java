package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.dto.rental.SetActualReturnDateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rental management",
        description = "Endpoints for managing rentals")
@RestController
@RequestMapping(value = "/rentals")
public class RentalController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new rental",
            description = "Save new rental and decrease car inventory by 1")
    public RentalDto add(@RequestBody @Valid CreateRentalRequestDto requestDto) {
        return null;
    }

    @GetMapping("/{userId}/{isActive}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the rentals by user id and active status",
            description = "Retrieve rentals by user identification number"
                    + " and whether the rental is still active or not")
    public RentalDto getByUserIdAndActiveStatus(@PathVariable Long userId,
                                                  @PathVariable boolean isActive) {
        return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the rental by id",
            description = "Get specific rental by identification number")
    public RentalDto getById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Set actual return date",
            description = "Set actual return date and increase car inventory by 1")
    public RentalDto setActualReturnDay(@RequestBody @Valid SetActualReturnDateRequestDto requestDto) {
        return null;
    }
}
