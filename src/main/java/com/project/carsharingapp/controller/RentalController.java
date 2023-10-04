package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.dto.rental.RentalSearchParametersDto;
import com.project.carsharingapp.service.rental.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rental management",
        description = "Endpoints for managing rentals")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rentals")
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new rental",
            description = "Save new rental and decrease car inventory by 1")
    public RentalDto add(@RequestBody @Valid CreateRentalRequestDto requestDto, Authentication authentication) {
        return rentalService.add(requestDto, authentication);
    }

//    @GetMapping("/")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get the rentals by user id and active status",
//            description = "Retrieve rentals by user identification number"
//                    + " and whether the rental is still active or not")
//    public List<RentalDto> getByUserIdAndActiveStatus(Pageable pageable, RentalSearchParametersDto params) {
//        return rentalService.getByUserIdAndActiveStatus(pageable, params);
//    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the rentals by user id and active status",
            description = "Retrieve rentals by user identification number"
                    + " and whether the rental is still active or not")
    public List<RentalDto> getByUserIdAndActiveStatus(@RequestParam(required = false) Long userId,
                                                      @RequestParam(required = false) Boolean isActive) {
//        return rentalService.getByUserIdAndActiveStatus(pageable, params);
        return rentalService.getByUserIdAndActiveStatus(userId, isActive);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the rental by id",
            description = "Get specific rental by identification number")
    public RentalDto getById(@PathVariable Long id) {
        return rentalService.getById(id);
    }

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Set actual return date",
            description = "Set actual return date and increase car inventory by 1")
    public RentalDto setActualReturnDay(Long id) {
        return rentalService.setActualReturnDay(id);
    }
}
