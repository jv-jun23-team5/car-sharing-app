package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Rental;
import java.util.List;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto);

    List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Long id);

    Rental getByUserIdAndActive(Long userId, boolean isActive);
}
